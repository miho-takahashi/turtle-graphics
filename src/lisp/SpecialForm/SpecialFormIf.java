package lisp.SpecialForm;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.List;

/**
 * 特殊形式（if）
 *
 * @author ryu2153
 */

public class SpecialFormIf extends SpecialForm {
    @Override
    public SExpression apply(SExpression args, Environment env) {
        try {
            SExpression cell1 = args; // 式predicateの項
            SExpression cell2 = ((ConsCell) cell1).getCdr(); // 式thenの項
            SExpression cell3 = ((ConsCell) cell2).getCdr(); // 式elseの項

            SExpression expPredicate = ((ConsCell)cell1).getCar();
            SExpression expThen = ((ConsCell)cell2).getCar();
            SExpression expElse = null;

            if(cell3 instanceof EmptyList){
                expElse = Undef.getInstance();
            } else {
                expElse = ((ConsCell) cell3).getCar();
            }

            if(Evaluator.eval(expPredicate, env) == Bool.valueOf(true)){
                return Evaluator.eval(expThen, env);
            } else {
                return Evaluator.eval(expElse, env);
            }

        } catch (ClassCastException e) {

        } catch (LispException e){

        }
        return null;
        // [TODO] subr =の実装がまだなので保留
    }
}
