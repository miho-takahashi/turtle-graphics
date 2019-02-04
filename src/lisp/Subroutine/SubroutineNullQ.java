package lisp.Subroutine;

import lisp.eval.Bool;
import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.SExpression;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（null?）
 * 
 * @author ryu2153
 *
 */

public class SubroutineNullQ extends Subroutine {
	@Override
	public SExpression eval(SExpression args) throws SyntaxErrorException {

	    try {
            return Bool.valueOf(((ConsCell)args).getCar() instanceof EmptyList);
        } catch (ClassCastException e){
	        throw new SyntaxErrorException("Argument Error");
        }
	}

	public String toString() {
		return "#<subr null?>";
	}

}
