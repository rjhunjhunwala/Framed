/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.awt.List;
import java.util.LinkedList;

/**
 *
 * @author rohan
 */
public class FaceMesh {

	LinkedList<Face> meshes = new LinkedList<>();

	public FaceMesh(LinkedList<Face> list) {
		for (Face m: list) {
			meshes.add(m);
		}
	}
public static FaceMesh getCube(double x,double y,double z){
LinkedList<Face> faces = new LinkedList<>();
faces.add(Face.getSquareFace(x, y, z, 0));
faces.add(Face.getSquareFace(x, y, z, 1));
faces.add(Face.getSquareFace(x, y, z, 2));
faces.add(Face.getSquareFace(x, y, z+1, 0));
faces.add(Face.getSquareFace(x, y+1, z, 1));
faces.add(Face.getSquareFace(x+1, y, z, 2));
return new FaceMesh(faces);
}

}
