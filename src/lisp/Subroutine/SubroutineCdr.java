package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.SExpression;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（cdr）
 *
 * @author ryu2153
 */

public class SubroutineCdr extends Subroutine {
    @Override
    public SExpression eval(SExpression args) throws lisp.exception.LispException {
        if (args instanceof ConsCell) {
            ConsCell cell = (ConsCell) ((ConsCell) args).getCar();
//			System.out.println("car:" + cell.getCar());
//			System.out.println("cdr:" + cell.getCdr());
            return cell.getCdr();
        } else if (args instanceof EmptyList) {
            throw new SyntaxErrorException("Argument Error");
        }
        return EmptyList.getInstance();
    }
}
