/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.util.Arrays;
import java.util.Stack;

/**
 *
 * @author rohan
 */
public class Map {

	public static final boolean threeD = false;
	public static final boolean surface = false;
 public static final boolean SPHERE = false;
	
	public static final int RADIUS = 15;
	public static final int RESOLUTION = 150;
	
	
	public static Stack<MyPoint> nodes = new Stack<>();
	static boolean[][] maze;
	
	public static final int maxOctaves = 9;
	public static final int SIZE2 = 1 << maxOctaves;
	public static final double[][] heightMap2 = new double[SIZE2][SIZE2];
	public static final int octaves = 5;

	public static void makeMaze() {
		if (threeD) {
			Maze.make4dMaze();
			Maze.dispWholeMaze();
		} else {

			maze = new boolean[69][69];
			nodes.push(new MyPoint(1, 1));
			while (!nodes.empty()) {
				findNewNode();
			}
		}
	}

	/**
	 * log base two of size (floored)
	 */
	private static final int lb2Size = 6;
	
		public static final double HILLS = (3<<(lb2Size))*2.7;
	/**
	 * For certain reasons, this must be one greater than a power of two.
	 */
	public static final int SIZE = 1 + (1 << lb2Size);
	static double[][] heightMap = new double[SIZE][SIZE];


	public static void midpointDisplaceHeightMap() {
		for (int i = 0; i < lb2Size * 2; i++) {
						int incr = (SIZE - 1) >> (i>>1);
			if ((i & 1) == 1) {
	//square
					for(int y = 0;y<SIZE;y+=incr/2){
						for(int x = (1-((y/(incr/2))&1))*(incr/2);x<SIZE;x+=incr){
							 int x0 = x - (incr>>1) + SIZE;
								int y0 = y;
								int x1 = x;
								int y1 = y + (incr>>1);
								int x2 = x + (incr>>1);
								int y2 = y;
								int x3 = x;
								int y3 = y - (incr>>1)+SIZE;
											heightMap[x][y] = (Math.random() - .5) * (HILLS) / (1 << i)
														+ (heightMap[x0%SIZE][y0%SIZE] + heightMap[x1%SIZE][y1%SIZE] + heightMap[x2%SIZE][y2%SIZE] + heightMap[x3%SIZE][y3%SIZE]) / 4;
						}
					}
				
			} else {
//diamond
				for (int x = 0; x < SIZE-1; x+=incr) {
					for (int y = 0; y < SIZE-1; y+=incr) {

						int xMid = x + (incr >> 1);
						int yMid = y + (incr >> 1);

						heightMap[xMid][yMid] = (Math.random() - .5) * (HILLS) / (1 << i)
														+ (heightMap[x][y] + heightMap[x + incr][y] + heightMap[x+incr][y+incr] + heightMap[x][y+incr]) / 4;

						
					}
				}

			}


	}
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

	public static void getPerlinNoiseHeightMap(){
		for(int octave = 0;octave<octaves;octave++){
			int period = (4<<octaves)>>octave;
			double[][][] gradients = new double[SIZE2/period+1][SIZE2/period+1][2];
			for(int i = 0;i<gradients.length;i++){
				for(int j = 0;j<gradients[i].length;j++){
					double angle = Math.PI*2*Math.random();
					gradients[i][j][0] = Math.cos(angle);
					gradients[i][j][1] = Math.sin(angle);
				}
			}
			for(int i = 0;i<SIZE2;i++){
				for(int j = 0;j<SIZE2;j++){
				 heightMap2[i][j] += 1.7*(1<<(maxOctaves-1))/(1<<(octave+2)) * perlin(gradients,(double) i/(double) period, (double) j/ (double) period);
				}
			}
		}
		heightMap = heightMap2;
	}
	
	public static double dotGridGradient(double[][][] gradients,int ix, int iy, double x, double y){
		double dX = x - ix;
		double dY = y - iy;
		
		return gradients[ix][iy][0]*dX + gradients[ix][iy][1]*dY;
		
	}
	public static double perlin(double[][][] gradients, double x, double y){
 int x0 = (int) x;
	int y0 = (int) y;
	int x1 = x0+1;
	int y1 = y0 + 1;
 
	double sX = x - x0;
	double sY = y - y0;
	
	double dotProd1 = dotGridGradient(gradients,x0,y0,x,y);
	double dotProd2 = dotGridGradient(gradients,x1,y0,x,y);
	dotProd2 = lerp(dotProd1,dotProd2,sX);
	double dotProd3 = dotGridGradient(gradients,x0,y1,x,y);
	double dotProd4 = dotGridGradient(gradients,x1,y1,x,y);
	dotProd4 = lerp(dotProd3,dotProd4,sX);
	return lerp(dotProd2, dotProd4,sY);
	}
	/**
		* Weighted average ((L)inear int(ERP)olation)
		* @return 
		*/
	public static double lerp(double a, double b, double weight){
		return (1-weight) * a +  (b*weight);
	}
	
	public static boolean inBounds(int x, int y) {
		return x > 0 && y > 0 && x < maze.length && y < maze[x].length;
	}
}
