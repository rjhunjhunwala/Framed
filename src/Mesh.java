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
public class Mesh {

	LinkedList<ThreeDPoint> vertices = new LinkedList<ThreeDPoint>();

	public Mesh(LinkedList<ThreeDPoint> list) {
		for (ThreeDPoint p : list) {
			vertices.add(p);
		}
	}

	public static Mesh getCubeMesh(double x, double y, double z) {
		LinkedList<ThreeDPoint> l = new LinkedList<>();
		l.add(new ThreeDPoint(x, y, z));
		l.add(new ThreeDPoint(x, y + 1, z));
		l.add(new ThreeDPoint(x + 1, y + 1, z));
		l.add(new ThreeDPoint(x + 1, y, z));
		l.add(new ThreeDPoint(x, y, z));
		l.add(new ThreeDPoint(x, y, z - 1));
		l.add(new ThreeDPoint(x + 1, y, z - 1));
		l.add(new ThreeDPoint(x + 1, y + 1, z - 1));
		l.add(new ThreeDPoint(x, y + 1, z - 1));
		l.add(new ThreeDPoint(x, y, z - 1));

		l.add(new ThreeDPoint(x + 1, y, z));
		l.add(new ThreeDPoint(x + 1, y, z - 1));
		l.add(new ThreeDPoint(x + 1, y + 1, z));

		l.add(new ThreeDPoint(x + 1, y + 1, z - 1));
		l.add(new ThreeDPoint(x, y + 1, z));
		l.add(new ThreeDPoint(x, y + 1, z - 1));
		l.add(new ThreeDPoint(x, y, z));

		//create a cube with cross bars		
		//System.out.println(l);
		return new Mesh(l);
	}

	public static Mesh getSimpleCubeMesh(double x, double y, double z) {
		LinkedList<ThreeDPoint> l = new LinkedList<>();
		l.add(new ThreeDPoint(x, y, z));
		l.add(new ThreeDPoint(x, y + 1, z));
		l.add(new ThreeDPoint(x + 1, y + 1, z));
		l.add(new ThreeDPoint(x + 1, y, z));
		l.add(new ThreeDPoint(x, y, z));
		l.add(new ThreeDPoint(x, y, z + 1));
		l.add(new ThreeDPoint(x, y + 1, z + 1));
		l.add(new ThreeDPoint(x, y + 1, z));
		l.add(new ThreeDPoint(x, y + 1, z + 1));
		l.add(new ThreeDPoint(x + 1, y + 1, z + 1));
		l.add(new ThreeDPoint(x + 1, y + 1, z));
		l.add(new ThreeDPoint(x + 1, y + 1, z + 1));
		l.add(new ThreeDPoint(x + 1, y, z + 1));
		l.add(new ThreeDPoint(x + 1, y, z));
		l.add(new ThreeDPoint(x + 1, y, z + 1));

		//create a cube with cross bars		
		//System.out.println(l);
		return new Mesh(l);
	}

	public static Mesh getPyramidMesh(int pX, int pY, double pZ) {
		LinkedList<ThreeDPoint> l = new LinkedList<>();
		l.add(new ThreeDPoint(pX, pY, pZ));
		l.add(new ThreeDPoint(pX - 1, pY - 1, pZ - 2));
		//l.add(new ThreeDPoint(pX-1, pY+1, pZ-2));
		//l.add(new ThreeDPoint(pX+1, pY+1, pZ-2));
		//l.add(new ThreeDPoint(pX+1, pY-1, pZ-2));
		return new Mesh(l);
	}

	public static Mesh getPyramidMesh() {
		return null;
	}
}
