package lisp.Subroutine;

import lisp.eval.*;
import lisp.exception.SyntaxErrorException;

/**
 * リストの長さを取得する
 *
 * @author ryu2153
 */
public class SubroutineLength extends Subroutine{
    public SExpression eval(SExpression args) throws SyntaxErrorException {
        SExpression cell = ((ConsCell)args).getCar();
        int count = 0;
        SubroutineListQ sub = new SubroutineListQ();

        if(sub.eval(args).equals(Bool.valueOf(true))){
            while (!cell.equals(EmptyList.getInstance())) {
                count++;
                cell = ((ConsCell) cell).getCdr();
            }
        } else {
            throw new SyntaxErrorException("Args aren't List.");
        }
        return Int.valueOf(count);
    }
}
