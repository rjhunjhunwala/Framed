/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

/**
 *
 * @author rohan
 */
public class ThreeDPoint {
	private double x; 
	private double y;
	private double z;
	ThreeDPoint(double inX,double inY,double inZ){
		x = inX;
		y = inY;
		z = inZ;
	}

	public String toString(){
		return "("+x+","+y+","+z+")";
	}
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

}
