import static main.Const.*;

import java.util.ArrayList;

public class Domination {
	Sketch s;
	ArrayList<ArrayList<Double>> top;

	public void domination_main() {
		double[][] domi2 = s.read(path_domi2);
		double[][] domi4 = s.read(path_domi4);
		double[][] domi6 = s.read(path_domi6);
		double[][] ans_domi2 = s.read(path_adomi2);
		double[][] ans_domi4 = s.read(path_adomi4);
		double[][] ans_domi6 = s.read(path_adomi6);

		//domi2
		if(s.same(dominate(domi2), ans_domi2)) {
			System.out.println("domi2 : true");
		} else {
			System.out.println("domi2 : false");
		}
		//domi4
		if(s.same(dominate(domi4), ans_domi4)) {
			System.out.println("domi4 : true");
		} else {
			System.out.println("domi4 : false");
		}
		//domi6
		if(s.same(dominate(domi6), ans_domi6)) {
			System.out.println("domi6 : true");
		} else {
			System.out.println("domi6 : false");
		}
	}

	// 非劣な解集合をreturnする
	public double[][] dominate(double[][] _x) {
		int finish;
		finish = getDominate(_x);
		double[][] nonDominate;
		do {
			nonDominate = new double[top.size()][top.get(0).size()];
			for (int i = 0; i < nonDominate.length; i++) {
				for (int j = 0; j < nonDominate[0].length; j++) {
					nonDominate[i][j] = top.get(i).get(j);
				}
			}
			finish = getDominate(nonDominate);
		} while (finish == 1);

		domiSort(nonDominate);
		return nonDominate;
	}

	public int getDominate(double[][] _x) {
		this.top = new ArrayList<ArrayList<Double>>();
		int finish = 0;
		int index = 0;
		double[] tmp;

		domi: for (int i = 0; i < _x.length; i++) {
			tmp = _x[i];
			next: for (int j = 0; j < _x.length; j++) {
				for (int k = 0; k < tmp.length; k++) {
					if (tmp[k] >= _x[j][k]) {
						continue next;
					}
				}
				for(int k = 0; k < tmp.length; k++) {
					if(tmp[k] <= _x[j][k]) {
						finish = 1;
						continue domi;
					}
				}
			}
			// 走査後 continueが起こっていない ならば tmpが優越する解はない = tmpは非劣解
			top.add(new ArrayList<Double>());
			for(int l = 0; l < tmp.length; l++) {
				top.get(index).add(tmp[l]);
			}
			index++;
		}
		return finish;
	}

	public void domiSort(double[][] _x) {
		boolean flg = true;
		double[] tmp;
		while(flg) {
			flg = false;
			for(int i = 1; i < _x.length; i++) {
				if(_x[i - 1][0] > _x[i][0]) {
					tmp = _x[i];
					_x[i] = _x[i - 1];
					_x[i - 1] = tmp;
					flg = true;
				}
			}
		}
	}

	//constructor
	Domination(Sketch _s) {
		this.s = _s;
		this.top = new ArrayList<ArrayList<Double>>();
	}
}
