package lisp.reader;

/**
 * 字句
 *
 * @author tetsuya
 */
public class Token {
    /**
     * 字句の種類
     *
     * @author tetsuya
     */
    public enum Kind {
        NUMBER, // 数値
        DOUBLE, // 小数値
        BOOLEAN, // 真理値
        SYMBOL, // 記号
        LEFTPAR, // 左括弧
        RIGHTPAR, // 右括弧
        DOT, // ドット
        QUOTATION  // 一重引用符
    }

    private Kind kind;
    private int intValue;
    private double doubleValue;
    private boolean booleanValue;
    private String symbol;

    /**
     * 字句の種類を得る。
     *
     * @return 字句の種類
     */
    Kind getKind() {
        return this.kind;
    }

    /**
     * 整数値を得る。
     *
     * @return 整数値
     */
    int getIntValue() {
        return this.intValue;
    }

    /**
     * 小数値を得る。
     *
     * @return 整数値
     */
    double getDoubleValue() {
        return this.doubleValue;
    }

    /**
     * 真理値を得る。
     *
     * @return 真理値
     */
    boolean getBooleanValue() {
        return this.booleanValue;
    }

    /**
     * 記号名を得る。
     *
     * @return 記号名
     */
    String getSymbolValue() {
        return this.symbol;
    }

    /**
     * 種類だけを指定して字句を構築する。
     *
     * @param kind 字句の種類
     */
    Token(Kind kind) {
        this.kind = kind;
    }

    /**
     * 整数の字句
     *
     * @param value 整数値
     */
    Token(int value) {
        this.kind = Kind.NUMBER;
        this.intValue = value;
    }

    /**
     * 浮動小数点数の字句
     *
     * @param value 整数値
     */
    Token(double value) {
        this.kind = Kind.DOUBLE;
        this.doubleValue = value;
    }


    /**
     * 真理値の字句
     *
     * @param value 真理値
     */
    Token(boolean value) {
        this.kind = Kind.BOOLEAN;
        this.booleanValue = value;
    }

    /**
     * 記号の字句
     *
     * @param value 記号名
     */
    Token(String value) {
        this.kind = Kind.SYMBOL;
        this.symbol = value;
    }

    @Override
    public String toString() {
        // 数値
        if (this.kind == Kind.NUMBER) {
            return "Token (Number, " + this.intValue + ")";
        }
        if (this.kind == Kind.DOUBLE) {
            return "Token (Number, " + this.doubleValue + ")";
        }
        // 真理値
        if (this.kind == Kind.BOOLEAN) {
            return "Token (Boolean, " + this.booleanValue + ")";
        }
        // 記号
        if (this.kind == Kind.SYMBOL) {
            return "Token (Symbol, " + this.symbol + ")";
        }
        // 左括弧
        if (this.kind == Kind.LEFTPAR) {
            return "Token (LeftPar)";
        }
        // 右括弧
        if (this.kind == Kind.RIGHTPAR) {
            return "Token (RightPar)";
        }
        // ドット
        if (this.kind == Kind.DOT) {
            return "Token (Dot)";
        }
        // 一重引用符
        if (this.kind == Kind.QUOTATION) {
            return "Token (Quotation)";
        }

        return "Token (Unknown)";
    }
}
