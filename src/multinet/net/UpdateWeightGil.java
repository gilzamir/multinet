package multinet.net;


import java.util.ArrayList;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class UpdateWeightGil implements UpdateWeightStrategy {

    public final static int OUTPUTS = 3;
    
    @Override
    public void update(final NeuralNet net) {
        if (!net.plasticityEnabled) {
            return;
        }
        ArrayList<Neuron> neurons = net.getNeurons();
     
        final double maxEnergy = 25000;
        double energy = net.lambda;
        net.numberOfUpdates = 0.0;
        double rho = energy/maxEnergy;
        
        for (int i = 0; i < net.getSize(); i++) {
            Neuron pos = neurons.get(i);
            double oi = pos.getFunction().exec(pos.getState()) * net.outputGain;
 
            double h = pos.getShift() + pos.getAmp();
            double l = pos.getShift() - pos.getAmp();
            double m = pos.getShift();
            double p = 0.0;
            
           
            if (p > h || p < l || rho < 0.9) {
                p = (m - oi) * Math.exp(1-rho);
            }

            for (int j = 0; j < net.getSize(); j++) {
                Neuron pre = neurons.get(j);
                double wi = net.getWeight(j, i);
                
                if (wi == 0) {
                    p = 0;
                }
   

                double oj = pre.getFunction().exec(pre.getState()) * net.outputGain;

                double plasticity = net.getPlasticity(j, i);
    
                double wi1 = 0;
                
                if (pos.getLearningMethod() == 0) {
                    wi1 = plasticity * oj * p;
                } else if (pos.getLearningMethod() == 1) {
                    wi1 = plasticity * oj * (-p);
                } else if (pos.getLearningMethod() == 2) {
                    wi1 = plasticity * p;
                }
                
                net.numberOfUpdates += Math.abs(wi1);
                
                wi = wi + wi1;

                net.setWeight(j, i, wi);                                        
            }
        }
        final double rate = net.numberOfUpdates/(double)(net.getSize()*net.getSize());
        NeuralNetEvent ev = new NeuralNetEvent() {
            @Override
            public String getMessage() {
                return "Update rate: " + rate;
            }

            @Override
            public NeuralNet getSource() {
                return net;
            }

            @Override
            public Object[] getData() {
                return null;
            }
        };
        
        if (net.getListener() != null) {
            net.getListener().handleUpdateWeight(ev);
        }
    }

    @Override
    public void init(NeuralNet net) {
    } 
}
