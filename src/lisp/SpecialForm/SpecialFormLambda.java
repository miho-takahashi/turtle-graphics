package lisp.SpecialForm;

import lisp.eval.*;

import java.util.List;

/**
 * 特殊形式（lambda）
 * @author ryu2153
 *
 */

public class SpecialFormLambda extends SpecialForm{
    @Override
    public SExpression apply(SExpression sexp, Environment env) {
        ConsCell cell1 = (ConsCell)sexp;
        ConsCell cell2 = (ConsCell)cell1.getCdr();

        return Closure.getInstance(cell1.getCar(), cell2, env);
    }

    @Override
    public String toString() {
        return "#<SpecialForm lambda>";
    }
}
