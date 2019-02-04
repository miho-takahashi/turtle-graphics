package lisp.eval;

/**
 * Cons cell (ドット対)
 *
 * @author ryu2153
 */
public class ConsCell implements SExpression {
    private SExpression car;
    private SExpression cdr;

    public SExpression getCar() {
        return this.car;
    }

    public SExpression getCdr() {
        return this.cdr;
    }

    private ConsCell(SExpression car, SExpression cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    /**
     * Cons Cell（ドット対）を構築する。
     *
     * @param car car部
     * @param cdr cdr部
     * @return 指定されたcar部とcdr部で構成されるCons Cell（ドット対）
     */
    public static ConsCell getInstance(SExpression car, SExpression cdr) {
        return new ConsCell(car, cdr);
    }

    @Override
    public String toString() {
        SExpression cdr = this.getCdr();
        if (cdr instanceof ConsCell) {
            return "(" + this.toStringOmit() + ")";

        } else if (cdr instanceof EmptyList) {
            return this.car.toString();
        }
        return "(" + this.car + " . " + this.cdr + ")";
    }

    /**
     * ドット対の省略形式を表示できるようにするための内部メソッド
     * @return
     */
    private String toStringOmit() {
        if (cdr instanceof ConsCell) {
            return this.car + " " + ((ConsCell) this.cdr).toStringOmit();
        } else if (cdr instanceof EmptyList) {
            return this.car.toString();
        } else {
            return this.car.toString() + " . " + this.cdr.toString();
        }
    }

    /**
     * ConsCellリストの長さを取得する。
     * @return リストの長さ
     */
    public int getLength() {
        SExpression cell = this;
        int count = 0;

        while (!cell.equals(EmptyList.getInstance())) {
            count++;
            try {
                cell = ((ConsCell) cell).getCdr();
            } catch (ClassCastException e){
                count -= 1;
                return count;
            }
        }
        return count;
    }

    /**
     * S式の配列をConsCellのリストに変換する（2次元）
     * @param expArray S式の2次元配列
     * @return ConsCellリストの開始セル
     */
    public static SExpression createList(SExpression[][] expArray) {
        int len = expArray.length;
        SExpression list = EmptyList.getInstance();

        for (int i = 0; i < len; i++) {
            list = ConsCell.getInstance(ConsCell.createList(expArray[len - i - 1]), list);
        }
        return list;
    }

    /**
     * S式の配列をConsCellのリストに変換する（1次元）
     * @param expArray S式の1次元配列
     * @return ConsCellリストの開始セル
     */
    public static SExpression createList(SExpression[] expArray) {
        int len = expArray.length;
        SExpression list = EmptyList.getInstance();

        for (int i = 0; i < len; i++) {
            list = ConsCell.getInstance(expArray[len - i - 1], list);
        }
        return list;
    }
}
