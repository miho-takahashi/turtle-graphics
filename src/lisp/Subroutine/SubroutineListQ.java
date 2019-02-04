package lisp.Subroutine;

import lisp.eval.*;

/**
 * 述語list?
 *
 * @author ryu2153
 */

public class SubroutineListQ extends Subroutine {

    @Override
    public SExpression eval(SExpression args) {
        if (args instanceof ConsCell){
            return Bool.valueOf(SubroutineListQ.listq(((ConsCell)args).getCar()));
        } else {
            return Bool.valueOf(false);
        }
    }

    public static boolean listq(SExpression exp){
        try {
            SExpression cell = exp;
            while (!cell.equals(EmptyList.getInstance())) {
                cell = ((ConsCell) cell).getCdr();
            }
            return true;
        } catch (ClassCastException e) {
            //型変換に失敗 → リストではない
            return false;
        }
    }

    @Override
    public String toString() {
        return "#<subr list?>";
    }
}
