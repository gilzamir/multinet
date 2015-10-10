package multinet.net;

import java.io.Serializable;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class Tanh implements Function, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1346264636287338117L;

	public double exec(double value) {
		return Math.tanh(value);
	}

	@Override
	public double getMinValue() {
		return -1.0;
	}

	@Override
	public double getMaxValue() {
		return 1.0;
	}
}