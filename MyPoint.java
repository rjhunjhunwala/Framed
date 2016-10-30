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
public class MyPoint{
 private double x;
	private double y;
	MyPoint(double inX, double inY){
		x = inX;
		y = inY;
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
	* Copy 
	*/
public MyPoint getCopy(){
	return new MyPoint(x,y);
}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
		hash = 37 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
		return hash;
	}
	@Override
	public boolean equals(Object o){
	return ((MyPoint)o).equals(this);
}
}
