/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author rohan
 */
public class Controller implements KeyListener{
public static final double SPEED = .125;
	@Override
	public void keyTyped(KeyEvent e) {
//nil
	}

	@Override
	public void keyPressed(KeyEvent e) {

switch(e.getKeyCode()){
	case KeyEvent.VK_UP:
	case KeyEvent.VK_W:
Framed.p.x+=SPEED;
		break;
	case KeyEvent.VK_DOWN:
	case KeyEvent.VK_S:
		Framed.p.x-=SPEED;
break;		
			case KeyEvent.VK_LEFT:
	case KeyEvent.VK_A:
		Framed.p.y+=SPEED;
break;
			case KeyEvent.VK_RIGHT:
	case KeyEvent.VK_D:
		Framed.p.y-=SPEED;
break;

	case KeyEvent.VK_Q:
		Framed.p.yXAngle+=Math.PI/8;
break;
	case KeyEvent.VK_E:
		Framed.p.yXAngle-=Math.PI/8;
break;
}
	}

	@Override
	public void keyReleased(KeyEvent e) {
//nil
	}
	
	
	
}
