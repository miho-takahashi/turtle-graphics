package lisp.Subroutine;

import lisp.eval.Bool;
import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.SExpression;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（car）
 *
 * @author ryu2153
 */

public class SubroutineNot extends Subroutine {
    @Override
    public SExpression eval(SExpression args) throws lisp.exception.LispException {
        if (((ConsCell)args).getCar() instanceof Bool) {
            Bool value = (Bool)((ConsCell)args).getCar();
            return Bool.valueOf(!value.isValid());

        } else if (args instanceof EmptyList) {
            throw new SyntaxErrorException("Argument Error");
        }
        return args;
    }
}
