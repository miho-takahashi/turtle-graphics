package lisp.Subroutine;

import lisp.eval.*;

/**
 * 組み込み手続き（eq?）
 *
 * @author ryu2153
 */

public class SubroutineEq extends Subroutine {

    @Override
    public SExpression eval(SExpression args) {

        ConsCell cell1 = null;
        ConsCell cell2 = null;
        try {
            cell1 = (ConsCell)args;
            cell2 = (ConsCell)cell1.getCdr();
        } catch (ClassCastException e){
            return null;
        }

        SExpression obj1 = cell1.getCar();
        SExpression obj2 = cell2.getCar();

        return Bool.valueOf(obj1.equals(obj2));
    }

    public String toString() {
        return "#<subr eq?>";
    }
}
