package lisp.SpecialForm;

import lisp.eval.ConsCell;
import lisp.eval.Environment;
import lisp.eval.SExpression;

/**
 * 特殊形式（`, quote）
 * @author ryu2153
 *
 */

public class SpecialFormQuote extends SpecialForm {
    @Override
    public SExpression apply(SExpression args, Environment env) {
        return ((ConsCell)args).getCar();
    }
}
