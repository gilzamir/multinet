package multinet.core;

public class RungeKuttaMethod extends NumericalMethod {

    public RungeKuttaMethod() {
        super();
        setDouble("step", 0.001);
    }
    
    @Override
    public double nextState(double state, NumericalProducer producer) {
   //4th Order Runge-Kutta ====================================================
            double k1, k2, k3, k4;
            double step = getDouble("step");
            double pState = state;
            
            k1 = producer.produce(state);
            
            state = pState + k1 * step / 2.0;

            k2 = producer.produce(state);
            
            state = pState + k2 * step / 2.0;

            k3 = producer.produce(state);
            
            state = pState + k3 * step;

            k4 = producer.produce(state);
            
            state = pState + (k1 + 2 * k2 + 2 * k3 + k4) * step / 6.0;
            
            if (state > 100)
            {
                state = 100;
            }
            else if (state < -100)
            {
                state = -100;
            }
            return state;
    }
}
