package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.SExpression;
import lisp.exception.EndOfFileException;
import lisp.exception.SyntaxErrorException;

import java.util.List;

/**
 * 組み込み手続き
 * @author tetsuya
 *
 */
public abstract class Subroutine implements SExpression {
    public abstract SExpression eval(SExpression args) throws lisp.exception.LispException;
}
