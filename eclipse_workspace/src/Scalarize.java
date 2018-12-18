import static main.Const.*;

import java.util.Arrays;

public class Scalarize {
	Sketch s;

	public void scalar_main() {
		double[][] scala2 = s.read(path_func2);
		double[][] scala3 = s.read(path_func3);
		double[][] scala4 = s.read(path_func4);
		double[][] scala5 = s.read(path_func5);
		double[][] ans1_scala2 = s.read(path_ans1func2);
		double[][] ans1_scala3 = s.read(path_ans1func3);
		double[][] ans1_scala4 = s.read(path_ans1func4);
		double[][] ans1_scala5 = s.read(path_ans1func5);
		double[][] ans2_scala2 = s.read(path_ans2func2);
		double[][] ans2_scala3 = s.read(path_ans2func3);
		double[][] ans2_scala4 = s.read(path_ans2func4);
		double[][] ans2_scala5 = s.read(path_ans2func5);

		double[][] ans_vec = s.read(path_vec);

		int M;
		int H;
		double[][] weightVec;
		double[][] answer;
		int index;

		M = 2;
		H = 99;
		weightVec = getWeight(M, H);



		answer = new double[weightVec.length][M];
		for(int i = 0; i < weightVec.length; i++) {
			index = searchMax(scala2, weightVec[i]);
			for(int j = 0; j < M; j++) {
				answer[i][j] = scala2[index][j];
			}
		}

		for(int i = 0; i < weightVec.length; i++) {
			System.out.print(i + " : ");
			for(int j = 0; j < weightVec[0].length; j++) {
				System.out.print("weight : " + weightVec[i][j] + " ");
//				System.out.print("answer : " + answer[i][j] + " ");
			}
			System.out.println("");
		}

		System.out.println("----------------------------------------");
		System.out.println(weightVec[24][0] * answer[24][0] + weightVec[24][1] * answer[24][1]);
		System.out.println(weightVec[24][0] * 0.304709 + weightVec[24][1] * 0.952446);
//		System.out.println(0.242424 * 0.304709 +  0.757576* 0.952446);
		if((weightVec[24][0] * answer[24][0] + weightVec[24][1] * answer[24][1]) == (weightVec[24][0] * 0.304709 + weightVec[24][1] * 0.952446)) {
			System.out.println("equals.");
		}
//		System.out.println(0.242424 * answer[24][0] + 0.757576 * answer[24][1]);
//		System.out.println(0.242424 * 0.304709 + 0.757576 * 0.952446);
//		System.out.println(weightVec[24][1]);
		System.out.println("----------------------------------------");



//		for(int i = 0; i < weightVec[24].length; i++) {
//			System.out.print("weightVec : " + weightVec[24][i] + " ");
//		}
//		System.out.println("");
		if(same(answer, ans1_scala2, weightVec, answer)) {
			System.out.println("scala2_ans1 : true");
		} else {
			System.out.println("scala2_ans1 : false");
		}
//		System.out.println(combination(H + M - 1, M - 1));
	}


	public int searchMax(double[][] _x, double[] _weightVec) {
		double tmp = 0;
		double max = -1;
		int maxIndex = -1;

		for(int i = 0; i < _x.length; i++) {
//		for(int i = _x.length - 1; i >= 0; i--) {
			for(int j = 0; j < _weightVec.length; j++) {
				if(j == 0) {
					tmp = _x[i][j] * _weightVec[j];
				}
				else {
					tmp += _x[i][j] * _weightVec[j];
				}
			}

			if(i == 0) {
//			if(i == _x.length - 1) {
				max = tmp;
				maxIndex = i;
			}
			else {
				if(tmp >= max) {
					max = tmp;
					maxIndex = i;
				}
			}

		}

		return maxIndex;
	}

	public boolean same(double[][] a, double[][] b, double[][] weightVec, double[][] answer) {
		if(a.length != b.length || a[0].length != b[0].length) {
			return false;
		}
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[i].length; j++) {
				if(a[i][j] - b[i][j] > 0.0000000000000001) {
//				if(a[i][j] != b[i][j]) {
					System.out.print("False index : " + i + " : ");
					if((weightVec[i][0] * answer[i][0] + weightVec[i][1] * answer[i][1]) == (weightVec[i][0] * b[i][0] + weightVec[i][1] * b[i][1])) {
						System.out.println("equals.");
					}
					else {
						System.out.println("not equals.");
					}
					System.out.println(weightVec[i][0] * answer[i][0] + weightVec[i][1] * answer[i][1]);
					System.out.println(weightVec[i][0] * b[i][0] + weightVec[i][1] * b[i][1]);
//					return false;
				}
			}
		}
		return true;
	}

	public double[][] getWeight(int _m, int _h) {
		double[][] weightVec = new double[combination(_h + _m - 1, _m - 1)][_m];
		double[] w_source = new double[_h + _m - 1];
		Arrays.fill(w_source, 1 / (double) _h);
		int[] partition = new int[_m - 1];
		int[] flg = new int[_m - 1];
		int index;

		//仕切りの左寄せ
		for (int i = 0; i < _m - 1; i++) {
			partition[i] = i;
		}

		for (int a = 0; a < weightVec.length; a++) {
			//仕切りの挿入
			Arrays.fill(w_source, 1 / (double) _h);
			for (int i = 0; i < partition.length; i++) {
				w_source[partition[i]] = -1;
			}
			//重みベクトルの獲得
			index = 0;
			for (int i = 0; i < w_source.length; i++) {
				if (w_source[i] == -1) {
					index++;
					continue;
				}
				weightVec[a][index] += w_source[i];
			}
//			//重みベクトルの小数点以下第7位の四捨五入
//			for(int i = 0; i < weightVec[a].length; i++) {
//				double tmp;
//				tmp = Math.round(weightVec[a][i] * 1000000);
//				weightVec[a][i] = tmp / 1000000;
//			}
			//仕切り位置の更新
			for (int i = 0; i < partition.length; i++) {
				if (partition[(_m - 1) - 1 - i] < (_h + _m - 1) - 1 - i) {
					partition[(_m - 1) - 1 - i]++;
					flg[(_m - 1) - 1 - i] = 0;
					if (i != 0 && flg[(_m - 1) - 1 - i + 1] == 1) {
						//初期化する
						for (int j = (_m - 1) - 1 - i + 1; j < partition.length; j++) {
							partition[j] = partition[j - 1] + 1;
							Arrays.fill(flg, 0);
						}
					}
					break;
				} else {
					flg[(_m - 1) - 1 - i] = 1;
					continue;
				}
			}
		}

//		for(int i = 0; i < weightVec.length; i++) {
//			for(int j = 0; j < weightVec[0].length; j++) {
//				System.out.print(weightVec[i][j] + " ");
//			}
//			System.out.println("");
//		}
		return weightVec;
	}

	public int combination(int _n, int _r) {
		int num = 1;
		for (int i = 1; i <= _r; i++) {
			num = num * (_n - i + 1) / i;
		}
		return num;
	}

	//constructor
	Scalarize(Sketch _s) {
		this.s = _s;
	}

}
