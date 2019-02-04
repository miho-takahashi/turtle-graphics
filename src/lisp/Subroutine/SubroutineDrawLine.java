package lisp.Subroutine;

import java.util.ArrayList;
import java.util.List;

import lisp.eval.ConsCell;
import lisp.eval.Int;
import lisp.eval.SExpression;
import lisp.eval.Symbol;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（draw-line）
 * 
 * @author ryu2153 (draw-line sx sy ex ey colorNumber)
 */

public class SubroutineDrawLine extends Subroutine {
	@Override
	public SExpression eval(SExpression args) throws SyntaxErrorException {
		// 引数を分解する
		List<Double> list = new ArrayList<>();
		resolve(args, list);

		// 5個じゃない場合は終了
		if (list.size() != 5) {
			throw new SyntaxErrorException("The number of arguments is not enough :" + args);
		}

		// 0 : 黒, 1 : 赤, 2 : 緑, 3 : 青, それ以外の色 : 黒(とりあえず)
		int colorNumber = list.get(4).intValue();
		if (list.get(4) - colorNumber != 0) {
			throw new SyntaxErrorException("invalid parameter : " + list.get(4));
		}

		lisp.Main.window.drawLine((int) Math.round(list.get(0)), (int) Math.round(list.get(1)),
				(int) Math.round(list.get(2)), (int) Math.round(list.get(3)), colorNumber);
		return Symbol.getInstance("draw-line");
	}

	/**
	 * S式の引数を分解してリストに入れる
	 * 
	 * @param exp
	 * @param list
	 */
	private void resolve(SExpression exp, List<Double> list) throws SyntaxErrorException {
		// ConsCellじゃなかったら終了
		if (!(exp instanceof ConsCell)) {
			return;
		}

		ConsCell cons = (ConsCell) exp;
		SExpression car = cons.getCar();
		// Int,Doubleじゃない場合は終了
		if (car instanceof Int) {
			list.add((double) ((Int) car).getValue());
		} else if (car instanceof lisp.eval.Double) {
			list.add(((lisp.eval.Double) car).getValue());
		} else {
			throw new SyntaxErrorException("invalid parameter : " + car);
		}
		// 再帰び出しで分解していく
		resolve(cons.getCdr(), list);
	}
}
