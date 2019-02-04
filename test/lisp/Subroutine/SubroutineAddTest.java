package lisp.Subroutine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.Int;
import lisp.eval.SExpression;
import lisp.eval.Symbol;

public class SubroutineAddTest {

	@Test
	public void 引数なし() throws lisp.exception.LispException {
		SubroutineAdd add = new SubroutineAdd();
		SExpression args = EmptyList.getInstance();

		SExpression actual = add.eval(args);
		SExpression expected = (Int) Int.valueOf(0);
		assertThat(actual, is(expected));
	}

	@Test
	public void 整数() throws lisp.exception.LispException {
		SubroutineAdd add = new SubroutineAdd();
		SExpression args = ConsCell.getInstance(Int.valueOf(Integer.MAX_VALUE), EmptyList.getInstance());

		SExpression actual = add.eval(args);
		SExpression expected = (Int) Int.valueOf(Integer.MAX_VALUE);
		assertThat(actual, is(expected));
	}

	@Test
	public void 整数_整数() throws lisp.exception.LispException {
		SubroutineAdd add = new SubroutineAdd();
		SExpression args = ConsCell.getInstance(Int.valueOf(1),
				ConsCell.getInstance(Int.valueOf(2), EmptyList.getInstance()));

		SExpression actual = add.eval(args);
		SExpression expected = (Int) Int.valueOf(3);
		assertThat(actual, is(expected));
	}

	@Test
	public void 整数_整数_整数() throws lisp.exception.LispException {
		SubroutineAdd add = new SubroutineAdd();
		SExpression args = ConsCell.getInstance(Int.valueOf(1),
				ConsCell.getInstance(Int.valueOf(2), ConsCell.getInstance(Int.valueOf(3), EmptyList.getInstance())));

		SExpression actual = add.eval(args);
		SExpression expected = (Int) Int.valueOf(6);
		assertThat(actual, is(expected));
	}

	@Test
	public void 整数_浮動小数点数() throws lisp.exception.LispException {
		SubroutineAdd add = new SubroutineAdd();
		SExpression args = ConsCell.getInstance(Int.valueOf(1),
				ConsCell.getInstance(lisp.eval.Double.valueOf(2.0), EmptyList.getInstance()));

		SExpression actual = add.eval(args);
		SExpression expected = lisp.eval.Double.valueOf(3.0);
		assertThat(actual, is(expected));
	}

	@Test
	public void 浮動小数点数_整数() throws lisp.exception.LispException {
		SubroutineAdd add = new SubroutineAdd();
		SExpression args = ConsCell.getInstance(lisp.eval.Double.valueOf(2.0),
				ConsCell.getInstance(Int.valueOf(1), EmptyList.getInstance()));

		SExpression actual = add.eval(args);
		SExpression expected = lisp.eval.Double.valueOf(3.0);
		assertThat(actual, is(expected));
	}

	@Test
	public void 浮動小数点数_浮動小数点数() throws lisp.exception.LispException {
		SubroutineAdd add = new SubroutineAdd();
		SExpression args = ConsCell.getInstance(lisp.eval.Double.valueOf(2.0),
				ConsCell.getInstance(lisp.eval.Double.valueOf(1.0), EmptyList.getInstance()));

		SExpression actual = add.eval(args);
		SExpression expected = lisp.eval.Double.valueOf(3.0);
		assertThat(actual, is(expected));
	}

	@Test(expected = lisp.exception.LispException.class)
	public void 記号() throws lisp.exception.LispException {
		SubroutineAdd add = new SubroutineAdd();
		SExpression args = ConsCell.getInstance(Symbol.getInstance("x"), EmptyList.getInstance());
		SExpression actual = add.eval(args);
	}

}
