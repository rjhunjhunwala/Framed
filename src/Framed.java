/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.util.Arrays;

/**
 *
 * @author rohan
 */
public class Framed {
	public static final Player p = new Player();
static GameFrame mainFrame = new GameFrame();
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Map.genMap();
//for(int[] i:Map.heightMap){
	//System.out.println(Arrays.toString(i));
//}
			//p.zXAngle = Math.PI/192;
			//p.	yXAngle = Math.PI/4+Math.PI/192;
				//System.out.println(Arrays.toString(Framed.p.viewVector));
if(Map.surface){
	Framed.p.x = Map.heightMap.length/2;
	Framed.p.y = Map.heightMap[0].length/2;
	//Framed.p.z = Map.HILLS + 1;
}

				for(;;){

	mainFrame.repaint();
}
	}
	
}
