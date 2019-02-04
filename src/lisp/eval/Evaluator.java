package lisp.eval;

import lisp.Subroutine.*;
import lisp.SpecialForm.*;
import lisp.exception.SyntaxErrorException;

/**
 * 評価器
 *
 * @author tetsuya
 */
public class Evaluator {
    /**
     * <p>
     * 引数の環境の下で引数のS式を評価する。
     * </p>
     *
     * <p>
     * S式xの評価値をE[x]とすると、E[x]は次のように定義される。
     * </p>
     * <ul>
     * <li>xが整数値の場合、E[x] = x</li>
     * <li>xが真理値の場合、E[x] = x</li>
     * <li>xが空リストの場合、E[x] = 空リスト</li>
     * <li>xが未定義値の場合、E[x] = 未定義値</li>
     * <li>xがクロージャの場合、E[x] = x</li>
     * <li>xがサブルーチンの場合、E[x] = x</li>
     * <li>xが特殊形式の場合、E[x] = x</li>
     * <li>xが記号の場合、E[x] = 環境の下で記号xに束縛された値</li>
     * <li>xが空リストではないリスト(x0 x1 ... xn)の場合
     * <ul>
     * <li>E[x0]が特殊形式の場合、引数x1, ..., xn を特殊形式に適用した結果</li>
     * <li>E[x0]がサブルーチン（組み込み手続き）の場合、引数E[x1], ..., E[xn]をサブルーチンに適用した結果</li>
     * <li>E[x0]がクロージャの場合、引数E[x1], ..., E[xn]をクロージャに適用した結果</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param sexp S式
     * @param env  環境
     * @return 評価値(S式)
     */
    public static SExpression eval(SExpression sexp, Environment env) throws lisp.exception.LispException {
//        System.out.println("Evaluating : " + sexp);
        // 整数値の場合
        if (sexp instanceof Int) return (Int)sexp;

        // 真理値の場合
        if (sexp instanceof Bool) return (Bool)sexp;

        // 空リストの場合
        if (sexp instanceof EmptyList) return (EmptyList)sexp;

        // 未定義値の場合
        if (sexp instanceof Undef) return (Undef)sexp;

        // クロージャの場合
        if (sexp instanceof Closure) return (Closure)sexp;

        // サブルーチンの場合
        if (sexp instanceof Subroutine) return (Subroutine)sexp;

        // 特殊形式の場合
        if (sexp instanceof SpecialForm) return (SpecialForm)sexp;

        // 記号の場合
        if (sexp instanceof Symbol) {
            Symbol symbol = (Symbol) sexp;

            // 記号の両端が""ならば、式を評価せずに返却。（文字列として扱う）
            String st = symbol.getName();
            if (st.charAt(0) == '"' && st.charAt(st.length() - 1) == '"') {
                return symbol;
            }
            return Evaluator.eval(env.searchCdr(symbol), env);
        }

        // リストの場合
        if (sexp instanceof ConsCell) {
            // リストでない場合はエラー
            if (!SubroutineListQ.listq(sexp)){
                throw new SyntaxErrorException("invalid List");
            }

            ConsCell cell = (ConsCell) sexp;

            SExpression proc = (SExpression) Evaluator.eval(cell.getCar(), env);

            if (proc instanceof Subroutine) {
                Subroutine subr = (Subroutine) proc;
                SExpression nextArg = cell.getCdr();
                return subr.eval(evalList(nextArg, env));

            } else if (proc instanceof SpecialForm) {
                SpecialForm sp = (SpecialForm) proc;
                return sp.apply(cell.getCdr(), env);

            } else if (proc instanceof Closure) {
                Closure cl = (Closure) proc;
                return cl.eval(evalList(cell.getCdr(), env));
            }
        }
        return sexp;
    }

    /**
     * リストの要素をそれぞれ評価し、評価後のリストを得る
     *
     * @param args 　評価するリストの先頭セル
     * @param env  　評価する環境
     * @return
     */
    private static SExpression evalList(SExpression args, Environment env) throws lisp.exception.LispException {
        if (args instanceof EmptyList) {
            return EmptyList.getInstance();
        } else if (args instanceof ConsCell){
            ConsCell cell = (ConsCell)args;
            SExpression car = Evaluator.eval(cell.getCar(), env);
            SExpression cdr = evalList(cell.getCdr(), env);
            return ConsCell.getInstance(car, cdr);
        } else {
            throw new SyntaxErrorException("Argument Error");
        }
    }
}
