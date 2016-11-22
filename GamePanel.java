/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
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
	static final int SCALE = 3;
	static ArrayList<Mesh> meshes = new ArrayList<>();

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
										meshes.add(Mesh.getSimpleCubeMesh(i * SCALE + l, j * SCALE + m, k * SCALE + n));
									}
								}
							}

						}
					}
				}
			}
		} else {
			for (int i = 0; i < Maze.size; i++) {
				for (int j = 0; j < Maze.size; j++) {

					if (Map.maze[i][j] == false) {
						for (int l = 0; l < SCALE; l++) {
							for (int m = 0; m < SCALE; m++) {

								meshes.add(Mesh.getSimpleCubeMesh(i * SCALE + l, j * SCALE + m, 0.5));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(X_SIZE, Y_SIZE);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.drawString(Framed.p.x + "|" + Framed.p.y + "|" + Framed.p.z + "|" + Math.toDegrees(Framed.p.yXAngle), 0, 20);
		Holder[][] zBuff = new Holder[X_SIZE / 4][Y_SIZE / 4];
		for (int i = 0; i < X_SIZE / 4; i++) {
			for (int j = 0; j < Y_SIZE / 4; j++) {
				zBuff[i][j] = new Holder(1000, null);
			}
		}
		for (Mesh m : meshes) {
			ThreeDPoint p = m.vertices.get(0);
			double dX = p.getX() - Framed.p.x;
			double dY = p.getY() - Framed.p.y;
			double dZ = p.getZ() - Framed.p.z;
			if (Math.abs(dX) > 10 || Math.abs(dY) > 10 || Math.abs(dZ) > 10) {
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
			boolean behind = Math.abs(Math.PI - Math.abs(vAngle - Framed.p.yXAngle)) < Math.PI / 2;
			if (Math.abs(Math.tan(YXangleDiff)) > 3 || behind || xL >= X_SIZE || yL >= Y_SIZE) {
				continue;
			}
			double dist = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
			int buffY = (int) (yL / 4);
			int buffX = (int) (xL / 4);
			try {
				if (zBuff[buffX][buffY].dist - dist > 0) {
					zBuff[buffX][buffY] = new Holder(dist, m);
				}
			} catch (Exception ex) {
			}
		}
		for (int i = 0; i < X_SIZE / 4; i++) {
			for (int j = 0; j < Y_SIZE / 4; j++) {

				Mesh m = zBuff[i][j].m;
				if (m != null) {

					MyPoint oldPoint = null;
					for (ThreeDPoint p : m.vertices) {

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
//||((Math.cos(Framed.p.yXAngle)>0!=Math.cos(vAngle)>0)&&(Math.sin(Framed.p.yXAngle)<0!=Math.sin(vAngle)<0))
						boolean behind = Math.abs(Math.PI - Math.abs(vAngle - Framed.p.yXAngle)) < Math.PI / 2;
//behind = (Math.sin(Framed.p.yXAngle)<0!=Math.sin(vAngle)<0)&&(Math.cos(vAngle)<0!=Math.cos(Framed.p.yXAngle)<0)&&(Math.sin(Framed.p.zXAngle)<0!=Math.sin(vAngle2)<0);	
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
