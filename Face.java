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
public class Face {

ThreeDPoint[] vertices;

	public Face(LinkedList<ThreeDPoint> list) {
		vertices = new ThreeDPoint[list.size()];
		for(int i = 0;i<vertices.length;i++){
			vertices[i] = list.get(i);
		}
	}


}
