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
	public double yXAngle = 0;
	public double zXAngle = 0;
//public double x=GamePanel.SCALE+.01,y=GamePanel.SCALE+.01,z=Map.threeD?GamePanel.SCALE+.01:0.01;
public double x =Maze.size*SCALE-3,y= Maze.size*SCALE-3,z=Maze.size*SCALE-3;
}
