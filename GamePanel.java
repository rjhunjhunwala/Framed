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
public class GamePanel extends JPanel{
	static int X_SIZE = 640;
	static int Y_SIZE = 640;
	static int X_MID = X_SIZE/2;
	static int Y_MID = Y_SIZE/2;
	static final int SCALE =3;
	static ArrayList<Mesh> meshes = new ArrayList<>();
	static{
Map.makeMaze();
//meshes.add(Mesh.getCubeMesh(5, 3, 2));
for(int i=0;i<Map.maze.length;i++){
	for(int j=0;j<Map.maze[i].length;j++){
		if(!Map.maze[i][j]){
			for(int k=0;k<SCALE;k++){
				for(int l=0;l<SCALE;l++){
								meshes.add(Mesh.getSimpleCubeMesh(i*SCALE+k, j*SCALE+l, .5));
				}
			}

		}
	}
}	
LinkedList<ThreeDPoint> test = new LinkedList<>();
//test.add(new ThreeDPoint(5,3,2));
	meshes.add(new Mesh(test));
	}
	
	@Override
	public Dimension getPreferredSize(){
	return new Dimension(X_SIZE,Y_SIZE);
}
	@Override
	public void paintComponent(Graphics g){
super.paintComponent(g);
		g.setColor(Color.white);
	g.drawString(Framed.p.x+"|"+Framed.p.y+"|"+Framed.p.z, 0, 20);
for(Mesh m:meshes){
		MyPoint oldPoint = null;
	for(ThreeDPoint p:m.vertices){

		MyPoint newPoint = null;
		double dX = p.getX()-Framed.p.x;
		double dY = p.getY()-Framed.p.y;
			double dZ = p.getZ()-Framed.p.z;
			if(dX>9||dY>9||dZ>9){
				continue;
			}
			//System.out.println(dX+"|"+dY+"|"+dZ);
			double YXangleDiff = Trig.atan(dY,dX)-Framed.p.yXAngle;
			double ZYangleDiff = Trig.atan(dZ,dX)-Framed.p.zXAngle;
			double xOff = Math.tan(YXangleDiff)*X_SIZE/2;
			double yOff = Math.tan(ZYangleDiff)*Y_SIZE/2;
			newPoint = new MyPoint(X_MID-xOff,Y_MID-yOff);
			if(Math.abs(YXangleDiff)>1||Math.abs(ZYangleDiff)>1){
				continue;
			}
			g.drawRect((int) newPoint.getX(), (int) newPoint.getY(), 0, 0);
		
if(oldPoint!=null){
g.drawLine((int) oldPoint.getX(),(int) oldPoint.getY(), (int)newPoint.getX(),(int) newPoint.getY());
}
		oldPoint=newPoint.getCopy();	
	}
}
	//System.exit(0);
	}
	public GamePanel(){
		this.setBackground(Color.black);
	}
}
