/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.awt.Color;
import static java.awt.Color.green;
import java.awt.List;
import java.util.LinkedList;

/**
 *
 * @author rohan
 */
public class Face implements Comparable{
public static final boolean RANDOM = true;
public static final int HARD_CODED_R = 255;
public static final int HARD_CODED_G = 255;
public static final int HARD_CODED_B = 255;
ThreeDPoint[] vertices;
int r = RANDOM?(int) (Math.random()*256):HARD_CODED_R;
int g = RANDOM?(int) (Math.random()*256):HARD_CODED_G;
int b = RANDOM?(int) (Math.random()*256):HARD_CODED_B;
	
public Face(LinkedList<ThreeDPoint> list) {
		vertices = new ThreeDPoint[list.size()];
		for(int i = 0;i<vertices.length;i++){
			vertices[i] = list.get(i);
		}
	}
public Face(double...inputs){
	vertices = new ThreeDPoint[inputs.length/3];
	for(int i = 0;i<inputs.length;i+=3){
		vertices[i/3] = new ThreeDPoint(inputs[i],inputs[i+1],inputs[i+2]);
	}
}
public Face(Color inColor,double...inputs){
this(inputs);
r = inColor.getRed();
g = inColor.getGreen();
b = inColor.getBlue();
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
	 * @param inColor
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
public double[] getAverage(){
		double averageX = 0;
	double averageY = 0;
	double averageZ = 0;

	for(ThreeDPoint p:vertices){
		averageX+=p.x;
		averageY+=p.y;
		averageZ+=p.z;
	}
	averageX/=vertices.length;
	averageY/=vertices.length;
	averageZ/=vertices.length;
	return new double[]{averageX,averageY,averageZ};
}

	public double dist(){
double[] average = getAverage();
	double dX = average[0]-Framed.p.x;
	double dY = average[1]-Framed.p.y;
		double dZ = average[2]-Framed.p.z;
	return Math.sqrt(dX*dX+dY*dY+dZ*dZ);
}


}
