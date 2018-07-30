/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author rohan
 */
public class Controller implements KeyListener {

	public static double SPEED = .233;

	@Override
	public void keyTyped(KeyEvent e) {
//nil
	}

	@Override
	public void keyPressed(KeyEvent e) {
		double x = Framed.p.x, y = Framed.p.y, z = Framed.p.z;
		double[] pV = Framed.p.viewVector;
		double[] pLV = Framed.p.leftVector;
		double[] pUP = Framed.p.upVector;
		boolean correct = false;
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				x += pV[0] * SPEED;
				y += pV[1] * SPEED;
				z += pV[2] * SPEED;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				x -= pV[0] * SPEED;
				y -= pV[1] * SPEED;
				z -= pV[2] * SPEED;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				x += pLV[0] * SPEED;
				y += pLV[1] * SPEED;
				z += pLV[2] * SPEED;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				x -= pLV[0] * SPEED;
				y -= pLV[1] * SPEED;
				z -= pLV[2] * SPEED;
				break;

			case KeyEvent.VK_Q:
				Framed.p.viewVector = Trig.rotate(pV, pUP, Math.PI/8);
				Framed.p.leftVector = Trig.rotate(pLV, pUP, Math.PI/8);
				break;
			case KeyEvent.VK_E:
   				Framed.p.viewVector = Trig.rotate(pV, pUP, -Math.PI/8);
				Framed.p.leftVector = Trig.rotate(pLV, pUP, -Math.PI/8);

				break;
			case KeyEvent.VK_Z:
				   				Framed.p.viewVector = Trig.rotate(pV, pLV, -Math.PI/8);
				Framed.p.upVector = Trig.rotate(pUP, pLV, -Math.PI/8);
				break;
			case KeyEvent.VK_C:
   				Framed.p.viewVector = Trig.rotate(pV, pLV, Math.PI/8);
				Framed.p.upVector = Trig.rotate(pUP, pLV, Math.PI/8);

				break;
			case KeyEvent.VK_T:
   				Framed.p.upVector = Trig.rotate(pUP, pV, -Math.PI/8);
				Framed.p.leftVector = Trig.rotate(pLV, pV, -Math.PI/8);
				break;
			case KeyEvent.VK_B:
		   				Framed.p.upVector = Trig.rotate(pUP, pV, Math.PI/8);
				Framed.p.leftVector = Trig.rotate(pLV, pV, Math.PI/8);
				break;
			case KeyEvent.VK_SPACE:
				x += pUP[0] * SPEED;
				y += pUP[1] * SPEED;
				z += pUP[2] * SPEED;
				break;
			case KeyEvent.VK_X:
				x -= pUP[0] * SPEED;
				y -= pUP[1] * SPEED;
				z -= pUP[2] * SPEED;
				break;

			case KeyEvent.VK_R:
				GamePanel.renderDist++;
				GamePanel.DARK = 240 / GamePanel.SCALE / GamePanel.renderDist;

				break;
			case KeyEvent.VK_V:
				GamePanel.renderDist--;
				GamePanel.DARK = 240 / GamePanel.SCALE / GamePanel.renderDist;
				break;
			case KeyEvent.VK_Y:
				SPEED*=2;
				break;
			case KeyEvent.VK_N:
				SPEED/=2;
				break;
		}

		if (Map.surface||(Map.threeD ? Maze.maze[0][(int) x / (GamePanel.SCALE)][(int) (y / GamePanel.SCALE)][(int) (z / GamePanel.SCALE)] == 1 : (int) z != 0 || Map.maze[(int) (x / GamePanel.SCALE)][(int) (y / GamePanel.SCALE)])) {
			Framed.p.z = z;
			Framed.p.x = x;
			Framed.p.y = y;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
//nil
	}
}
