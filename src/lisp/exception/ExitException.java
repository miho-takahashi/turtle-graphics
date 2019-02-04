package lisp.exception;

/**
 * 終了命令が入力された例外
 *
 * @author ryu2153
 *
 */
@SuppressWarnings("serial")
public class ExitException extends LispException {
    /**
     * 空のエラーメッセージを持つ例外を構築する。
     */
    public ExitException() {
        super("");
    }

    /**
     * 指定されたエラーメッセージを持つ例外を構築する。
     *
     * @param msg エラーメッセージ
     */
    public ExitException(String msg) {
        super(msg);
    }
}
