package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.Int;
import lisp.eval.SExpression;
import lisp.exception.SyntaxErrorException;
import lisp.exception.UndefinedErrorException;

/**
 * 組み込み手続き（modulo）
 * @author ryu2153
 *
 */

public class SubroutineModulo extends Subroutine {
	@Override
	public SExpression eval(SExpression sexp) throws lisp.exception.LispException {
		int mod = 0;
		if (sexp instanceof ConsCell) {
			SExpression cell = sexp;
			SExpression exp = ((ConsCell) cell).getCar();

			if (exp instanceof Int) {
				mod = ((Int) exp).getValue();
				cell = ((ConsCell) cell).getCdr();
				if (cell instanceof EmptyList) {
					throw new SyntaxErrorException("wrong number of arguments");
				}
				exp = ((ConsCell) cell).getCar();
				if (exp instanceof Int) {
					mod %= ((Int) exp).getValue();
				} else {
					throw new UndefinedErrorException("unbound variable");
				}
			} else {
				throw new UndefinedErrorException("unbound variable");
			}
			cell = ((ConsCell) cell).getCdr();
			if (!(cell instanceof EmptyList)) {
				throw new SyntaxErrorException("wrong number of arguments");
			}
		} else if (sexp instanceof EmptyList) {
			throw new SyntaxErrorException("wrong number of arguments");
		}
		return Int.valueOf(mod);
	}

	@Override
	public String toString() {
		return "#<subr modulo>";
	}
}
