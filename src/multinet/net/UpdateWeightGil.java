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
        for (int i = 0; i < net.getSize(); i++) {
            Neuron pos = neurons.get(i);
            double oi = pos.getFunction().exec(pos.getState()) * net.outputGain;
 
            double p = 1.0f - (pos.getAmp() * energy + pos.getShift())/maxEnergy;
            
            for (int j = 0; j < net.getSize(); j++) {
                Neuron pre = neurons.get(j);
                double wi = net.getWeight(j, i);
                
                if (wi == 0) {
                    p = 0;
                }
                
                double zij0 = (wi+100)/200.0; 
                           
                if (zij0 < 0.0) { 
                    zij0 = 0.0; 
                } else if (zij0 > 1.0) { 
                    zij0 = 1.0; 
                } 

                double oj = pre.getFunction().exec(pre.getState()) * net.outputGain;

                double plasticity = net.getPlasticity(j, i);

                
                double wi1 = 0;
                
                if (pos.getLearningMethod() == 0) {
                    wi1 = plasticity * oj * p * oi;
                } else if (pos.getLearningMethod() == 1) {
                    wi1 = plasticity * p * oj * (oi - zij0);
                } else if (pos.getLearningMethod() == 2) {
                    wi1 = plasticity * p * (oj - zij0) * oi;
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
