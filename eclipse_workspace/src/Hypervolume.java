import static main.Const.*;

public class Hypervolume {
	Sketch s;
	Domination d;

	public void hyper_main() {
		double[][] points = h_read(path_hyper);
		double[][] nonDomi_points;

//		System.out.println(points.length);
//		for(int i = 0; i < points.length; i++) {
//			for(int j = 0; j < points[0].length; j++) {
//				System.out.print(points[i][j] + " ");
//			}
//			System.out.println("");
//		}
//		s.drawDataset(points);

		nonDomi_points = d.dominate(points);
		hyperSort(nonDomi_points);


//		for(int i = 0; i < nonDomi_points.length; i++) {
//			for(int j = 0; j < nonDomi_points[0].length; j++) {
//				System.out.print(nonDomi_points[i][j] + " ");
//			}
//			System.out.println("");
//		}

		s.drawDataset(nonDomi_points);
		System.out.println(nonDomi_points.length);
	}

	public void setDomi(Domination _d) {
		this.d = _d;
	}

	public void hyperSort(double[][] _x) {
		boolean flg = true;
		double[] tmp;
		while(flg) {
			flg = false;
			for(int i = 1; i < _x.length; i++) {
				if(_x[i-1][0] > _x[i][0]) {
					tmp = _x[i];
					_x[i] = _x[i-1];
					_x[i-1] = tmp;
					flg = true;
				}
			}
		}
	}

	public double[][] h_read(String[] _path){
		double[][] x;
		String[] line = s.loadStrings(_path[0]);
		int n_solution = line.length - 1;
		int n_objective = line[1].split(_path[1]).length;

		x = new double[n_solution][n_objective];
		for(int i = 1; i < line.length; i++) {
			for(int j = 0; j < n_objective; j++) {
				x[i-1][j] = Double.parseDouble(line[i].split(_path[1])[j]);
			}
		}

		return x;
	}
	//constructor
	Hypervolume(Sketch _s){
		this.s = _s;
	}
}
