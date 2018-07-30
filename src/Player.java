/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import static framed.GamePanel.SCALE;

/**
 *
 * @author rohan
 */
public class Player {
	public double[] viewVector = {1,0,0};
	//our system is right handed.
	public double[] leftVector = {0,1,0};
 //makes sense
	public double[] upVector = {0,0,1};
public double x=Maze.size/2*GamePanel.SCALE+.01,y=Maze.size/2*GamePanel.SCALE+.01,z=Map.threeD?Maze.size/2*GamePanel.SCALE+.01:0.01;

{
	boolean kludge = !Map.threeD;
	//kludge the player where I would like
	if(!Map.threeD&&kludge){
	x= -4;
	y = -4;
	z = -4;
	//yXAngle = Math.PI/4+Math.PI/48;
	//zXAngle = Math.PI/96;
	//viewVector = new double[]{.707,.707,0};
//Controller.playerVectorFix();
	}
}
}
