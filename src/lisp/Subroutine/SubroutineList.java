package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.SExpression;

/**
 * 組み込み手続き（list）
 * 
 * @author ryu2153, Toyoshima
 *
 */

public class SubroutineList extends Subroutine {
	@Override
	public SExpression eval(SExpression sexp) {
		SExpression car = ((ConsCell) sexp).getCar();
		SExpression cdr = ((ConsCell) sexp).getCdr();
		ConsCell list = ConsCell.getInstance(car, cdr);
		return list;
	}

	public String toString() {
		return "#<subr app>";
	}

}
