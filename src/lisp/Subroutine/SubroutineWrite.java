package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.SExpression;
import lisp.eval.Symbol;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（write）
 *
 * @author ryu2153
 */

public class SubroutineWrite extends Subroutine {
	@Override
	public SExpression eval(SExpression args) throws SyntaxErrorException {
        if (args instanceof ConsCell){
            ConsCell cell = (ConsCell)args;
            if (cell.getCar() instanceof Symbol){
                Symbol symbol = (Symbol) cell.getCar();

                if (symbol.isString()){
                    String st = symbol.getName();
                    System.out.println(st.substring(1, st.length() - 1));
                } else {
                    System.out.println(args);
                }
            } else {
                System.out.println(args);
            }
        } else if (args instanceof EmptyList){
            System.out.println(args);
        } else {
            throw new SyntaxErrorException("Argument Error: List Expected");
        }
        return args;
    }

	@Override
	public String toString() {
		return "#<subr write>";
	}
}
