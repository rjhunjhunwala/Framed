/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author rohan
 */
public class GamePanel extends JPanel {

	public GamePanel() {
		this.setBackground(Color.black);
	}

	static int X_SIZE = 640;
	static int Y_SIZE = 640;
	static int X_MID = X_SIZE / 2;
	static int Y_MID = Y_SIZE / 2;
	static final int SCALE = 3;
	static ArrayList<FaceMesh> meshes = new ArrayList<>();
	static ArrayList<Face> faces;
	public static final int[] magicArray = new int[]{-1, 0, 0, 1, 0, 0, 0, -1, 0, 0, 1, 0, 0, 0, -1, 0, 0, 1};

	static {
		Map.makeMaze();

		if (Map.threeD) {

			//floodfill to remove useless cubes
			boolean[][][] realMaze = new boolean[Maze.size * SCALE][Maze.size * SCALE][Maze.size * SCALE];

			//it's a debatable data structure, I know....
			LinkedList<int[]> frontier = new LinkedList<>();
			frontier.add(new int[]{realMaze.length / 2, realMaze.length / 2, realMaze.length / 2});
			realMaze[realMaze.length / 2][realMaze.length / 2][realMaze.length / 2] = true;
			while (!frontier.isEmpty()) {
				LinkedList<int[]> finalFrontier = new LinkedList<>();
				for (int[] a : frontier) {
					for (int i = 0; i < 6; i++) {

						int x = a[0] + magicArray[i * 3];
						int y = a[1] + magicArray[i * 3 + 1];
						int z = a[2] + magicArray[i * 3 + 2];
						if (!realMaze[x][y][z]) {
							realMaze[x][y][z] = true;

							if (Maze.maze[0][x / SCALE][y / SCALE][z / SCALE] == 1) {
								finalFrontier.add(new int[]{x, y, z});
							}
						}
					}
				}
				frontier = finalFrontier;
			}

			for (int i = 0; i < realMaze.length; i++) {
				for (int j = 0; j < realMaze.length; j++) {
					for (int k = 0; k < realMaze.length; k++) {
						if (realMaze[i][j][k] && Maze.maze[0][i / SCALE][j / SCALE][k / SCALE] == 0) {
							meshes.add(FaceMesh.getCube(i, j, k));
						}
					}
				}

			}

		} else {
			if (Map.surface) {
				faces = new ArrayList<>((Map.heightMap.length - 1) * (Map.heightMap[0].length - 1));
				for (int i = 0; i < Map.heightMap.length - 1; i++) {
					for (int j = 0; j < Map.heightMap[0].length - 1; j++) {
						faces.add(new Face(i, j, Map.heightMap[i][j], i + 1, j, Map.heightMap[i + 1][j], i + 1, j + 1, Map.heightMap[i + 1][j + 1], i, j + 1, Map.heightMap[i][j + 1]));
					}
				}
			} else {

				for (int i = 0; i < Map.maze.length; i++) {
					for (int j = 0; j < Map.maze[i].length; j++) {

						if (Map.maze[i][j] == false) {
							for (int l = 0; l < SCALE; l++) {
								for (int m = 0; m < SCALE; m++) {

									meshes.add(FaceMesh.getCube(i * SCALE + l, j * SCALE + m, 0));
								}
							}
						}
					}
				}
			}

			faces = new ArrayList<>(meshes.size() * 4);

			for (FaceMesh m : meshes) {
				for (Face f : m.meshes) {
					faces.add(f);
				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(X_SIZE, Y_SIZE);
	}
	public static int renderDist = Map.surface?Map.HILLS*6:9;
	public static int DARK = 400 / SCALE / renderDist;
	public static final boolean EDGES = false;

	public void paintComponentL(Graphics g) {
		super.paintComponent(g);
		int numValidFaces = 0;

		for (Face f : faces) {
			if (isValidFace(f)) {
				numValidFaces++;
			}
		}
		ArrayList<Face> validFaces = new ArrayList<>(numValidFaces);
		//ugly, but blazing fast...
		for (Face f : faces) {
			if (isValidFace(f)) {
				validFaces.add(f);
			}
		}

		//	Collections.sort(faces);
		Collections.sort(validFaces);
		boxes:
		for (Face f : validFaces) {

			int[] xPoints = new int[f.vertices.length];
			int[] yPoints = new int[f.vertices.length];
			int i = 0;
			double dist = 0.0;
			MyPoint newPoint = null;
			for (ThreeDPoint p : f.vertices) {

				//these determine a "distance vector" of sorts...
				//Yes, dX is misleading, this isn't a differential, d is a poor appreviation for delta x
				//or if you prefer dX means "displacement"X
				double dX = p.getX() - Framed.p.x;
				double dY = p.getY() - Framed.p.y;
				double dZ = p.getZ() - Framed.p.z;

				if (dX * dX + dY * dY + dZ * dZ > renderDist * renderDist * SCALE * SCALE) {
					continue boxes;
				}

				//lets assume that the view vector is some unit vector turned on a horizontal plane by theta
				// and pitched vertically by fi
				//now lets turn the displacement vector by applying linear transformations
				//don't worry, it's just "linear" algebra....
				//[dX, dY] *[[cos -theta, sin -theta], [-sin -theta (=sin theta), cos -theta]] = [newDX, newDY]
				double newdX = dX * Math.cos(Framed.p.yaw) + dY * Math.sin(Framed.p.yaw);
				double newdY = -dX * Math.sin(Framed.p.yaw) + dY * Math.cos(Framed.p.yaw);

				//[dX,dZ] * [[cos -theta, sin -theta], [-sin -theta (=sin theta), cos -theta]] = [newDX, newDZ]
				double newNewdX = newdX * Math.cos(Framed.p.pitch) + dZ * Math.sin(Framed.p.pitch);
				double newdZ = -newdX * Math.sin(Framed.p.pitch) + dZ * Math.cos(Framed.p.pitch);

				if (newNewdX < 0) {
					continue boxes;
				}
				xPoints[i] = (int) (X_SIZE / 2 - (newdY / newNewdX) * X_SIZE / 2);
				yPoints[i] = (int) (Y_SIZE / 2 - (newdZ / newNewdX) * Y_SIZE / 2);
				i++;

			}

			int r = (int) (f.r - f.dist() * DARK);
			int gr = (int) (f.g - f.dist() * DARK);
			int b = (int) (f.b - f.dist() * DARK);

			//darken each channel and force it to be above zero
			g.setColor(new Color(r > 0 ? r : 0, gr > 0 ? gr : 0, b > 0 ? b : 0));

			g.fillPolygon(xPoints, yPoints, 4);

		}

	}

	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		int numValidFaces = 0;

		for (Face f : faces) {
			if (isValidFace(f)) {
				numValidFaces++;
			}
		}
		ArrayList<Face> validFaces = new ArrayList<>(numValidFaces);
		//ugly, but blazing fast...
		for (Face f : faces) {
			if (isValidFace(f)) {
				validFaces.add(f);
			}
		}

		//if it looks stupid and it works... it's not stupid
		double a = Framed.p.viewVector[0];
		double b = Framed.p.viewVector[1];
		double c = Framed.p.viewVector[2];

		double d = Framed.p.leftVector[0];
		double e = Framed.p.leftVector[1];
		double f = Framed.p.leftVector[2];

		double g = Framed.p.upVector[0];
		double h = Framed.p.upVector[1];
		double j = Framed.p.upVector[2]; //sorry

		//Yes, crappy naming conventionsm I'm sorry... we're storing the array to avoid the expensive of indexing it
		//	Collections.sort(faces);
		Collections.sort(validFaces);
		boxes:
		for (Face someFace : validFaces) {

			int[] xPoints = new int[someFace.vertices.length];
			int[] yPoints = new int[someFace.vertices.length];
			int i = 0;
			double dist = 0.0;
			MyPoint newPoint = null;
			for (ThreeDPoint p : someFace.vertices) {

				//these determine a "distance vector" of sorts...
				//Yes, dX is misleading, this isn't a differential, d is a poor appreviation for delta x
				//or if you prefer dX means "displacement"X
				double dX = p.getX() - Framed.p.x;
				double dY = p.getY() - Framed.p.y;
				double dZ = p.getZ() - Framed.p.z;

				//all right, the following magic math deserves some explanation...
				//the matrix formed by using the three "basis vectors" coming out of our camera, as column vectors actually describes 
				//an orthagonal rotational matrix
				//i.e this abomination, describes the rotation of the camera...
				//|viewVector[0], leftVector[0], upVector[0]|
				//|vV[1]        , lV[1]        , upV[1]     |   =R
				//|vV[2}        , lV[2]        , upV[2]     |
				//to find the inverse, we just need R^T, we transpose the matrix so it's as follows
				// vV[0],vV[1]... etc.... = R^T = R^-1
				//now, R^-1 * displacement Vector = Transformed position
				double newdX = a * dX + b * dY + c * dZ;
				double newdY = d * dX + e * dY + f * dZ;
				double newdZ = g * dX + h * dY + j * dZ;

				if (newdX < 0) {
					continue boxes;
				}

				xPoints[i] = (int) (X_SIZE / 2 - (newdY / newdX) * X_SIZE / 2);
				yPoints[i] = (int) (Y_SIZE / 2 - (newdZ / newdX) * Y_SIZE / 2);
				i++;

			}

			int r = (int) (someFace.r - someFace.dist() * DARK);
			int green = (int) (someFace.g - someFace.dist() * DARK);
			int bl = (int) (someFace.b - someFace.dist() * DARK);

			//darken each channel and force it to be above zero
			gr.setColor(new Color(r > 0 ? r : 0, green > 0 ? green : 0, bl > 0 ? bl : 0));

			gr.fillPolygon(xPoints, yPoints, 4);

		}

	}

	public static boolean isValidFace(Face f) {
		double dotProd = Framed.p.viewVector[0] * (f.vertices[0].x - Framed.p.x) + Framed.p.viewVector[1] * (f.vertices[0].y - Framed.p.y) + Framed.p.viewVector[2] * (f.vertices[0].z - Framed.p.z);
		return f.dist() < renderDist * SCALE && (dotProd > 0);
	}

}
