package lisp.Subroutine;

import lisp.eval.*;
import lisp.exception.LispException;
import lisp.reader.Reader;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class SubroutineMapTest {

    @Test
    public void mapのテスト1() throws IOException, LispException {
        // setUp 初期化
        String inputExp = "(map + '(1 2 3) '(4 5 6))";
        SExpression expected = ConsCell.getInstance(Int.valueOf(5), ConsCell.getInstance(Int.valueOf(7), ConsCell.getInstance(Int.valueOf(9), EmptyList.getInstance())));

        Reader reader = new Reader(new BufferedReader(new StringReader(inputExp)));
        Environment env = new Environment();

        // Exercise テストの実行
        SExpression actual = Evaluator.eval(reader.read(), env);

        // Verify 検証
        assertEquals(expected.toString(), actual.toString());
    }
}