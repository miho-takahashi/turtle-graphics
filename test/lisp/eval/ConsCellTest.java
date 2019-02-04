package lisp.eval;

import lisp.exception.LispException;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConsCellTest {

    @Test
    public void getLengthのテスト1() throws IOException, LispException {
        // setUp 初期化
        ConsCell cell = ConsCell.getInstance(Int.valueOf(1), Int.valueOf(2));
        int expected = 1;

        // Exercise テストの実行
        int actual = cell.getLength();

        // Verify 検証
		assertThat(actual, is(expected));
    }

    @Test
    public void getLengthのテスト2() throws IOException, LispException {
        // setUp 初期化
        ConsCell cell = ConsCell.getInstance(Int.valueOf(1), ConsCell.getInstance(Int.valueOf(2),EmptyList.getInstance()));
        int expected = 2;

        // Exercise テストの実行
        int actual = cell.getLength();

        // Verify 検証
        assertThat(actual, is(expected));
    }
}
