/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javax.imageio.ImageIO;
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
	static final int SCALE = 1;

	public static final int MAGIC_NUMBER = 0;

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
				for (int i = 0; i < Map.heightMap.length; i++) {
					for (int j = 0; j < Map.heightMap.length; j++) {
						Map.heightMap[i][j] = 0;
					}
				}

				if (Map.SPHERE) {
					Map.heightMap = new double[Map.RESOLUTION + 2][Map.RESOLUTION + 2];
					for (int i = 0; i < Map.RESOLUTION + 1; i++) {
						for (int j = 0; j < Map.RESOLUTION + 1; j++) {
							double x = (i - Map.RESOLUTION / 2.0) * 2 * Map.RADIUS / Map.RESOLUTION;
							double y = (j - Map.RESOLUTION / 2.0) * 2 * Map.RADIUS / Map.RESOLUTION;
							//z = +/-Math.sqrt(Radius^2-x^2-y^2) for norm<x,y> <= RADIUS...
							double discriminant = Map.RADIUS * Map.RADIUS - x * x - y * y;
							double z = discriminant > 0 ? Math.sqrt(discriminant) : discriminant * discriminant < .01 ? 0 : MAGIC_NUMBER;
							Map.heightMap[i][j] = z;
						}
					}
					faces = new ArrayList<>((Map.heightMap.length - 1) * (Map.heightMap[0].length - 1));
					for (int i = 0; i < Map.heightMap.length - 1; i++) {
						for (int j = 0; j < Map.heightMap[0].length - 1; j++) {
							double x = (i - Map.RESOLUTION / 2.0) * 2 * Map.RADIUS / Map.RESOLUTION;
							double y = (j - Map.RESOLUTION / 2.0) * 2 * Map.RADIUS / Map.RESOLUTION;

							if (getSum(Map.heightMap[i][j], Map.heightMap[i][j + 1], Map.heightMap[i + 1][j], Map.heightMap[i + 1][j + 1]) != MAGIC_NUMBER * 4) {
								LinkedList<ThreeDPoint> pointsUpper = new LinkedList<>();

								faces.add(new Face(x, y, Map.heightMap[i][j], x + 2.0 * Map.RADIUS / Map.RESOLUTION, y, Map.heightMap[i + 1][j], x + 2.0 * Map.RADIUS / Map.RESOLUTION, y + 2.0 * Map.RADIUS / Map.RESOLUTION, Map.heightMap[i + 1][j + 1], x, y + 2.0 * Map.RADIUS / Map.RESOLUTION, Map.heightMap[i][j + 1]));
								faces.add(new Face(x, y, -Map.heightMap[i][j], x + 2.0 * Map.RADIUS / Map.RESOLUTION, y, -Map.heightMap[i + 1][j], x + 2.0 * Map.RADIUS / Map.RESOLUTION, y + 2.0 * Map.RADIUS / Map.RESOLUTION, -Map.heightMap[i + 1][j + 1], x, y + 2.0 * Map.RADIUS / Map.RESOLUTION, -Map.heightMap[i][j + 1]));

							}
						}
					}
				} else {
					Map.getPerlinNoiseHeightMap();

					faces = new ArrayList<>((Map.heightMap.length - 1) * (Map.heightMap[0].length - 1));
					for (int i = 0; i < Map.heightMap.length - 1; i++) {
						for (int j = 0; j < Map.heightMap[0].length - 1; j++) {
							faces.add(new Face(i, j, Map.heightMap[i][j], i + 1, j, Map.heightMap[i + 1][j], i + 1, j + 1, Map.heightMap[i + 1][j + 1], i, j + 1, Map.heightMap[i][j + 1]));
						}
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
		}
		if (Map.threeD || !Map.surface) {
			faces = new ArrayList<>(meshes.size() * 6);

			for (FaceMesh m : meshes) {
				for (Face f : m.meshes) {
					faces.add(f);
				}
			}
		}
	}

	public static double getSum(double... group) {
		double sum = 0;
		for (double d : group) {
			sum += d;
		}

		return sum;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(X_SIZE, Y_SIZE);
	}
	public static int renderDist = Map.surface ? Map.SPHERE ? Map.RADIUS * 2 +1 : Map.heightMap.length / 6 : 5;
	public static int DARK = 240 / SCALE / renderDist;
	public static final boolean EDGES = false;

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

		//Yes, crappy naming conventions I'm sorry... we're storing the array to avoid the expensive of indexing it
		//	Collections.sort(faces);
		Collections.sort(validFaces);
		boxes:
		for (Face someFace : validFaces) {

			int[] xPoints = new int[someFace.vertices.length];
			int[] yPoints = new int[someFace.vertices.length];
			int i = 0;
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
			double dist = someFace.dist();
			int r = (int) (someFace.r * Math.exp(-dist * 2.2 / renderDist));
			int green = (int) (someFace.g * Math.exp(-dist * 2.2 / renderDist));
			int bl = (int) (someFace.b * Math.exp(-dist * 2.2 / renderDist));

			//darken each channel and force it to be above zero
			gr.setColor(new Color(r, green, bl));

			gr.fillPolygon(xPoints, yPoints, xPoints.length);

		}
	}

	public static BufferedImage TEXTURE;

	static {
		try {
			TEXTURE = ImageIO.read(new File("cobble2.png"));

		} catch (IOException ex) {
			Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static Image IMAGE = SwingFXUtils.toFXImage(TEXTURE, null);
	public static final int TEXTURE_SIZE = (int) IMAGE.getWidth();

	public void paintComponentL(Graphics gr) {
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

		//Yes, crappy naming conventions I'm sorry... we're storing the array to avoid the expensive of indexing it
		//	Collections.sort(faces);
		Collections.sort(validFaces);
		boxes:
		for (Face someFace : validFaces) {

			int[] xPoints = new int[someFace.vertices.length];
			int[] yPoints = new int[someFace.vertices.length];
			int i = 0;
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
			double dist = someFace.dist();
			int darken = (int) (255 * (1 - Math.exp(-dist * 1.5 / renderDist)));
			Point2D p0 = new Point2D.Double(xPoints[0], yPoints[0]);
			Point2D p1 = new Point2D.Double(xPoints[1], yPoints[1]);
			Point2D p2 = new Point2D.Double(xPoints[2], yPoints[2]);
			Point2D p3 = new Point2D.Double(xPoints[3], yPoints[3]);
			BufferedImage newImage = null;//todo figure this out. 
			gr.drawImage(newImage, 0, 0, X_SIZE, Y_SIZE, null);
			gr.setColor(new Color(0, 0, 0, darken));
			gr.fillPolygon(xPoints, yPoints, xPoints.length);

		}
	}

	public static boolean isValidFace(Face f) {
		double[] averageVector = f.getAverage();
		double dotProd = Framed.p.viewVector[0] * (averageVector[0] - Framed.p.x) + Framed.p.viewVector[1] * (averageVector[1] - Framed.p.y) + Framed.p.viewVector[2] * (averageVector[2] - Framed.p.z);
		return f.dist() < renderDist * SCALE && (dotProd > 0);
	}

}
