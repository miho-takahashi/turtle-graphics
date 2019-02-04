package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.Int;
import lisp.eval.SExpression;
import lisp.exception.SyntaxErrorException;
import lisp.exception.UndefinedErrorException;

/**
 * 組み込み手続き（cos）
 * 
 * @author ryu2153
 *
 */

public class SubroutineCos extends Subroutine {
	@Override
	public SExpression eval(SExpression sexp) throws SyntaxErrorException, UndefinedErrorException {
		double rad = 0;
		if (sexp instanceof ConsCell) {
			SExpression cell = ((ConsCell) sexp).getCar();
			if (cell instanceof Int) {
				rad = ((Int) cell).getValue();
			} else if (cell instanceof lisp.eval.Double) {
				rad = ((lisp.eval.Double) cell).getValue();
			} else {
				// 例外 束縛されていない変数
				throw new lisp.exception.UndefinedErrorException("unbound variable");
			}

			if (!(((ConsCell) sexp).getCdr() instanceof EmptyList)) {
				// 例外 引数の数が多い
				throw new lisp.exception.SyntaxErrorException("too many arguments given to cos");
			}
		}

		else if (sexp instanceof EmptyList) {
			// 例外 引数の数が少ない
			throw new lisp.exception.SyntaxErrorException("too few arguments given to cos");
		}
		return lisp.eval.Double.valueOf(Math.cos(rad));
	}

	@Override
	public String toString() {
		return "#<subr cos>";
	}
}
