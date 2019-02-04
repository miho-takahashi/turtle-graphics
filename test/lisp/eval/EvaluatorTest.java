package lisp.eval;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import lisp.exception.LispException;
import lisp.reader.Reader;

/**
 * Evaluatorのテスト（システムテスト）
 *
 * @author ryu2153
 *
 */

public class EvaluatorTest {

	@Test
	public void 述語listの評価() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(list? (quote (1 2 3)))";
		SExpression expected = Bool.valueOf(true);

		//String inputExp = "(list (quote ((1 2) 3)))";
		//SExpression expected = Symbol.getInstance("#f");

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void 手続きappendの評価() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(append '(1) '(2 3))";
		String expected = "(1 2 3)";

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		String actual = Evaluator.eval(reader.read(), env).toString();

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void 述語eqの評価_真() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(eq? 1 1)";
		SExpression expected = Bool.valueOf(true);

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void 述語eqの評価_偽() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(eq? 1 1.0)";
		SExpression expected = Bool.valueOf(false);

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void 手続きmapの評価() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(map + '(1 2 3) '(4 5 6))";
		String expected = "(5 7 9)";

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		String actual = Evaluator.eval(reader.read(), env).toString();

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void 特殊形式ifの評価_真() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(if (= 1 1) 1 2)";
		SExpression expected = Int.valueOf(1);

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void 特殊形式lambdaの評価() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "((lambda (x y) (+ x y)) 1 2)";
		SExpression expected = Int.valueOf(3);

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void 記号の定義() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(define x 1)";
		SExpression expected = Symbol.getInstance("x");

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void クロージャの作成と束縛() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(define add1 (lambda (x y) (+ x y)))";
		SExpression expected = Symbol.getInstance("add1");
		//        SExpression expected = Int.valueOf(5);

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
		assertThat(actual, is(expected));

		//        String inputExp2 = "(add1 2 3)";
		//        SExpression expected2 = Int.valueOf(5);
		//        Reader reader2 = new Reader(new BufferedReader(new StringReader(inputExp2)));
		//        SExpression actual2 = Evaluator.eval(reader2.read(), env);
		//        assertThat(actual2, is(expected2));
	}

	@Test
	public void carを使ったS式の評価() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(car (quote (1 2 3)))";
		SExpression expected = Int.valueOf(1);

		StringReader sr = new StringReader(inputExp);
		BufferedReader br = new BufferedReader(sr);
		Reader reader = new Reader(br);
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void cdrを使ったS式の評価() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(cdr (quote (1 2 3)))";
		SExpression expected = ConsCell.getInstance(Int.valueOf(2),
				ConsCell.getInstance(Int.valueOf(3), EmptyList.getInstance()));

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
//		assertThat(actual, is(expected));
        assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void quoteを使ったS式の評価() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(quote (1 2 3))";
		SExpression expected = ConsCell.getInstance(Int.valueOf(1),
				ConsCell.getInstance(Int.valueOf(2), ConsCell.getInstance(Int.valueOf(3), EmptyList.getInstance())));
		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
//		assertThat(actual, is(expected));
        assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void sinを使ったS式の評価() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(sin 3.14159)";
		SExpression expected = Double.valueOf(2.65358979335273e-6);

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void cosを使ったS式の評価() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(cos 3.14159)";
		SExpression expected = Double.valueOf(-0.9999999999964793);

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
		assertThat(actual, is(expected));
	}

	@Test
	public void consドット対の構築() throws IOException, LispException {
		// setUp 初期化
		String inputExp = "(cons 1 2)";
		SExpression expected = ConsCell.getInstance(Int.valueOf(1), Int.valueOf(2));

		Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual = Evaluator.eval(reader.read(), env);

		// Verify 検証
//		assertThat(actual, is(expected));
        assertEquals(expected.toString(), actual.toString());
    }
    @Test
    public void quoteの省略記法の評価() throws IOException, LispException {
        // setUp 初期化
        String inputExp = "'(1 2 3)";
        SExpression expected = ConsCell.getInstance(Int.valueOf(1), ConsCell.getInstance(Int.valueOf(2),ConsCell.getInstance(Int.valueOf(3),EmptyList.getInstance())));

        Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
        Environment env = new Environment();

        // Exercise テストの実行
        SExpression actual = Evaluator.eval(reader.read(), env);

        // Verify 検証
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void lambdaを使ったS式の評価1() throws IOException, LispException {
        // setUp 初期化
        String inputExp = "((lambda (x1 x2) x1) 1 2)";
        String expected = "1";

        Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
        Environment env = new Environment();

        // Exercise テストの実行
        String actual = Evaluator.eval(reader.read(), env).toString();

        // Verify 検証
        assertThat(actual, is(expected));
    }

    @Test
    public void lambdaを使ったS式の評価2() throws IOException, LispException {
        // setUp 初期化
        String inputExp = "((lambda (x1 x2) x2) 1 2)";
        String expected = "2";

        Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
        Environment env = new Environment();

        // Exercise テストの実行
        String actual = Evaluator.eval(reader.read(), env).toString();

        // Verify 検証
        assertThat(actual, is(expected));
    }

    @Test
    public void lambdaを使ったS式の評価3() throws IOException, LispException {
        // setUp 初期化
        String inputExp = "((lambda x x) 1 2 3)";
        String expected = "(1 2 3)";

        Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
        Environment env = new Environment();

        // Exercise テストの実行
        String actual = Evaluator.eval(reader.read(), env).toString();

        // Verify 検証
        assertThat(actual, is(expected));
    }

    @Test
    public void lambdaを使ったS式の評価4() throws IOException, LispException {
        // setUp 初期化
        String inputExp = "((lambda x x))";
        String expected = "()";

        Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
        Environment env = new Environment();

        // Exercise テストの実行
        String actual = Evaluator.eval(reader.read(), env).toString();

        // Verify 検証
        assertThat(actual, is(expected));
    }

    @Test
    public void lambdaを使ったS式の評価5() throws IOException, LispException {
        // setUp 初期化
        String inputExp = "((lambda (x1 x2 . z) x1) 1 2 3 4)";
        String expected = "1";

        Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
        Environment env = new Environment();

        // Exercise テストの実行
        String actual = Evaluator.eval(reader.read(), env).toString();

        // Verify 検証
        assertThat(actual, is(expected));
    }

    @Test
    public void lambdaを使ったS式の評価6() throws IOException, LispException {
        // setUp 初期化
        String inputExp = "((lambda (x1 x2 . z) x2) 1 2 3 4)";
        String expected = "2";

        Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
        Environment env = new Environment();

        // Exercise テストの実行
        String actual = Evaluator.eval(reader.read(), env).toString();

        // Verify 検証
        assertThat(actual, is(expected));
    }

    @Test
    public void lambdaを使ったS式の評価7() throws IOException, LispException {
        // setUp 初期化
        String inputExp = "((lambda (x1 x2 . z) z) 1 2 3 4)";
        String expected = "(3 4)";

        Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
        Environment env = new Environment();

        // Exercise テストの実行
        String actual = Evaluator.eval(reader.read(), env).toString();

        // Verify 検証
        assertThat(actual, is(expected));
    }

    @Test
    public void lambdaを使ったS式の評価8() throws IOException, LispException {
        // setUp 初期化
        String inputExp = "((lambda (x1 x2 . z) z) 1 2)";
        String expected = "()";

        Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
        Environment env = new Environment();

        // Exercise テストの実行
        String actual = Evaluator.eval(reader.read(), env).toString();

        // Verify 検証
        assertThat(actual, is(expected));
    }

    @Test
    public void lambdaを使ったS式の評価9() throws IOException, LispException {
        // setUp 初期化
        String inputExp1 = "(define (f x) ((lambda (p q) (+ x p q)) 10 20))";
        String inputExp2 = "(f 30)";
        String expected = "60";

        Reader reader1 = new Reader(new BufferedReader(new StringReader(inputExp1)));
        Reader reader2 = new Reader(new BufferedReader(new StringReader(inputExp2)));
        Environment env = new Environment();

        // Exercise テストの実行
        SExpression actual1 = Evaluator.eval(reader1.read(), env);
        SExpression actual2 = Evaluator.eval(reader2.read(), env);

        // Verify 検証
        assertEquals(expected, actual2.toString());
    }

	@Test
	public void letを使ったS式の評価() throws IOException, LispException {
		// setUp 初期化
		String inputExp1 = "(define (f x) (let ((p 10) (q 20)) (+ p q x)))";
		String inputExp2 = "(f 30)";
		String expected = "60";

		Reader reader1 = new Reader(new BufferedReader(new StringReader(inputExp1)));
		Reader reader2 = new Reader(new BufferedReader(new StringReader(inputExp2)));
		Environment env = new Environment();

		// Exercise テストの実行
		SExpression actual1 = Evaluator.eval(reader1.read(), env);
		SExpression actual2 = Evaluator.eval(reader2.read(), env);

		// Verify 検証
		assertEquals(expected, actual2.toString());
	}
}


