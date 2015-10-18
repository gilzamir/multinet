package multinet.net;

public class Sin implements Function {

    @Override
    public double exec(double value) {
        return Math.sin(value);
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
