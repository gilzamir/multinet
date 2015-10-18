package multinet.net;

public class Identity implements Function {

    @Override
    public double exec(double value) {
        return value;
    }

    @Override
    public double getMinValue() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public double getMaxValue() {
        return Double.POSITIVE_INFINITY;
    }
}
