package multinet.net;

import java.io.Serializable;

public class Sigmoid implements Function, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7946857799377925833L;

	public double exec(double value) {
			return 1.0/(1 + Math.exp(-value));
	}

	@Override
	public double getMinValue() {
		return 0;
	}

	@Override
	public double getMaxValue() {
		return 1.0;
	}
}