package lisp.Subroutine;

import lisp.eval.*;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（<, 条件式[単調増加]）
 *
 * @author ryu2153
 */

public class SubroutineIncreasing extends Subroutine {
    @Override
    public SExpression eval(SExpression args) throws SyntaxErrorException {
        boolean flag = true;
        int count = 0;
        Int value = null;
        Int valueOrg = null;
        SExpression cell = args;

        while (!cell.equals(EmptyList.getInstance())) {
            if (cell instanceof ConsCell) {
                try {
                    value = (Int) ((ConsCell) cell).getCar();
                } catch (ClassCastException e){
                    throw new SyntaxErrorException("Argument type Error.");
                }

                if (count == 0) {
                    valueOrg = value;
                } else if (count != 0) {
                    if (valueOrg.getValue() < value.getValue()){
                        valueOrg = value;
                    } else {
                        return Bool.valueOf(false);
                    }
                }
            }
            cell = ((ConsCell) cell).getCdr();
            count += 1;
        }
        return Bool.valueOf(flag);
    }

    public String toString() {
        return "#<subr increasing>";
    }
}
