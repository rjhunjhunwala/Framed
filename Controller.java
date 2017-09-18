/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 *
 * @author rohan
 */
public class Controller implements KeyListener{
public static final double SPEED = .25;
	@Override
	public void keyTyped(KeyEvent e) {
//nil
	}

	@Override
	public void keyPressed(KeyEvent e) {
double x=Framed.p.x,y=Framed.p.y,z=Framed.p.z;
switch(e.getKeyCode()){
	case KeyEvent.VK_UP:
	case KeyEvent.VK_W:
x+=Math.cos(Framed.p.yXAngle)*SPEED;
y+=Math.sin(Framed.p.yXAngle)*SPEED;
break;
	case KeyEvent.VK_DOWN:
	case KeyEvent.VK_S:
	x-=Math.cos(Framed.p.yXAngle)*SPEED;
y-=Math.sin(Framed.p.yXAngle)*SPEED;
break;		
			case KeyEvent.VK_LEFT:
	case KeyEvent.VK_A:
x+=Math.cos(Framed.p.yXAngle+Math.PI/2)*SPEED;
	y+=Math.sin(Framed.p.yXAngle+Math.PI/2)*SPEED;
break;
			case KeyEvent.VK_RIGHT:
	case KeyEvent.VK_D:

	x+=Math.cos(Framed.p.yXAngle-Math.PI/2)*SPEED;
y+=Math.sin(Framed.p.yXAngle-Math.PI/2)*SPEED;
		break;

	case KeyEvent.VK_Q:
			Framed.p.yXAngle+=Math.PI/8;
		Framed.p.yXAngle%=2*Math.PI;
playerVectorFix();
		break;
	case KeyEvent.VK_E:
	
		Framed.p.yXAngle+=15*Math.PI/8;
		Framed.p.yXAngle%=2*Math.PI;
playerVectorFix();
		break;
	case KeyEvent.VK_Z:
		
		Framed.p.zXAngle+=47*Math.PI/24;
		Framed.p.zXAngle%=2*Math.PI;
	playerVectorFix();
		break;		
	case KeyEvent.VK_C:
		
		Framed.p.zXAngle+=1*Math.PI/24;
		Framed.p.zXAngle%=2*Math.PI;
	playerVectorFix();
		break;
	case KeyEvent.VK_SPACE:
z+=.3;
	break;
	case KeyEvent.VK_X:
z-=.3;
	break;
	case KeyEvent.VK_R:
		GamePanel.renderDist++;
		GamePanel.DARK = 180/GamePanel.SCALE/GamePanel.renderDist;
	
		break;
	case KeyEvent.VK_V:
GamePanel.renderDist--;
		GamePanel.DARK = 180/GamePanel.SCALE/GamePanel.renderDist;		
		break;
}
if(Map.threeD?Maze.maze[0][(int)x/(GamePanel.SCALE)][(int)(y/GamePanel.SCALE)][(int)(z/GamePanel.SCALE)]==1:(int) z!=0||Map.maze[(int) (x/GamePanel.SCALE)][(int) (y/GamePanel.SCALE)]){
	Framed.p.z = z;
									Framed.p.x= x;
									Framed.p.y = y;
}
	}

	@Override
	public void keyReleased(KeyEvent e) {
//nil
	}
	
	
	public static void playerVectorFix(){
		System.out.println(Framed.p.yXAngle+"|"+Framed.p.yXAngle);
	
	 Framed.p.viewVector = new double[]{Math.cos(Framed.p.zXAngle)*Math.cos(Framed.p.yXAngle),Math.cos(Framed.p.zXAngle)*Math.sin(Framed.p.yXAngle),Math.sin(Framed.p.zXAngle)};
	System.out.println(Arrays.toString(Framed.p.viewVector));
	}
}
