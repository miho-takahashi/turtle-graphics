package lisp.Subroutine;

import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.SExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * 組み込み手続き（append）
 * 
 * @author ryu2153, Toyoshima
 *
 */

public class SubroutineAppend extends Subroutine {
	@Override
	public SExpression eval(SExpression sexp) {
		List<SExpression> returnList = new ArrayList<>();

		if (sexp instanceof EmptyList) return EmptyList.getInstance();

		SExpression cell1 = sexp;
		SExpression cell2;

        while (!cell1.equals(EmptyList.getInstance())) {
            if (cell1 instanceof ConsCell){
                cell2 = ((ConsCell) cell1).getCar();
                while (!cell2.equals(EmptyList.getInstance())) {
                    returnList.add(((ConsCell) cell2).getCar());

                    cell2 = ((ConsCell) cell2).getCdr();
                }
                cell1 = ((ConsCell) cell1).getCdr();
            }
        }

        SExpression[] returnArray = new SExpression[returnList.size()];

        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = returnList.get(i);
        }

        return ConsCell.createList(returnArray);
	}

	public String toString() {
		return "#<subr append>";
	}
}
