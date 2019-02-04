package lisp.Subroutine;

import lisp.eval.SExpression;
import lisp.exception.EndOfFileException;
import lisp.exception.ExitException;

/**
 * 組み込み手続き（exit）
 * @author ryu2153
 *
 */

public class SubroutineExit extends Subroutine {
    @Override
    public SExpression eval(SExpression args) throws ExitException {
        throw new ExitException();
    }
}
