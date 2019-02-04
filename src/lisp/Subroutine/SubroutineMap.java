package lisp.Subroutine;

import lisp.eval.*;
import lisp.exception.LispException;
import lisp.exception.SyntaxErrorException;

/**
 * 組み込み手続き（map）
 *
 * @author ryu2153
 */

public class SubroutineMap extends Subroutine {
    @Override
    public SExpression eval(SExpression args) throws LispException {
        SExpression func = ((ConsCell) args).getCar();
        ConsCell argsCell = (ConsCell) ((ConsCell) args).getCdr();
        ConsCell cell = null;

        // 受け取るリストの数
        int argsNum = argsCell.getLength();

        // 一つ目ののリストの長さ
        int listNum = ((ConsCell) argsCell.getCar()).getLength();

//        System.out.println("argsNum = " + argsNum + " listNum = " + listNum);

        // 各リストの要素数が揃っているかを確かめる
        cell = (ConsCell)args;

        for (int i = 0; i < argsNum; i++) {
            cell = ((ConsCell)cell.getCdr());
            if(listNum != ((ConsCell)cell.getCar()).getLength()){
                throw new SyntaxErrorException("Argument list's are Incorrect.");
            }
        }

        // 要素を二次元配列にまとめる
        SExpression[][] expArray = new SExpression[listNum][argsNum];

        for (int i = 0; i < argsNum ; i++) {
            cell = (ConsCell) argsCell.getCar();
            for (int j = 0; j < listNum; j++) {
                expArray[j][i] = cell.getCar();
//                System.out.println("expArray[" + j + "][" + i + "] = " + expArray[j][i]);

                if (cell.getCdr() instanceof ConsCell) cell = (ConsCell) cell.getCdr();
            }
            if (argsCell.getCdr() instanceof ConsCell) argsCell = (ConsCell) argsCell.getCdr();
        }

//        System.out.println("Subroutine = " + subr);
//        System.out.println("exp = " + createConsCellList(expArray));

        // 二次元配列をConsCellリストへ組み直し
        SExpression[] evaluatedList = new SExpression[listNum];

//        System.out.println("isClosure?" + (func instanceof Closure));
//        System.out.println("isConsCell?" + (func instanceof ConsCell));

        if (func instanceof Subroutine) {
            Subroutine subr = (Subroutine) func;
            for (int i = 0; i < listNum; i++) {
                SExpression exp = ConsCell.createList(expArray[i]);
                evaluatedList[i] = subr.eval(exp);
            }
        } else if (func instanceof Closure){
            Closure cl = (Closure) func;
            for (int i = 0; i < listNum; i++) {
                SExpression exp = ConsCell.createList(expArray[i]);
                evaluatedList[i] = cl.eval(exp);
            }
        } else {
            throw new SyntaxErrorException("Unexpected map func:" + func);
        }

        return ConsCell.createList(evaluatedList);
    }

    @Override
    public String toString() {
        return "#<subr map>";
    }
}

/*
 (map + '(1 2 3) '(4 5 6))
 1 2 3
 4 5 6

 (map + '(1 2 3 4) '(5 6 7 8))
 */