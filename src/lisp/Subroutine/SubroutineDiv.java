package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.Double;
import lisp.eval.EmptyList;
import lisp.eval.Int;
import lisp.eval.SExpression;
import lisp.exception.SyntaxErrorException;
import lisp.exception.UndefinedErrorException;

/**
 * 組み込み手続き（/, 除算）
 * @author ryu2153
 *
 */

public class SubroutineDiv extends Subroutine {
	@Override
	public SExpression eval(SExpression sexp) throws lisp.exception.LispException {
		double quo = 0;
		int check;
		boolean first = true;
		boolean seisuu = true;
		if (sexp instanceof ConsCell) {
			SExpression cell = sexp;

			while (!cell.equals(EmptyList.getInstance())) {
				SExpression exp = ((ConsCell) cell).getCar();

				if (exp instanceof Int) {
					if (first)
						quo = ((Int) exp).getValue();
					else
						quo /= ((Int) exp).getValue();
				} else if (exp instanceof lisp.eval.Double) {
					if (first)
						quo = ((lisp.eval.Double) exp).getValue();
					else
						quo /= ((lisp.eval.Double) exp).getValue();
					seisuu = false;
				} else {
					throw new UndefinedErrorException("unbound variable");
				}
				cell = ((ConsCell) cell).getCdr();
				first = false;
			}
		} else if (sexp instanceof EmptyList) {
			throw new SyntaxErrorException("wrong number of arguments");
		}
		check = (int) quo;
		if (seisuu) {
			return Int.valueOf(check);
		} else {
			return Double.valueOf(quo);
		}
	}

	@Override
	public String toString() {
		return "#<subr />";
	}
}