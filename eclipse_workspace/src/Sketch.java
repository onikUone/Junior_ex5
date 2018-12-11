import processing.core.PApplet;

public class Sketch extends PApplet{
	int size = 700;

	double[][] x;

	public void settings() {
		size(size, size);
	}

	public void setup() {
		String path = "src/input/5_1/dtlz1Rand_4obj.dat";
		read(path, ", ");
		System.out.println(x[0].length);
	}

	public void read(String _path, String _separator) {
		String[] line = loadStrings(_path);
		int n_solution = line.length;
		int n_objective = line[0].split(_separator).length;

		this.x = new double[n_solution][n_objective];
		for(int i = 0; i < n_solution; i++) {
			for(int j = 0; j < n_objective; j++) {
				this.x[i][j] = Double.parseDouble(line[i].split(_separator)[j]);
			}
		}
	}

	public static void main(String[] args) {
		PApplet.main("Sketch");
	}

}
