import static main.Const.*;

import java.util.Arrays;

public class Hypervolume {
	Sketch s;
	Domination d;

	public void hyper_main() {
		double[][] points = h_read(path_hyper);
		double[][] nonDomi_points;
		double[] contribution;
		double[][] points100 = new double[100][2];
		double[][] points_top = new double[100][2];
		int[] index;
		int[] flg;
		int[] flg_for100;

		double hypervolume;

		// Fig4のHypervolume
//		double[][] fig4 = {{1, 8}, {4, 7}, {6, 6}, {8, 4}, {9, 1}};
//		int[] flg_fig4 = {0, 0, 0, 1, 0};
//		System.out.println("fig4 : " + hypervolume(fig4, flg_fig4));

		// 200pointsの出力と描画
//		System.out.println(points.length);
//		for(int i = 0; i < points.length; i++) {
//			for(int j = 0; j < points[0].length; j++) {
//				System.out.print(points[i][j] + " ");
//			}
//			System.out.println("");
//		}
//		s.drawDataset(points);

		// 非劣解の取り出し
		nonDomi_points = d.dominate(points);
		hyperSort(nonDomi_points);

		// 全体のHypervolumeの計算
		contribution = new double[nonDomi_points.length];
		flg = new int[nonDomi_points.length];
		index = new int[nonDomi_points.length];

		Arrays.fill(flg, 0);
		hypervolume = hypervolume(nonDomi_points, flg);
		System.out.println("nonDomi_points : " + hypervolume);

		// 貢献度の計算
		for(int i = 0; i < contribution.length; i++) {
			Arrays.fill(flg, 0);
			flg[i] = 1;
			contribution[i] = hypervolume - hypervolume(nonDomi_points, flg);
			index[i] = i;
		}

		// 貢献度 上位100点を抽出
		contriSort(contribution, index);
		for(int i = 0; i < points100.length; i++) {
			points100[i] = nonDomi_points[index[i]];
		}
		flg_for100 = new int[100];
		hyperSort(points100);
		Arrays.fill(flg_for100, 0);
		System.out.println("top100 hypervolume : " + hypervolume(points100, flg_for100));
//		for(int i = 0; i < contribution.length; i++) {
//			System.out.println(i + " : " +contribution[i] + " : " + index[i]);
//		}

		// 貢献度 最下位を順に削除していく
		flg_for100 = new int[nonDomi_points.length];

		Arrays.fill(flg_for100, 0);
		for(int i = nonDomi_points.length; i > 100; i--) {
			hypervolume = hypervolume(nonDomi_points, flg_for100);

			for(int j= 0; j < nonDomi_points.length; j++) {
				if(flg_for100[j] == 1) {
					contribution[j] = -1;
					index[j] = j;
					continue;
				}
				for(int k = 0; k < flg.length; k++) {
					flg[k] = flg_for100[k];
				}
				flg[j] = 1;
				contribution[j] = hypervolume - hypervolume(nonDomi_points, flg);
				index[j] = j;
			}

			contriSort(contribution, index);
			flg_for100[index[i-1]] = 1;

			for(int j = 0; j < 100; j++) {
				points_top[j] = nonDomi_points[index[j]];
			}
		}

		hyperSort(points_top);
		flg = new int[points_top.length];
		Arrays.fill(flg, 0);
		System.out.println("top100 2 hypervolume : " + hypervolume(points_top, flg));
		System.out.println(points_top.length);


//		s.drawDataset(nonDomi_points);
//		s.drawDataset(points100);
		s.drawDataset(points_top);
		System.out.println(nonDomi_points.length);
	}

	public void contribution() {

	}

	public double hypervolume(double[][] _x, int[] _flg) {

		double[] distance = new double[_x.length];
		double hypervolume = 0;
		int index = 0;

		//第一変数についてdistanceを計算
		for(int i = 0; i < distance.length; i++) {
			if(_flg[i] == 1) {
				continue;
			}
			if(i == 0) {
				distance[0] = _x[0][0];
			}
			else {
				distance[i] = _x[i][0] - _x[index][0];
				index = i;
			}
		}

		for(int i = 0; i < distance.length; i++) {
			if(_flg[i] == 1) {
				continue;
			}
			hypervolume += distance[i] * _x[i][1];
		}

		return hypervolume;

	}

	public void setDomi(Domination _d) {
		this.d = _d;
	}

	public void contriSort(double[] _contribute, int[] _index) {
		boolean flg = true;
		double tmp_contri;
		int tmp_index;
		while(flg) {
			flg = false;
			for(int i = 1; i < _contribute.length; i++) {
				if(_contribute[i-1] < _contribute[i]) {
					tmp_contri = _contribute[i];
					_contribute[i] = _contribute[i-1];
					_contribute[i-1] = tmp_contri;

					tmp_index = _index[i];
					_index[i] = _index[i-1];
					_index[i-1] = tmp_index;
					flg = true;
				}
			}
		}
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
