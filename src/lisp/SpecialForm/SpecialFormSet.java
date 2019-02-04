package lisp.SpecialForm;

import lisp.eval.*;
import lisp.exception.EndOfFileException;
import lisp.exception.SyntaxErrorException;

import java.util.List;

/**
 * 特殊形式（set!）
 * @author ryu2153
 *
 */

public class SpecialFormSet extends SpecialForm {
    @Override
    public SExpression apply(SExpression args, Environment env) throws lisp.exception.LispException {
        ConsCell cell1 = (ConsCell)args;

        if (cell1.getCar() instanceof Symbol) {
            Symbol symbol = (Symbol) cell1.getCar();
            // 第一引数がSymbolのとき((set! Symbol S式)のとき)
            try {
                ConsCell cell2 = (ConsCell) cell1.getCdr();

                // S式の評価と束縛
                SExpression exp = Evaluator.eval(cell2.getCar(), env);
//                System.out.println("set! : " + symbol + " - " + exp);
                env.updateBinding((Symbol)cell1.getCar(), exp);

            } catch (ClassCastException e) {
                // 型変換に失敗した場合（第二引数がない場合など）
                throw new SyntaxErrorException("SExpression is Empty");
            } catch (EndOfFileException e) {
                e.printStackTrace();
            }
        }
        // 特殊形式を適用したら、car部（記号）を返す
        return cell1.getCar();
    }

    @Override
    public String toString() {
        return "#<SpecialForm set!>";
    }
}
