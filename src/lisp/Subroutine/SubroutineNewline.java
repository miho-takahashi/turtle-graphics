package lisp.Subroutine;

import lisp.eval.EmptyList;
import lisp.eval.SExpression;
import lisp.eval.Undef;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（newline）
 *
 * @author ryu2153
 *
 */

public class SubroutineNewline extends Subroutine {
	@Override
	public SExpression eval(SExpression args) throws SyntaxErrorException {
		if (args instanceof EmptyList) {
			System.out.println();
			return Undef.getInstance();
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "#<subr newline>";
	}
}
