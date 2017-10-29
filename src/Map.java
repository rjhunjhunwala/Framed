/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.util.Stack;

/**
 *
 * @author rohan
 */
public class Map {

	public static final boolean threeD = true;
	public static Stack<MyPoint> nodes = new Stack<>();
	static boolean[][] maze;

	public static void makeMaze() {
		if (threeD) {
			Maze.make4dMaze();
			Maze.dispWholeMaze();
		} else {

			maze = new boolean[65][65];
			nodes.push(new MyPoint(1, 1));
			while (!nodes.empty()) {
				findNewNode();
			}
		}
	}
	static int[][] heightMap = new int[10][10];

	public static void genMap() {
		for (int i = 0; i < heightMap.length; i++) {
			for (int j = 0; j < heightMap[i].length; j++) {
				heightMap[i][j] = 10;
			}
		}
		makePerlinNoise(0,9,0,9,64);
	}

	public static void makePerlinNoise(int lowX, int maxX, int lowY, int maxY, int distortion) {

		int midX = (lowX + maxX) / 2;
		int midY = (lowY + maxY) / 2;
if(midX==lowX&&midY==lowY){
	return;
}
		heightMap[midX][midY] = (int) ((Math.random() * distortion + (heightMap[lowX][lowY] + heightMap[lowX][maxY] + heightMap[maxX][lowY] + heightMap[maxX][maxY]) / 4));
		distortion /= 2;
		makePerlinNoise(lowX, midX, lowY, midY, distortion);
	makePerlinNoise(midX, maxX, lowY, midY, distortion);
		makePerlinNoise(lowX, midX, midY, maxY, distortion);
		makePerlinNoise(midX, maxX, midY, maxY, distortion);
	}

	public static void findNewNode() {
		MyPoint c = nodes.peek();
		int x = (int) c.getX();
		int y = (int) c.getY();

		int startCase = (int) (Math.random() * 4);

		for (int i = 0; i < 4; i++) {
			int attemptX = x, attemptY = y;
			switch (startCase) {
				case 0:
					attemptX += 2;
					break;
				case 1:
					attemptX -= 2;
					break;
				case 2:
					attemptY += 2;
					break;
				case 3:
					attemptY -= 2;
					break;
			}
			if (inBounds(attemptX, attemptY)) {
				if (!maze[attemptX][attemptY]) {
					maze[attemptX][attemptY] = true;
					maze[x + ((attemptX - x) / 2)][y + ((attemptY - y) / 2)] = true;
					nodes.push(new MyPoint(attemptX, attemptY));
					return;
				}
			}
			startCase += 1;
			startCase %= 4;
		}
		nodes.pop();
	}

	public static boolean inBounds(int x, int y) {
		return x > 0 && y > 0 && x < maze.length && y < maze[x].length;
	}
}
