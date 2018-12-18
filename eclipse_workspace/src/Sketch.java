/*
 * プログラミング課題5
 * Junior_ex5
 * */

import processing.core.PApplet;

public class Sketch extends PApplet{
	int size = 700;
	int limit = 500;
	int zero = (size - limit) / 2;

	public void settings() {
		size(size, size);
	}

	public void setup() {
		background(255);

//		System.out.println("-----優越関係ベース-----");
		Domination d = new Domination(this);
//		d.domination_main();
//		System.out.println("----------------------");

//		System.out.println("-----スカラー化関数ベース-----");
//		Scalarize s = new Scalarize(this);
//		s.scalar_main();
//		System.out.println("--------------------------");

//		System.out.println("-----Hypervolumeベース-----");
		Hypervolume h = new Hypervolume(this);
		h.setDomi(d);
		h.hyper_main();
//		System.out.println("--------------------------");

	}

	public boolean same(double[][] a, double[][] b) {
		if(a.length != b.length || a[0].length != b[0].length) {
			return false;
		}
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[i].length; j++) {
				if(a[i][j] - b[i][j] > 10e-14) {
//				if(a[i][j] != b[i][j]) {
					System.out.println("False index : " + i);
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

	public void drawAxis() {
		stroke(0);
		line(zero, zero, zero, limit + zero);
		line(zero, limit + zero, limit + zero, limit + zero);
		line(limit + zero, limit + zero, limit + zero, zero);
		line(limit + zero, zero, zero, zero);

		for (int i = 0; i < 10; i++) {

			if (i % 2 == 0) {
				line(zero + (limit / 10) * i,
						height - zero,
						zero + (limit / 10) * i,
						height - (zero + 15));

				line(zero + (limit / 10) * i,
						height - (zero + limit),
						zero + (limit / 10) * i,
						height - (zero + limit - 15));

				line(zero,
						zero + (limit / 10) * i,
						zero + 15,
						zero + (limit / 10) * i);

				line(zero + limit,
						zero + (limit / 10) * i,
						zero + limit - 15,
						zero + (limit / 10) * i);
			} else {
				line(zero + (limit / 10) * i,
						height - zero,
						zero + (limit / 10) * i,
						height - (zero + 5));

				line(zero + (limit / 10) * i,
						height - (zero + limit),
						zero + (limit / 10) * i,
						height - (zero + limit - 5));

				line(zero,
						zero + (limit / 10) * i,
						zero + 5,
						zero + (limit / 10) * i);

				line(zero + limit,
						zero + (limit / 10) * i,
						zero + limit - 5,
						zero + (limit / 10) * i);
			}
		}
	}

	public void drawDataset(double[][] _x) {
		drawAxis();
		for(int p = 0; p < _x.length; p++) {
			stroke(0);
			strokeWeight(1);
			fill(255, 51, 51);
			ellipse(map((float)_x[p][0], (float)2.6, (float)3.1, (float)0, (float)1) * limit + zero,
					height - (map((float)_x[p][1], (float)0.9, (float)1.4, (float)0, (float)1) * limit + zero),
					10, 10);
		}
	}

	public static void main(String[] args) {
		PApplet.main("Sketch");
	}

}
