package lisp.reader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import lisp.exception.LispException;

public class LexerTest {
	final int NUMBER=123;
	final String SYMBOL="X0";
	final String TRUE="#t";
	final String FALSE="#f";
	String input = "(" + NUMBER + ") . " + SYMBOL + " " + TRUE + " " + FALSE;

	@Test
	public void 左括弧の認識() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			Token actual = lexer.getNextToken();
			Token expected = new Token(Token.Kind.LEFTPAR);
			assertThat(actual.getKind(), is(expected.getKind()));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void 整数値の認識_字句の種類が正しい() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			lexer.getNextToken();
			Token.Kind actual = lexer.getNextToken().getKind();
			Token.Kind expected = Token.Kind.NUMBER;
			assertThat(actual, is(expected));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void 整数値の認識_字句の値が正しい() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			lexer.getNextToken();
			int actual = lexer.getNextToken().getIntValue();
			int expected = this.NUMBER;
			assertThat(actual, is(expected));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void 右括弧の認識() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			lexer.getNextToken();
			lexer.getNextToken();
			Token.Kind actual = lexer.getNextToken().getKind();
			Token.Kind expected = Token.Kind.RIGHTPAR;
			assertThat(actual, is(expected));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void ドットの認識() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			Token.Kind actual = lexer.getNextToken().getKind();
			Token.Kind expected = Token.Kind.DOT;
			assertThat(actual, is(expected));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void シンボルの認識_字句の種類が正しい() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			Token.Kind actual = lexer.getNextToken().getKind();
			Token.Kind expected = Token.Kind.SYMBOL;
			assertThat(actual, is(expected));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void シンボルの認識_字句の値が正しい() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			String actual = lexer.getNextToken().getSymbolValue();
			String expected = this.SYMBOL;
			assertThat(actual, is(expected));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void 真値の認識_字句の種類が正しい() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			Token.Kind actual = lexer.getNextToken().getKind();
			Token.Kind expected = Token.Kind.BOOLEAN;
			assertThat(actual, is(expected));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void 真値の認識_字句の値が正しい() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			boolean actual = lexer.getNextToken().getBooleanValue();
			boolean expected = true;
			assertThat(actual, is(expected));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}

	@Test
	public void 偽値の認識_字句の種類が正しい() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			Token.Kind actual = lexer.getNextToken().getKind();
			Token.Kind expected = Token.Kind.BOOLEAN;
			assertThat(actual, is(expected));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}
	@Test
	public void 偽値の認識_字句の値が正しい() {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Lexer lexer = new Lexer(br);
		try {
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			lexer.getNextToken();
			boolean actual = lexer.getNextToken().getBooleanValue();
			boolean expected = false;
			assertThat(actual, is(expected));
		} catch(Exception e) {
			fail("Exception thrown");
		}
	}
	
	@Test
	public void 不正な整数() {
		String str = "1x2";
		BufferedReader br = new BufferedReader(new StringReader(str));
		Lexer lexer = new Lexer(br);
		try {
			Token token = lexer.getNextToken();
			System.out.println(token);
		} catch(IOException e) {
			fail("Exception thrown");
		} catch(LispException e) {
			return;
		}
		fail();
	}

	@Test
	public void 不正な定数() {
		String str = "#tt";
		BufferedReader br = new BufferedReader(new StringReader(str));
		Lexer lexer = new Lexer(br);
		try {
			Token token = lexer.getNextToken();
			System.out.println(token);
		} catch(IOException e) {
			fail("Exception thrown");
		} catch(LispException e) {
			return;
		}
		fail();
	}

}
