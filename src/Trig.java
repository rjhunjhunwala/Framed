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
		* The idea is to get the actual angle as opposed to a possible one.
		*/
	public static double atan(double O,double A){
		double angle = Math.atan(O/A);
		if(A<0){
			angle+=Math.PI;
		}
		if(O<0&&A>0){
			angle+=2*Math.PI;
		}
		return angle;
	}
	/**
		* Gets an angle between two vectors in 3 dimensional space.
		* Essentially acos(v1 dot v2 / |v1||v2|)
		* Unrolling everything into one expression to shave out a costly loop, I'm unsure if the compiler would unroll
		* This project is slow as molasses already. 
		*/
	public static double angle(double[] v1, double[] v2){

		return Math.acos((dot(v1,v2))/(Math.sqrt(v1[0]*v1[0]+v1[1]*v1[1]+v1[2]*v1[2])*Math.sqrt(v2[0]*v2[0]+v2[1]*v2[1]+v2[2]*v2[2])));

	}
	/**
		* Dot product
		* @param v1
		* @param v2
		* @return 
		*/
	public static double dot(double[] v1, double[]v2){
		return v1[0]*v2[0]+v1[1]*v2[1]+v1[2]*v2[2];
	}
}
