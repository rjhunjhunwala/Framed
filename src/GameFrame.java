/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import static framed.GamePanel.X_SIZE;
import static framed.GamePanel.Y_SIZE;
import static framed.GamePanel.faces;
import static framed.GamePanel.renderDist;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javax.swing.JFrame;

/**
 *
 * @author rohan
 */
public class GameFrame extends JFrame {

	@Override
	public void repaint() {
		super.repaint();
	}

	public static class TestPanel extends JFXPanel {

		public TestPanel() {
			this.setBackground(Color.black);
			this.addKeyListener(new Controller());
			this.addMouseListener(null);
			this.addMouseListener(null);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(GamePanel.X_SIZE, GamePanel.Y_SIZE);
		}

		@Override
		public void paintComponent(Graphics graphic) {
			super.paintComponent(graphic);

			BufferedImage zBuff = new BufferedImage(X_SIZE, Y_SIZE, BufferedImage.TYPE_INT_RGB);
			Graphics zBuffGraphics = zBuff.getGraphics();

			StackPane root = new StackPane();
			Scene gameScene = new Scene(root, GamePanel.X_SIZE, GamePanel.Y_SIZE);
			Rectangle bg = new Rectangle();
			bg.setFill(javafx.scene.paint.Color.BLACK);

			bg.setWidth(GamePanel.X_SIZE);
			bg.setHeight(GamePanel.Y_SIZE);
			root.getChildren().add(bg);
			int numValidFaces = 0;

			for (Face f : faces) {
				if (GamePanel.isValidFace(f)) {
					numValidFaces++;
				}
			}
			ArrayList<Face> validFaces = new ArrayList<>(numValidFaces);
			//ugly, but blazing fast...
			for (Face f : faces) {
				if (GamePanel.isValidFace(f)) {
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

					xPoints[i] = (int) (X_SIZE / 2 - (newdY / newdX) * GamePanel.X_SIZE / 2);
					yPoints[i] = (int) (Y_SIZE / 2 - (newdZ / newdX) * GamePanel.Y_SIZE / 2);
					i++;

				}
				double dist = someFace.dist();
				int blue = (int) (255 * ((1 - Math.exp(-dist * 1.25 / renderDist))));
				zBuffGraphics.setColor(new Color(0, 0, blue));
				zBuffGraphics.fillPolygon(xPoints, yPoints, 4);
			}

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

					xPoints[i] = (int) (-(newdY / newdX) * GamePanel.X_SIZE / 2);
					yPoints[i] = (int) (-(newdZ / newdX) * GamePanel.Y_SIZE / 2);
					i++;

				}
				double dist = someFace.dist();
				double darken = ((1 - Math.exp(-dist * 1.25 / renderDist)));
				int blueness = (int) (darken * 260);

				boolean obscured = true;
				int onScreen = 0;

				for (int point = 0; point < 4; point++) {
					int xPoint = xPoints[point] + X_SIZE / 2;
					int yPoint = yPoints[point] + Y_SIZE / 2;
					if (xPoint >= 0 && xPoint < X_SIZE && yPoint >= 0 && yPoint < Y_SIZE) {
						obscured &= !(blueness >= (zBuff.getRGB(xPoint, yPoint) & 255));
						onScreen++;
					}

				}

				if (!obscured || (onScreen < 4)) {
					PerspectiveTransform perspectiveTransform = new PerspectiveTransform();
					perspectiveTransform.setUlx(xPoints[0]);
					perspectiveTransform.setUly(yPoints[0]);
					perspectiveTransform.setUrx(xPoints[1]);
					perspectiveTransform.setUry(yPoints[1]);
					perspectiveTransform.setLrx(xPoints[2]);
					perspectiveTransform.setLry(yPoints[2]);
					perspectiveTransform.setLlx(xPoints[3]);
					perspectiveTransform.setLly(yPoints[3]);

					ImageView iV = new ImageView(GamePanel.IMAGE);
					iV.setEffect(perspectiveTransform);
					root.getChildren().add(iV);
				Rectangle rect = new Rectangle();
				rect.setFill(new javafx.scene.paint.Color(0,0,0,darken));
			 rect.setWidth(GamePanel.TEXTURE_SIZE);
				rect.setHeight(GamePanel.TEXTURE_SIZE);
				rect.setEffect(perspectiveTransform);
			  root.getChildren().add(rect);
				}
			}

			GameFrame.jFXPanel.setScene(gameScene);
		}

	}
	public static TestPanel jFXPanel;

	public static GamePanel gamePanel = new GamePanel();

	public GameFrame() {
		super("Framed");
		this.setResizable(false);
		this.add(gamePanel);
		//this.add(jFXPanel = new TestPanel());
		this.pack();
		this.addKeyListener(new Controller());
		this.addMouseListener(null);
		this.addMouseMotionListener(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(Color.black);

		//Platform.runLater(new ReRenderRunnable());
	}
}
