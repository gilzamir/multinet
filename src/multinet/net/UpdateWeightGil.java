package multinet.net;

import java.util.ArrayList;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class UpdateWeightGil implements UpdateWeightStrategy {

    @Override
    public void update(final NeuralNet net) {
        ArrayList<Neuron> neurons = net.getNeurons();
        int ur = 0;
        for (int i = 0; i < net.getSize(); i++) {
            Neuron pos = neurons.get(i);
            double oi = pos.getFunction().exec(pos.getState());
            double Hl = pos.getShift() - pos.getAmp();
            double Hu = pos.getShift() + pos.getAmp();
            double p;                     
            if (oi > Hl && oi < Hu) {
                ur++;
                p = 0;
            } else {
                p = 1.0f - oi/net.lambda;
            }
            for (int j = 0; j < net.getSize(); j++) {
                 Neuron pre = neurons.get(j);
                 if (net.isPlasticityEnabled()) {
                        double wi = net.getWeight(j, i);
                        if (wi == 0) {
                            continue;
                        }
                        double oj = pre.getFunction().exec(pre.getState());
                        
                        double plasticity = net.getPlasticity(j, i);

                        double wi1 = plasticity * (1.0-oj) * p * net.getLearningRate();
                       
                        wi = wi + wi1;

                        net.setWeight(j, i, wi);                        
                    }
                
            }
        }
        net.numberOfUpdates = ur;
        final double rate = ur/(double)(net.getSize()*net.getSize());
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
