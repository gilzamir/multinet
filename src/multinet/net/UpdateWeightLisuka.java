package multinet.net;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class UpdateWeightLisuka implements UpdateWeightStrategy {

    @Override
    public void update(final NeuralNet net) {
        /*
        ArrayList<Neuron> neurons = net.getNeurons();
        int ur = 0;
        for (int i = 0; i < net.getSize(); i++) {
            Neuron pos = neurons.get(i);
            for (int j = 0; j < net.getSize(); j++) {
                Neuron pre = neurons.get(j);
                 if (net.isPlasticityEnabled()) {
                        double p;
                        double wi = net.getWeight(j, i);
                        if (wi == 0) {
                            continue;
                        }
                        double oj = pre.getFunction().exec(pre.getState());
                        double oi = pos.getFunction().exec(pos.getState());
                        
                        double Hl = pos.getDouble("amp");
                        double plasticity = net.getPlasticity(j, i);
                        
                        if (oj >= Hl) {
                            p = 0;
                        } else {
                            p = 1.0f - oj/Hl;
                        }

                        double wi1 = plasticity * (1.0-oi) * p * net.getLearningRate();
                       
                        ur += p;
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
        }*/
    }

    @Override
    public void init(NeuralNet net) {
    }
}
