/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.awt.Color;
import java.awt.List;
import java.util.LinkedList;

/**
 *
 * @author rohan
 */
public class Face implements Comparable{

ThreeDPoint[] vertices;
int r = 0;
int g = 255;
int b = 0;
	public Face(LinkedList<ThreeDPoint> list) {
		vertices = new ThreeDPoint[list.size()];
		for(int i = 0;i<vertices.length;i++){
			vertices[i] = list.get(i);
		}
	}
	public Face(LinkedList<ThreeDPoint> list, int r, int g, int b){
		this(list);
		this.r = r;
		this.g = g;
		this.b = b;
	}
	/**
		* Gets a square face originate at a given coordinate
		* @param x
		* @param y
		* @param z
		* @param orientation 0 = positive x, positive y, 1 = positive x, positive z, 2 = positive y,z
		* @return 
		*/
	public static Face getSquareFace(double x,double y,double z, int orientation){
		LinkedList<ThreeDPoint> list = new LinkedList<>();
		list.add(new ThreeDPoint(x,y,z));
switch(orientation){
	case 0:
	list.add(new ThreeDPoint(x+1,y,z));
		break;
	case 1:
			list.add(new ThreeDPoint(x+1,y,z));
		break;
	case 2:
			list.add(new ThreeDPoint(x,y+1,z));
}
switch(orientation){
	case 0:
			list.add(new ThreeDPoint(x+1,y+1,z));
		break;
	case 1:
			list.add(new ThreeDPoint(x+1,y,z+1));
		break;
	case 2:
			list.add(new ThreeDPoint(x,y+1,z+1));
}
switch(orientation){
	case 0:
			list.add(new ThreeDPoint(x,y+1,z));
		break;
	case 1:
			list.add(new ThreeDPoint(x,y,z+1));
		break;
	case 2:
					list.add(new ThreeDPoint(x,y,z+1));
}
		return new Face(list);
	}

	@Override
	public int compareTo(Object o) {

	return (int) (1000000*((((Face) o).dist())-this.dist()));

		}
public double dist(){
	double dX = this.vertices[0].getX()-Framed.p.x;
	double dY = this.vertices[0].getY()-Framed.p.y;
		double dZ = this.vertices[0].getZ()-Framed.p.z;
	return Math.sqrt(dX*dX+dY*dY+dZ*dZ);
}


}
