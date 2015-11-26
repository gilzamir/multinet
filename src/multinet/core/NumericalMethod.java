package multinet.core;

public abstract class NumericalMethod extends  AbstractComponent {
    
    public abstract double nextState(double currentState, NumericalProducer method);
}
