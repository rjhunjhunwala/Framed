/*/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rohan
 */
public class Maze {

	/**
	 * distance the user can see Recommended size 2
	 */
	public static int window;
	/**
	 * if the size is even the maze will not be nicely contained by walls Also the
	 * "glade" in the middle will be off center. Not a big deal because the program
	 * automatically renders positions off the side of the maze as walls
	 * recommended size 11
	 */
	public static final int size=11;
	//if the user wants gremlins this is true
	/**
	 * If gremlins are enabled the recommended setting is yes
	 */
	

	
	//these are the dimensions
	/**
	 * The size of the z dimension
	 */
	public static int zSize = size;
	/**
	 * The size of the y dimension
	 */
	public static int ySize = size;
	/**
	 * The size of the x dimension
	 */
	public static int xSize = size;
	/**
	 * The dimensions are by default all the same. I.e the maze is a cube
	 */
	/**
	 * start position for the human and the maze generator
	 */
	public static byte[] position = {0, 1, 1, 1};
	/**
	 * 4d maze
	 */
	public static byte[][][][] maze = new byte[1][zSize][ySize][xSize];
	/**
	 * the nodes for use in the algorithm to make a perfect maze A place for the
	 * program to store the places its carved passages to This way it can
	 * recursively backtrack to them when needed
	 */
	private static ArrayList<byte[]> nodes = new ArrayList(1);


	/**
	 * turns the byte[][][][] maze into a 4d maze with a nifty algorithm that
	 * carves passages by setting the value of the index =1 1=hole 0= wall
	 * (optimized to prevent me from having to manually fill in walls)
	 */
	public static void make4dMaze() {
		byte[] mazeCoords = position.clone();
		nodes.add(0, mazeCoords);

		while (nodes.size() > 0) {
			findNewNode(nodes.get(nodes.size() - 1));

		}
		makeGlade();
	}

	/**
	 * Displays the whole maze level by level impractical for view of large mazes
	 */
	public static void dispWholeMaze() {
		for (byte[][] x : maze[0]) {
			dispMaze(x);
			System.out.println("\n");
		}
	}

	/**
	 *
	 * @param node from which its looking for nodes to grow to if it fails it
	 * deletes this node from the list of nodes its growing to otherwise it adds
	 * the first random node it finds
	 */
	public static void findNewNode(byte[] node) {
		boolean done = false;
		int counter = 0;
		int choice;
		while (!done) {
			if (counter >= 12) {
				choice = counter - 12;
			} else {
				choice = (int) Math.random()*8;
			}
			try {
				if (choice == 0) {
					if (maze[node[0] + 2][node[1]][node[2]][node[3]] == 0) {
						maze[node[0] + 2][node[1]][node[2]][node[3]] = 1;
						maze[node[0] + 1][node[1]][node[2]][node[3]] = 1;
						node[0] += 2;
						done = true;
					}
				} else if (choice == 1) {
					if (maze[node[0] - 2][node[1]][node[2]][node[3]] == 0) {
						maze[node[0] - 2][node[1]][node[2]][node[3]] = 1;
						maze[node[0] - 1][node[1]][node[2]][node[3]] = 1;
						node[0] -= 2;
						done = true;
					}
				} else if (choice == 2) {
					if (maze[node[0]][node[1] + 2][node[2]][node[3]] == 0) {
						maze[node[0]][node[1] + 2][node[2]][node[3]] = 1;
						maze[node[0]][node[1] + 1][node[2]][node[3]] = 1;
						node[1] += 2;
						done = true;
					}
				} else if (choice == 3) {
					if (maze[node[0]][node[1] - 2][node[2]][node[3]] == 0) {
						maze[node[0]][node[1] - 2][node[2]][node[3]] = 1;
						maze[node[0]][node[1] - 1][node[2]][node[3]] = 1;
						node[1] -= 2;
						done = true;
					}
				} else if (choice == 4) {
					if (maze[node[0]][node[1]][node[2] + 2][node[3]] == 0) {
						maze[node[0]][node[1]][node[2] + 2][node[3]] = 1;
						maze[node[0]][node[1]][node[2] + 1][node[3]] = 1;
						node[2] += 2;
						done = true;
					}
				} else if (choice == 5) {
					if (maze[node[0]][node[1]][node[2] - 2][node[3]] == 0) {
						maze[node[0]][node[1]][node[2] - 2][node[3]] = 1;
						maze[node[0]][node[1]][node[2] - 1][node[3]] = 1;
						node[2] -= 2;
						done = true;
					}
				} else if (choice == 6) {
					if (maze[node[0]][node[1]][node[2]][node[3] + 2] == 0) {
						maze[node[0]][node[1]][node[2]][node[3] + 2] = 1;
						maze[node[0]][node[1]][node[2]][node[3] + 1] = 1;
						node[3] += 2;
						done = true;
					}
				} else if (choice == 7) {
					if (maze[node[0]][node[1]][node[2]][node[3] - 2] == 0) {
						maze[node[0]][node[1]][node[2]][node[3] - 2] = 1;
						maze[node[0]][node[1]][node[2]][node[3] - 1] = 1;
						node[3] -= 2;
						done = true;
					}
				} else if (choice == 8) {//wait a second there are no choices left
					nodes.remove(nodes.size() - 1);
					return;
				}

			} catch (Exception e) {/*gotcha*/
//cant afford to go off the map now can we

			}
			counter++;
		}
		byte[] cloned = node.clone();
		nodes.add(cloned);

	}

	/**
	 * makes a user move
	 *
	 * @param move is a character w a s d r to go up v to go down
	 */
	public static void makeMove(char move) {
		byte[] tempPosition = position.clone();
		try {
			maze[0][position[1]][position[2]][position[3]] = 1;
			if (move == 'w') {
				position[2] -= 1;
			} else if (move == 's') {
				position[2] += 1;
			} else if (move == 'a') {
				position[3] -= 1;
			} else if (move == 'd') {
				position[3] += 1;
			} else if (move == 'r') {
				position[1] -= 1;
			} else if (move == 'v') {
				position[1] += 1;
			} else {
			}
			if (maze[0][position[1]][position[2]][position[3]] != 0) {
				// the square is empty he is good to go
				maze[0][position[1]][position[2]][position[3]] = 2;
			} else {
				// if the square is occupied
				maze[0][tempPosition[1]][tempPosition[2]][tempPosition[3]] = 2;
				position = tempPosition.clone();
			}

		} catch (Exception e) {
			//he tried to move to a nonexistent or occupied square
			maze[0][tempPosition[1]][tempPosition[2]][tempPosition[3]] = 2;
			position = tempPosition.clone();
		}
	}


	/**
	 * displays one layer of the maze
	 *
	 * @param inMaze maze taken in
	 */
	public static void dispMaze(byte[][] inMaze) {
		for (byte[] a : inMaze) {
			for (byte b : a) {
System.out.print("["+(b==0?'W':' ')+"]");
			}
			System.out.println();
		}
	}
	

	/**
	 * makes the little hole in the middle of the maze Almost like the
	 * glade in maze runner By glade radius it means the farthest distance away
	 * from the center cube. Diagonal squares count as one. 
	 */
	public static void makeGlade() {
		int d = 2;//glade "radius"
		for (int z = size / 2 - d; z <= size / 2 + d; z++) {
			for (int y = size / 2 - d; y <= size / 2 + d; y++) {
				for (int x = size / 2 - d; x <= size / 2 + d; x++) {
					try {
						maze[0][z][y][x] = 1;
					} catch (Exception e) {


					}
				}
			}
		}

	}


}
