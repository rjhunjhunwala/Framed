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
public class Trig {
	/**
		* This method warrants a special explanation...
		* It is a slightly modified version of Math.atan with a domain of [0,Math.2PI)
		* The idea is to get the actual angle as opposied to a possible one.
		*/
	public static double atan(double O,double A){
		double angle = Math.atan(O/A);
		if(A<0){
			angle+=Math.PI;
		}
		return angle;
	}
}
