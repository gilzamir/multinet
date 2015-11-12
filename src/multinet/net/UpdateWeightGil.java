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
     
        //final double maxEnergy = 25000;
        //double energy = net.lambda;
        net.numberOfUpdates = 0.0;
       // double rho = energy/maxEnergy;
        
        for (int i = 0; i < net.getSize(); i++) {
            Neuron pos = neurons.get(i);
            double oi = pos.getFunction().exec(pos.getState()) * net.outputGain;
 
            double h = pos.getShift() + pos.getAmp();
            double l = pos.getShift() - pos.getAmp();
            
            double p = 0.0;
            
            if (oi < l) {
                p = 1.0 - oi/l;
            } else if (oi > h) {
                p = 1.0 - oi/h;
            }

            for (int j = 0; j < net.getSize(); j++) {
                Neuron pre = neurons.get(j);
                double wi = net.getWeight(j, i);
               
                double oj = pre.getFunction().exec(pre.getState()) * net.outputGain;

                if (wi == 0) {
                    p = 0;
                } else if (wi  < 0) {
                    p = -p;
                }
                
                double plasticity = net.getPlasticity(j, i);
                //System.out.println(plasticity);
                double wi1 = 0;
                
                if (pos.getLearningMethod() == 0) {
                    wi1 = plasticity * p * Math.abs(oj);
                }
                
                if (wi1 > 0){  
                    net.numberOfUpdates++;
                }
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
