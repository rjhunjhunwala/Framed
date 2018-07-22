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
public class Trig {
	/**
		* Return the product of a nxn matrix with an n height column matrix  
		* @param A
		* @param b
		* @return 
		*/
	public static final double[] product(double[][]a , double[]b){
		double[] out = new double[b.length];
		for(int i = 0;i<out.length;i++){
			out[i] = dot(a[i],b);
			
		}
		return out;
	}
	
	public static final double[] rotate(double[] input, double[] axis, double theta){
			double uX = axis[0];
			double uY = axis[1];
			double uZ = axis[2];
			double cosT = Math.cos(theta);
			double sinT = Math.sin(theta);
		double[][] rotationArray = new double[][]{
			{cosT+uX*uX*(1-cosT),uX*uY*(1-cosT)-uZ*sinT,uX*uZ*(1-cosT)+uY*sinT},
			{uY*uX*(1-cosT)+uZ*sinT,cosT+uY*uY*(1-cosT),uY*uZ*(1-cosT)-uX*sinT},
			{uZ*uX*(1-cosT)-uY*sinT, uZ*uY*(1-cosT)+uX*sinT, cosT + uZ*uZ*(1-cosT)},
		};
		//System.out.println(Arrays.deepToString(rotationArray));
		return product(rotationArray, input);
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
