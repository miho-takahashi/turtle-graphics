package lisp.reader;

import java.io.BufferedReader;
import java.io.IOException;

import lisp.SpecialForm.SpecialFormQuote;
import lisp.eval.Bool;
import lisp.eval.ConsCell;
import lisp.eval.Double;
import lisp.eval.EmptyList;
import lisp.eval.Int;
import lisp.eval.SExpression;
import lisp.eval.Symbol;
import lisp.exception.LispException;
import lisp.exception.SyntaxErrorException;

/**
 * 構文解析器
 *
 * @author tetsuya
 */
public class Reader {
    /**
     * 字句解析器
     */
    private Lexer lexer;

    /**
     * 先読みの字句
     */
    private Token token = null;

    /**
     * 括弧の入れ子レベル。式を読み終わった時にnestingLevelが0ならば、そこまでの式を評価する。
     */
    private int nestingLevel = 0;

    /**
     * コンストラクタ
     *
     * @param in 入力
     */
    public Reader(BufferedReader in) {
        this.lexer = new Lexer(in);
    }

    /**
     * <pre>
     * {@literal <S式>} ::= {@literal <整数値>} | {@literal <記号>} | {@literal <真理値>} | '(' ({@literal <S式>} '.' {@literal <S式>})? ')'
     * </pre>
     *
     * @return S式
     * @throws LispException
     * @throws IOException
     */
    SExpression sExpression() throws IOException, LispException {
        // 整数値
        if (this.token.getKind() == Token.Kind.NUMBER) {
            int value = this.token.getIntValue();
            if (this.nestingLevel != 0) { // 式が未完成
                this.token = this.lexer.getNextToken();
            }
            return Int.valueOf(value);
        }
        // 小数値
        if (this.token.getKind() == Token.Kind.DOUBLE) {
            double value = this.token.getDoubleValue();
            if (this.nestingLevel != 0) { // 式が未完成
                this.token = this.lexer.getNextToken();
            }
            return Double.valueOf(value);
        }
        // 記号
        if (this.token.getKind() == Token.Kind.SYMBOL) {
            String value = this.token.getSymbolValue();
            if (this.nestingLevel != 0) { // 式が未完成
                this.token = this.lexer.getNextToken();
            }
            return Symbol.getInstance(value);
        }
        // 真理値
        if (this.token.getKind() == Token.Kind.BOOLEAN) {
            boolean value = this.token.getBooleanValue();
            if (this.nestingLevel != 0) { // 式が未完成
                this.token = this.lexer.getNextToken();
            }
            return Bool.valueOf(value);
        }
        // 引用符（'）
        if (this.token.getKind() == Token.Kind.QUOTATION) {
            this.token = this.lexer.getNextToken();

            // 「'exp」を「 (quote . (exp . ()))」 すなわち「(quote exp)」と読み替える
            return ConsCell.getInstance(Symbol.getInstance("quote"),ConsCell.getInstance(sExpression(),EmptyList.getInstance()));
        }

        // '(' ')' or '(' <S式> . <S式> ')'
        if (this.token.getKind() == Token.Kind.LEFTPAR) {
            this.nestingLevel++;
            this.token = this.lexer.getNextToken();
            // 空リスト
            if (this.token.getKind() == Token.Kind.RIGHTPAR) {
                this.nestingLevel--;
                if (this.nestingLevel != 0) { // 式が未完成
                    this.token = this.lexer.getNextToken();
                }
                return EmptyList.getInstance();
            }
            // car
            SExpression car = sExpression();

            // '.'
            if (this.token.getKind() == Token.Kind.DOT) {
                this.token = this.lexer.getNextToken();
                // cdr
                SExpression cdr = sExpression();

                if (this.token.getKind() != Token.Kind.RIGHTPAR) {
                    throw new SyntaxErrorException("')' expected");
                }
                this.nestingLevel--;
                if (this.nestingLevel != 0) { // 式が未完成
                    this.token = this.lexer.getNextToken();
                }
                return ConsCell.getInstance(car, cdr);

            } else {
                // 先読みトークンがドットでないなら
                return ConsCell.getInstance(car, getList());
            }
        }
        throw new SyntaxErrorException("Invalid expression:" + this.token.getKind());
    }

    /**
     * 現在の先読みトークンをリストの次の要素として読み込むメソッド
     *
     * @return 次の要素（ConsCell型）
     * @throws IOException
     * @throws LispException
     */
    private SExpression getList() throws IOException, LispException {
        SExpression exp = null;
        SExpression car = null;
        SExpression cdr = null;

        if (this.token.getKind() == Token.Kind.RIGHTPAR) {
            this.nestingLevel--;
            if (this.nestingLevel != 0) { // 式が未完成
                this.token = this.lexer.getNextToken();
            }
            exp = EmptyList.getInstance();
        } else {
            car = sExpression();
            if (this.token.getKind() != Token.Kind.DOT) {
                exp = ConsCell.getInstance(car, getList());
            } else {
                // リストの最後がドット対 (1 2 . 3)
                this.token = this.lexer.getNextToken();
                cdr = sExpression();
                this.nestingLevel--;
                if (this.nestingLevel != 0) { // 式が未完成
                    this.token = this.lexer.getNextToken();
                }
                exp = ConsCell.getInstance(car, cdr);
            }
        }
        return exp;
    }


    public SExpression read() throws IOException, LispException {
        this.nestingLevel = 0;
        this.token = this.lexer.getNextToken();
        return sExpression();
    }
}
