package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.SExpression;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（cons）
 * 
 * @author ryu2153
 *
 */

public class SubroutineCons extends Subroutine {

	@Override
	public SExpression eval(SExpression args) throws SyntaxErrorException {

        if (args instanceof ConsCell) {
            ConsCell cell = (ConsCell) args;
            if (cell.getLength() != 2) {
                throw new SyntaxErrorException("Argument Error:two args Expected");
            }
            return ConsCell.getInstance(cell.getCar(), ((ConsCell) cell.getCdr()).getCar());
        } else {
            throw new SyntaxErrorException("Argument Error:two args Expected");
        }
	}

	@Override
	public String toString() {
		return "#<subr cons>";
	}
}