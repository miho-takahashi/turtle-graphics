package lisp.eval;

import java.util.ArrayList;
import java.util.List;

import lisp.SpecialForm.*;
import lisp.Subroutine.*;

/**
 * 環境
 *
 * @author ryu2153
 */
public class Environment {
	private List<ConsCell> frameList;
	private Environment outerEnv;

	public Environment() {
		frameList = new ArrayList<ConsCell>();
		outerEnv = null;

		// 組み込み手続きの束縛
		addBinding("+", new SubroutineAdd());
		addBinding("-", new SubroutineMinus());
		addBinding("*", new SubroutineMultiply());
		addBinding("/", new SubroutineDiv());
		addBinding("modulo", new SubroutineModulo());
		addBinding("sin", new SubroutineSin());
		addBinding("cos", new SubroutineCos());
		addBinding("car", new SubroutineCar());
		addBinding("cdr", new SubroutineCdr());
		addBinding("cons", new SubroutineCons());
		addBinding("exit", new SubroutineExit());
		addBinding("append", new SubroutineAppend());
		addBinding("list", new SubroutineList());
		addBinding("map", new SubroutineMap());
		addBinding("write", new SubroutineWrite());
		addBinding("newline", new SubroutineNewline());
		addBinding("draw-line", new SubroutineDrawLine());

        // 述語
        addBinding("eq?", new SubroutineEq());
        addBinding("null?", new SubroutineNullQ());
        addBinding("list?", new SubroutineListQ());
        // 条件式
        addBinding("=", new SubroutineEqual());
        addBinding("<", new SubroutineIncreasing());
        addBinding("<=", new SubroutineNonDecreasing());
        addBinding(">", new SubroutineDecreasing());
        addBinding(">=", new SubroutineNonIncreasing());
        addBinding("not", new SubroutineNot());

        // 特殊形式
        addBinding("define", new SpecialFormDefine());
        addBinding("set!", new SpecialFormSet());
        addBinding("quote", new SpecialFormQuote());
        addBinding("if", new SpecialFormIf());
        addBinding("lambda", new SpecialFormLambda());
        addBinding("let", new SpecialFormLet());

        // 追加で実装したもの
        addBinding("length", new SubroutineLength());
	}

	public Environment(Environment env) {
		frameList = new ArrayList<ConsCell>();
		this.outerEnv = env;
	}

	/**
	 * 環境の下で記号symbolに束縛された値を探索する
	 *
	 * @param symbol
	 *            探索するSymbol
	 * @return 見つかった値（SExpression）
	 */
	SExpression searchCdr(Symbol symbol) {
		// 現在のフレームで探索
		for (int i = 0; i < frameList.size(); i++) {
			ConsCell frame = frameList.get(i);
			if (symbol.equals(frame.getCar()))
				return frame.getCdr();
		}

		// 外側の環境に探しに行く
		if (outerEnv == null)
			return Undef.getInstance();
		return outerEnv.searchCdr(symbol);
	}

	/**
	 * 現在のフレームで新たに束縛を行う
	 *
	 * @param str 束縛する記号（String）
	 * @param exp 束縛するS式
	 */
	private void addBinding(String str, SExpression exp) {
		addBinding(Symbol.getInstance(str), exp);
	}

	/**
	 * 現在のフレームで新たに束縛を行う
	 * @param symbol 束縛する記号（Symbol）
	 * @param exp 束縛するS式
	 */
	public void addBinding(Symbol symbol, SExpression exp){
		this.frameList.add(ConsCell.getInstance(symbol, exp));
	}

	/*
	 * 定義済みの束縛を更新する（set!)
	 * 
	 * @param symbol
	 * 
	 * @param sexp
	 */

	public void updateBinding(Symbol symbol, SExpression sexp){
//        System.out.println("updateBinding");
        for (int i = 0; i < frameList.size(); i++) {
            ConsCell frame = frameList.get(i);
//            System.out.println(symbol + " - " + frame.getCar() + " : " + frame.getCar().equals(Symbol.getInstance(symbol)));
            if (frame.getCar().equals(symbol)){
                // いったん取り除く
                frameList.remove(i);

                // 新たに束縛する（frameListのiの位置に追加）
                this.frameList.add(i, ConsCell.getInstance(symbol, sexp));
            }
		}
	}
}
