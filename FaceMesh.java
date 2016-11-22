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


}
