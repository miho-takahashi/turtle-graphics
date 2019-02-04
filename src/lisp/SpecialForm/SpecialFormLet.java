package lisp.SpecialForm;

import lisp.eval.*;

/**
 * 特殊形式（let）
 * @author ryu2153
 */

public class SpecialFormLet extends SpecialForm {
    @Override
    public SExpression apply(SExpression args, Environment env) throws lisp.exception.LispException {
        SExpression headerCell = ((ConsCell) args).getCar();
        SExpression body = ((ConsCell) ((ConsCell) args).getCdr()).getCar(); // 手続き本体

        int listLength = ((ConsCell) headerCell).getLength();
        SExpression[] formalsArray = new SExpression[listLength]; // 仮引数リストの配列 [x1, x2, ..., xn]
        SExpression[] argsArray = new SExpression[listLength]; // 実引数リストの配列 [v1, v2, ..., vn]

        Environment localEnv = new Environment(env);

        // 仮引数リストと実引数リストを新しい環境に束縛する
        for (int i = 0; i < listLength; i++) {
            // アルゴリズムを変更したおかげで、配列を用意する必要がなくなったが、リファクタリングする時間がないのと、
            // 配列処理の方がやっていることが読み取りやすいので、ひとまずこのままとする。
            ConsCell cell = (ConsCell) ((ConsCell) headerCell).getCar();
            formalsArray[i] = cell.getCar();
            cell = (ConsCell) cell.getCdr();
            argsArray[i] = cell.getCar();

            headerCell = ((ConsCell) headerCell).getCdr();

            localEnv.addBinding((Symbol) formalsArray[i], argsArray[i]);
        }

        // 新たに束縛を行った環境でbody部の評価を行う
        return Evaluator.eval(body, localEnv);
//        return Closure.getInstance(EmptyList.getInstance(), body, localEnv);
    }

    @Override
    public String toString() {
        return "#<SpecialForm let>";
    }
}