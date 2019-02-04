package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.Double;
import lisp.eval.EmptyList;
import lisp.eval.Int;
import lisp.eval.SExpression;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（+, 加算）
 *
 * @author ryu2153
 */

public class SubroutineAdd extends Subroutine {

	@Override
	public SExpression eval(SExpression sexp) throws SyntaxErrorException {
		double sum = 0;
		int check;
		boolean flag = false;
		if (sexp instanceof ConsCell) {
			SExpression cell = sexp;
			while (!cell.equals(EmptyList.getInstance())) {
				SExpression exp = ((ConsCell) cell).getCar();

				// 整数か小数かを判断して合計
				if (exp instanceof Int) {
					sum += ((Int) exp).getValue();
				} else if (exp instanceof lisp.eval.Double) {
					sum += ((lisp.eval.Double) exp).getValue();
					flag = true;
				} else {
					throw new SyntaxErrorException("Argument Error: Int or Double Expected");
				}
				cell = ((ConsCell) cell).getCdr();
			}
		} else if (sexp instanceof EmptyList) {
			sum = 0;
		} else {
			throw new SyntaxErrorException("Argument error");
		}
		check = (int) sum;
		if ((double) check == sum && flag == false) {
			return Int.valueOf(check);
		} else {
			return Double.valueOf(sum);
		}
	}
  
    @Override
    public String toString() {
        return "#<subr add>";
    }
}
