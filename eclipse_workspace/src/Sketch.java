/*
 * プログラミング課題5
 * Junior_ex5
 * */

import processing.core.PApplet;

public class Sketch extends PApplet{
	int size = 700;

	public void settings() {
		size(size, size);
	}

	public void setup() {

//		System.out.println("-----優越関係ベース-----");
//		Domination d = new Domination(this);
//		d.domination_main();
//		System.out.println("----------------------");
		
		Scalarize s = new Scalarize(this);
		s.scalar_main();

	}

	public boolean same(double[][] a, double[][] b) {
		if(a.length != b.length || a[0].length != b[0].length) {
			return false;
		}
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[i].length; j++) {
				if(a[i][j] != b[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public double[][] read(String[] _path) {
		double[][] x;
		String[] line = loadStrings(_path[0]);
		int n_solution = line.length;
		int n_objective = line[0].split(_path[1]).length;

		x = new double[n_solution][n_objective];
		for(int i = 0; i < n_solution; i++) {
			for(int j = 0; j < n_objective; j++) {
				x[i][j] = Double.parseDouble(line[i].split(_path[1])[j]);
			}
		}

		return x;
	}

	public static void main(String[] args) {
		PApplet.main("Sketch");
	}

}
