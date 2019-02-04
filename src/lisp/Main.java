package lisp;

import java.io.*;

import lisp.eval.Environment;
import lisp.eval.Evaluator;
import lisp.eval.SExpression;
import lisp.exception.EndOfFileException;
import lisp.exception.ExitException;
import lisp.exception.LispException;
import lisp.reader.Reader;

/**
 * Lispインタプリタ
 * 
 * @author tetsuya
 *
 */
public class Main {
	/**
	 * 言語処理系の名前などを表示する。
	 * 
	 * @return なし
	 */
	public static lisp.eval.Window window = new lisp.eval.Window(480, 480); // 描画用ウインドウの呼び出し

	static void printGreetingMessage() {

		System.out.println("*************************************");
		System.out.println("");
		System.out.println("           Zwei Lisp ");
		System.out.println("");
		System.out.println("*************************************");
	}

	/**
	 * Lispインタプリタの対話的実行
	 * 
	 * @param args コマンドライン引数。引数が0個の時は対話処理、1個の時はバッチ処理、2個以上の時は起動を中断
	 * @throws IOException
	 *             入出力エラー
	 */
	public static void main(String[] args) throws IOException {
//        System.out.println("args length:" + args.length);

        if (args.length == 0) {
            // 対話処理
            printGreetingMessage();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Reader reader = new Reader(in);
            Environment env = new Environment();

            try {
                while (true) {
                    try {
                        System.out.print("lisp> ");
                        SExpression exp = reader.read();
                        SExpression value = Evaluator.eval(exp, env);
                        System.out.println(value);
                    } catch (EndOfFileException e) {
                        break;
                    } catch (ExitException e) {
                        window.dispose(); // ウインドウを消す
                        System.out.println("Exiting Zwei Lisp.");
                        break;
                    } catch (LispException e) {
                        System.err.println(e.getMessage());
                    }
                }
            } catch (Exception e) {
            }
            in.close();
        } else if (args.length == 1){
            // バッチ処理
            try {
                // テキストファイルから読み取る入力ストリームの生成
                FileReader file = new FileReader(args[0]);

                // ファイルに入力ストリームを接続
                BufferedReader in = new BufferedReader(file);

                Reader reader = new Reader(in);
                Environment env = new Environment();

                while (true) {
                    try {
//                        SExpression exp = reader.read();
//                        SExpression value = Evaluator.eval(exp, env);
                        Evaluator.eval(reader.read(), env);
                    } catch (EndOfFileException e) {
                        break;
                    } catch (ExitException e) {
                        window.dispose(); // ウインドウを消す
                        System.out.println("Exiting Zwei Lisp.");
                        break;
                    } catch (LispException e) {
                        System.err.println(e.getMessage());
                    }
                }

                in.close();
            }
            catch(FileNotFoundException e){
                System.err.println("指定したファイルがありません");
                System.exit(1);
            }
        }
    }

}
