package lisp.eval;

public class Double implements SExpression {
	private java.lang.Double value;

	public double getValue() {
		return value;
	}

	private Double(double value) {
		this.value = value;
	}

	public static Double valueOf(double value) {
		return new Double(value);
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof Double) == false) {
			return false;
		}
		return this.value.equals(((Double) obj).getValue());
	}
}
