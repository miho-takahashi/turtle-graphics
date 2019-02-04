package lisp.SpecialForm;

import lisp.eval.*;
import lisp.exception.EndOfFileException;
import lisp.exception.SyntaxErrorException;

/**
 * 特殊形式（define）
 * @author ryu2153
 */

public class SpecialFormDefine extends SpecialForm {
    @Override
    public SExpression apply(SExpression args, Environment env) throws lisp.exception.LispException{
        ConsCell cell1 = (ConsCell)args;

        if (cell1.getCar() instanceof Symbol) {
            // 第一引数がSymbolのとき((define Symbol S式)のとき)
            try {
                ConsCell cell2 = (ConsCell) cell1.getCdr();

                // S式の評価と束縛
                SExpression exp = Evaluator.eval(cell2.getCar(), env);
                env.addBinding(((Symbol) cell1.getCar()), exp);

            } catch (ClassCastException e) {
                // 型変換に失敗した場合（第二引数がない場合など）
                throw new SyntaxErrorException("SExpression is Empty");
            }
        } else if (cell1.getCar() instanceof ConsCell) {
            // 手続きの定義と同時に手続きへ名前をつける

            ConsCell cellFunc = (ConsCell)cell1.getCar();
            SExpression cellFormals = cellFunc.getCdr();
            SExpression cellBody = cell1.getCdr();

            env.addBinding(((Symbol) cellFunc.getCar()), Closure.getInstance(cellFormals, cellBody, env));

            // 手続き名を返す
            return cellFunc.getCar();
        } else {
            // それ以外の場合（Symbol部に数値や真理値がきた場合）
            throw new SyntaxErrorException("Unexpected symbol");
        }

        // 特殊形式を適用したら、car部（記号）を返す
        return cell1.getCar();
    }
    @Override
    public String toString() {
        return "#<SpecialForm define>";
    }
}
