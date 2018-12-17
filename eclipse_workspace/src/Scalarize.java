import static main.Const.*;

import java.util.Arrays;

public class Scalarize {
	Sketch s;
	int h;

	public void scalar_main() {
		double[][] scala2 = s.read(path_func2);
		double[][] scala3 = s.read(path_func3);
		double[][] scala4 = s.read(path_func4);
		double[][] scala5 = s.read(path_func5);

		getWeight(4, 5);
		System.out.println(combination(5 + 4 - 1, 4 - 1));

	}

	public double[][] getWeight(int _m, int _h) {
		double[][] weightVec = new double[combination(_h + _m - 1, _m - 1)][_m];
		double[] w_source = new double[_h + _m - 1];
		Arrays.fill(w_source, (double) 1 / (double) _h);
		int[] partition = new int[_m - 1];
		int[] flg = new int[_m - 1];
		int index;

		//仕切りの左寄せ
		for (int i = 0; i < _m - 1; i++) {
			partition[i] = i;
		}

		for (int a = 0; a < weightVec.length; a++) {
			//仕切りの挿入
			Arrays.fill(w_source, (double)1/(double)_h);
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
