/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;


import java.util.Arrays;
import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javax.swing.SwingUtilities;

/**
 *
 * @author rohan
 */
public class Framed {
	public static final Player p = new Player();
static GameFrame mainFrame;
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception{
SwingUtilities.invokeLater(new Runnable(){
	@Override
	public void run(){
		mainFrame = new GameFrame();
	}
});
if(Map.surface){
	if(Map.SPHERE){
		Framed.p.x = .0;
		Framed.p.y = 0;
		Framed.p.z = .01;
	}else{
		Framed.p.x = Map.heightMap.length/2;
		Framed.p.y = Map.heightMap[0].length/2;
		Framed.p.z = 0;
	}
}

//new Thread(new ReRenderThread()).start();
Thread.sleep(1000);
				for(;;){


	mainFrame.repaint();
}
	}
	
}
