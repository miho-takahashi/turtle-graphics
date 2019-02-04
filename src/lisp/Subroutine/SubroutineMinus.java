package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.Double;
import lisp.eval.EmptyList;
import lisp.eval.Int;
import lisp.eval.SExpression;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（-, 減算）
 * @author ryu2153
 *
 */

public class SubroutineMinus extends Subroutine {

	@Override
	public SExpression eval(SExpression sexp) throws SyntaxErrorException {
		double sum = 0;
		if (sexp instanceof ConsCell) {
			SExpression cell = sexp;
			int i = 0;
			while (!cell.equals(EmptyList.getInstance())) {

				SExpression exp = ((ConsCell) cell).getCar();

				i++;
				if (i == 1) {
					if (exp instanceof Int) {
						sum = ((Int) exp).getValue();
					} else if (exp instanceof lisp.eval.Double) {
						sum = ((lisp.eval.Double) exp).getValue();
					}
				} else {
					if (exp instanceof Int) {
						sum -= ((Int) exp).getValue();
					} else if (exp instanceof lisp.eval.Double) {
						sum -= ((lisp.eval.Double) exp).getValue();
					}
				}
				cell = ((ConsCell) cell).getCdr();
			}
			if (i == 1) {
				sum = -sum;
			}
		} else if (sexp instanceof EmptyList) {
			sum = 0;
		} else {
			System.out.println("Argument Error");
		}
		return Double.valueOf(sum);

	}

	@Override
	public String toString() {
		return "#<subr minus>";
	}
}
