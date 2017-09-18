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
import javax.swing.JPanel;

/**
 *
 * @author rohan
 */
public class GamePanel extends JPanel {

	static int X_SIZE = 640;
	static int Y_SIZE = 640;
	static int X_MID = X_SIZE / 2;
	static int Y_MID = Y_SIZE / 2;
	static final int SCALE = 2;
	static ArrayList<FaceMesh> meshes = new ArrayList<>();
	static ArrayList<Face> faces;

	static {
		Map.makeMaze();
		if (Map.threeD) {
			for (int i = 0; i < Maze.size; i++) {
				for (int j = 0; j < Maze.size; j++) {
					for (int k = 0; k < Maze.size; k++) {
						
if (Maze.maze[0][i][j][k] == 0) {
							for (int l = 0; l < SCALE; l++) {
								for (int m = 0; m < SCALE; m++) {
									for (int n = 0; n < SCALE; n++) {
										meshes.add(FaceMesh.getCube(i * SCALE + l, j * SCALE + m, k * SCALE + n));
									}
								}
							}

						}
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
		int b = (int) (Math.random() * 100);
		int g = (int) (Math.random() * 100);
		//meshes.add(FaceMesh.getCube(Maze.size * SCALE - 3.5, Maze.size * SCALE - 3.5, Maze.size * SCALE - 3.5));
		for (Face f : meshes.get(meshes.size() - 1).meshes) {
			f.b = b;
			f.g = g;
			f.r = 255;
		}
		faces = new ArrayList<>(meshes.size() * 4);

		for (FaceMesh m : meshes) {
			for (Face f : m.meshes) {
				faces.add(f);
			}
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(X_SIZE, Y_SIZE);
	}
		public static int renderDist = 5;
	public static int DARK = 180 / SCALE / renderDist;
	public static final boolean EDGES = false;

	
	public void paintComponent2(Graphics g) {
		super.paintComponent(g);
		Collections.sort(faces);
		
		boxes:
		for (Face f : faces) {

			int[] xPoints = new int[f.vertices.length];
			int[] yPoints = new int[f.vertices.length];
			int i = 0;
			double dist = 0.0;
			MyPoint newPoint = null;
			for (ThreeDPoint p : f.vertices) {

				double dX = p.getX() - Framed.p.x;
				double dY = p.getY() - Framed.p.y;
				double dZ = p.getZ() - Framed.p.z;
				
				double[] disp = new double[] {dX,dY,dZ};
				double[] view = Framed.p.viewVector;
			 double[] disp2 = new double[]{dX,dY,0};
				double[] view2 = new double[]{Framed.p.viewVector[0],Framed.p.viewVector[1],0};
				
				
				
				double xOff=0; 
				double yOff=0;
			
				double xL = (X_MID - xOff);
				double yL = (Y_MID - yOff);
				newPoint = new MyPoint(xL, yL);


				xPoints[i] = (int) newPoint.getX();
				yPoints[i] = (int) newPoint.getY();
				i++;

			}
			
			int r = (int) (f.r - f.dist() * DARK);
			int gr = (int) (f.g - f.dist() * DARK);
			int b = (int) (f.b - f.dist() * DARK);
			
			//darken each channel and force it to be above zero
			g.setColor(new Color(r > 0 ? r : 0, gr > 0 ? gr : 0, b > 0 ? b : 0));

			g.fillPolygon(xPoints, yPoints, 4);

		}
		
		if(EDGES){
			drawEdges(g);
		}


		
		
	}
	
	public void drawEdges(Graphics g){
		boxes:
			for (Face f : faces) {

				int[] xPoints = new int[f.vertices.length];
				int[] yPoints = new int[f.vertices.length];
				int i = 0;
				double dist = 0.0;
				MyPoint newPoint = null;
				for (ThreeDPoint p : f.vertices) {

					double dX = p.getX() - Framed.p.x;
					double dY = p.getY() - Framed.p.y;
					double dZ = p.getZ() - Framed.p.z;
						if (Math.abs(dX) > renderDist*SCALE/2 || Math.abs(dY) > renderDist*SCALE/2 || Math.abs(dZ) > renderDist*SCALE/2) {
							continue boxes;
						}
					double vAngle = Trig.atan(dY, dX);
					double vAngle2 = Trig.atan(dZ, Math.sqrt(dX * dX + dY * dY));
					double YXangleDiff = vAngle - Framed.p.yXAngle;
					double ZXangleDiff = vAngle2 - Framed.p.zXAngle;
					double xOff = Math.tan(YXangleDiff) * X_SIZE / 2;
					double yOff = Math.tan(ZXangleDiff) * Y_SIZE / 2 / Math.cos(YXangleDiff) / Math.cos(Framed.p.zXAngle);
					double xL = (X_MID - xOff);
					double yL = (Y_MID - yOff);
					newPoint = new MyPoint(xL, yL);
					boolean behind = Math.abs(Math.PI - Math.abs(vAngle - Framed.p.yXAngle)) < Math.PI / 2;

					if (Math.abs(Math.tan(YXangleDiff)) > 10 || behind) {
						continue boxes;
					}

					xPoints[i] = (int) newPoint.getX();
					yPoints[i] = (int) newPoint.getY();
					i++;

				}

				int b = (int) (255 - f.dist() * DARK);
				g.setColor(new Color(0, 0, b > 0 ? b : 0));

				g.drawPolygon(xPoints, yPoints, 4);

			}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Collections.sort(faces);
		
		boxes:
		for (Face f : faces) {

			int[] xPoints = new int[f.vertices.length];
			int[] yPoints = new int[f.vertices.length];
			int i = 0;
			double dist = 0.0;
			MyPoint newPoint = null;
			for (ThreeDPoint p : f.vertices) {

				double dX = p.getX() - Framed.p.x;
				double dY = p.getY() - Framed.p.y;
				double dZ = p.getZ() - Framed.p.z;
				
				if (Math.abs(dX)*Math.abs(dX) + Math.abs(dY)*Math.abs(dY) + Math.abs(dZ)*Math.abs(dZ) > renderDist*SCALE*4*SCALE*renderDist) {
					continue boxes;
				}
										//	double vAngle2 =Math.PI/2- Math.acos(dZ/Math.sqrt(dX*dX+dY*dY+dZ*dZ));
		double[] displacement = new double[]{dX,dY,dZ};
		
		double spaceAngle = Trig.angle(displacement,Framed.p.viewVector);
		
		//System.out.println(Arrays.toString(Framed.p.viewVector));
		double circleRad = Math.tan(spaceAngle)*Y_SIZE/2;
	
						
				double[] disp = new double[] {dX,dY,dZ};
				double[] view = Framed.p.viewVector;
			 double[] disp2 = new double[]{dX,dY,0};
				double[] view2 = new double[]{Framed.p.viewVector[0],Framed.p.viewVector[1],0};
		double yAngle = Trig.angle(disp2, view2);
		
		
		double objYXAngle = Trig.atan(dY, dX);
		
//objYXAngle = Trig.angle(new double[]{dX,dY,0},new double[]{Framed.p.viewVector[0],Framed.p.viewVector[1],0});
		
				double YXangleDiff = objYXAngle-Framed.p.yXAngle;
		//System.out.println(yAngle - YXangleDiff);
				boolean behind = Math.abs(Math.PI - Math.abs(objYXAngle - Framed.p.yXAngle)) < Math.PI / 2;
				if (Math.abs(Math.tan(YXangleDiff)) > 10 || behind) {
					continue boxes;
				}

		
        
			double ZXangleDiff = Math.asin(dZ/Math.sqrt(dZ*dZ+dX*dX+dY*dY)) - Framed.p.zXAngle;
			
			double tanYX = Math.tan(objYXAngle);
				double xOff = tanYX * X_SIZE / 2;
						double yOff = Math.tan(ZXangleDiff) * Y_SIZE / 2 / Math.cos(YXangleDiff);
						
				double xOff2 = Math.sqrt(circleRad*circleRad-yOff*yOff);

	double yOff2 = Math.sqrt(circleRad* circleRad-xOff*xOff);
				double xL = (X_MID - xOff);
				double yL = (Y_MID - yOff2*(dZ>0?1:-1));
				newPoint = new MyPoint(xL, yL);


				xPoints[i] = (int) newPoint.getX();
				yPoints[i] = (int) newPoint.getY();
				i++;

			}
			
			int r = (int) (f.r - f.dist() * DARK);
			int gr = (int) (f.g - f.dist() * DARK);
			int b = (int) (f.b - f.dist() * DARK);
			
			//darken each channel and force it to be above zero
			g.setColor(new Color(r > 0 ? r : 0, gr > 0 ? gr : 0, b > 0 ? b : 0));

			g.fillPolygon(xPoints, yPoints, 4);

		}
		
		if(EDGES){
			drawEdges(g);
		}


		
		
	}


	public void paintComponent1(Graphics g) {

		super.paintComponent(g);
		g.setColor(Color.green);
		g.drawString(Framed.p.x + "|" + Framed.p.y + "|" + Framed.p.z + "|" + Math.toDegrees(Framed.p.yXAngle), 0, 20);

		for (FaceMesh m : meshes) {
			if (m != null) {

				MyPoint oldPoint = null;
				for (ThreeDPoint p : m.meshes.get(0).vertices) {

					MyPoint newPoint = null;
					double dX = p.getX() - Framed.p.x;
					double dY = p.getY() - Framed.p.y;
					double dZ = p.getZ() - Framed.p.z;
					if (Math.abs(dX) > 20 || Math.abs(dY) > 20 || Math.abs(dZ) > 20) {
						continue;
					}
					double vAngle = Trig.atan(dY, dX);
					double vAngle2 = Trig.atan(dZ, Math.sqrt(dX * dX + dY * dY));
					//System.out.println(dX+"|"+dY+"|"+dZ);
					double YXangleDiff = vAngle - Framed.p.yXAngle;
					double ZXangleDiff = vAngle2 - Framed.p.zXAngle;
					double xOff = Math.tan(YXangleDiff) * X_SIZE / 2;
					double yOff = Math.tan(ZXangleDiff) * Y_SIZE / 2 / Math.cos(YXangleDiff);
					double xL = (X_MID - xOff);
					double yL = (Y_MID - yOff);
					newPoint = new MyPoint(xL, yL);
//||((Math.cos(Framed.p.yXAngle)>0!=Math.cos(objYXAngle)>0)&&(Math.sin(Framed.p.yXAngle)<0!=Math.sin(objYXAngle)<0))
					boolean behind = Math.abs(Math.PI - Math.abs(vAngle - Framed.p.yXAngle)) < Math.PI / 2;
//behind = (Math.sin(Framed.p.yXAngle)<0!=Math.sin(objYXAngle)<0)&&(Math.cos(objYXAngle)<0!=Math.cos(Framed.p.yXAngle)<0)&&(Math.sin(Framed.p.zXAngle)<0!=Math.sin(vAngle2)<0);	
					if (Math.abs(Math.tan(YXangleDiff)) > 3 || behind) {
						continue;
					}
					g.drawRect((int) newPoint.getX(), (int) newPoint.getY(), 0, 0);

					if (oldPoint != null) {
						g.drawLine((int) oldPoint.getX(), (int) oldPoint.getY(), (int) newPoint.getX(), (int) newPoint.getY());
					}
					oldPoint = newPoint.getCopy();
				}
			}
		}
	}

	public static class Holder implements Comparable {

		public Mesh m;
		public double dist;

		public Holder(double inDist, Mesh inMesh) {
			dist = inDist;
			m = inMesh;
		}

		public int compareTo(Holder h) {
			return (int) (100 * (h.dist - this.dist));
		}

		@Override
		public int compareTo(Object o) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}
	}

	public GamePanel() {
		this.setBackground(Color.black);
	}

}
