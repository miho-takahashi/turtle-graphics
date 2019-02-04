package lisp.eval;

import lisp.Subroutine.SubroutineAdd;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class ClosureTest {
    @Test
    public void 仮引数の数と実引数の数が同じ() throws lisp.exception.LispException {
        // ((lambda (x1 x2) x1) 1 2)
        Environment env = new Environment();
        SExpression formals = ConsCell.getInstance(Symbol.getInstance("x1"), ConsCell.getInstance(Symbol.getInstance("x2"), EmptyList.getInstance()));
        SExpression body = Symbol.getInstance("x1");
        SExpression args = ConsCell.getInstance(Int.valueOf(1),ConsCell.getInstance(Int.valueOf(2),EmptyList.getInstance()));

        Closure closure = Closure.getInstance(formals, body, env);
        System.out.println("formals = " + formals.toString());
        System.out.println("   body = " + body.toString());
        System.out.println("   args = " + args.toString());

        SExpression actual = Evaluator.eval(closure.eval(args), env);
        SExpression expected = (Int) Int.valueOf(1);
        assertThat(actual, is(expected));
    }

    @Test
    public void 仮引数が1個で実引数の数は2個以上() throws lisp.exception.LispException {
        // ((lambda x x) 1 2 3)
        Environment env = new Environment();
        SExpression formals = Symbol.getInstance("x");
        SExpression body = Symbol.getInstance("x");
        SExpression args = ConsCell.getInstance(Int.valueOf(1),ConsCell.getInstance(Int.valueOf(2),ConsCell.getInstance(Int.valueOf(3),EmptyList.getInstance())));

        Closure closure = Closure.getInstance(formals, body, env);
        System.out.println("formals = " + formals.toString());
        System.out.println("   body = " + body.toString());
        System.out.println("   args = " + args.toString());

        SExpression actual = Evaluator.eval(closure.eval(args), env);
        SExpression expected = args;
        assertThat(actual, is(expected));
    }
}