package lisp.eval;

import lisp.exception.LispException;

/**
 * クロージャ
 *
 * @author tetsuya, ryu2153
 */
public class Closure implements SExpression {
    private SExpression formals;
    private SExpression body;
    private Environment env;

    private Closure(SExpression formals, SExpression body, Environment env) {
        this.formals = formals;
        this.body = body;
        this.env = env;
//        System.out.println("---Create Closure---");
//        System.out.println("DEBUG : formals = " + formals);
//        System.out.println("DEBUG :    body = " + body);
    }

    public static Closure getInstance(SExpression formals, SExpression body, Environment env) {
        return new Closure(formals, body, env);
    }

    @Override
    public String toString() {
        return "#<Closure " + body + " >";
    }

    /**
     * クロージャの仮引数と実引数の紐つけ
     *
     * @param sexp 実引数の先頭セル
     * @return body
     */
    public SExpression eval(SExpression sexp) throws LispException {

        SExpression exp1 = formals; // 仮引数リスト
        SExpression exp2 = sexp;    // 実引数リスト
        Environment localEnv = new Environment(this.env);
//        System.out.println("DEBUG : formals = " + formals);
//        System.out.println("DEBUG :    body = " + body);
//        System.out.println("DEBUG :    args = " + exp2);

        int formalsLength = getLength(exp1);
        int sexpLength = getLength(exp2);

//        System.out.println("formals length:" + formalsLength);
//        System.out.println("   sexp length:" + sexpLength);

        // 仮引数が1個の場合
        if (formalsLength == 1) {
            Symbol symbol;
            SExpression arg;

            if (exp1 instanceof ConsCell) {
                symbol = ((Symbol) ((ConsCell) exp1).getCar());
            } else {
                symbol = ((Symbol) exp1);
            }

            // 仮引数リストが1つの要素のみのリストだったら
            try {
                if (((ConsCell) exp2).getCdr() instanceof EmptyList) {
                    arg = ((ConsCell) exp2).getCar();
                } else {
                    arg = exp2;
                }
            } catch (ClassCastException e) {
                arg = exp2;
            }
            localEnv.addBinding(symbol, arg);

            return evalBody(body, localEnv);
        }

        // 仮引数と実引数の束縛
        while (!exp1.equals(EmptyList.getInstance())) {
            if (exp1 instanceof ConsCell) {
                Symbol symbol = ((Symbol) ((ConsCell) exp1).getCar());
                SExpression arg = ((ConsCell) exp2).getCar();
                localEnv.addBinding(symbol, arg);
//                System.out.println(" DEBUG1 : " + st + " - " + arg.toString() + " is binded.");

                exp1 = ((ConsCell) exp1).getCdr();
                exp2 = ((ConsCell) exp2).getCdr();

            } else {
                // (lambda (x1 x2 . z) x2)等の場合
                Symbol symbol = ((Symbol) exp1);
                localEnv.addBinding(symbol, exp2);
//                System.out.println(" DEBUG2 : " + st + " - " + arg.toString() + " is binded.");
                break;
            }
        }
        return evalBody(body, localEnv);
    }

    /**
     * 引数の個数を取得する内部メソッド
     *
     * @param exp
     * @return
     */
    private int getLength(SExpression exp) {
        if (exp instanceof ConsCell) {
            return ((ConsCell) exp).getLength();
        } else if (exp instanceof EmptyList) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * クロージャのbodyを評価し、最後のbodyの評価値を返す
     * @param body
     * @param localEnv
     * @return
     * @throws LispException
     */
    private SExpression evalBody(SExpression body, Environment localEnv) throws LispException {
        if (body instanceof ConsCell) {
            SExpression cellBody = body;
            SExpression bodyCar = ((ConsCell) cellBody).getCar();

            while (!EmptyList.getInstance().equals(((ConsCell) cellBody).getCdr())) {
                Evaluator.eval(bodyCar, localEnv);
                bodyCar = ((ConsCell) cellBody).getCar();
                cellBody = ((ConsCell) cellBody).getCdr();
            }
//        System.out.println("eval : "+Evaluator.eval(bodyCar, localEnv));
            return Evaluator.eval(bodyCar, localEnv);
        } else {
            return Evaluator.eval(body, localEnv);
        }
    }
}