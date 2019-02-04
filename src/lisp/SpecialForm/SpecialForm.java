package lisp.SpecialForm;

import lisp.eval.Environment;
import lisp.eval.SExpression;
import lisp.exception.SyntaxErrorException;

/**
 * 特殊形式
 * @author tetsuya
 *
 */
public abstract class SpecialForm implements SExpression{
    public abstract SExpression apply(SExpression args, Environment env) throws lisp.exception.LispException;
}
