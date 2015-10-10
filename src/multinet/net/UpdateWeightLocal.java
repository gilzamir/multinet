package multinet.net;

import java.util.ArrayList;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class UpdateWeightLocal implements UpdateWeightStrategy {

    @Override
    public void update(final NeuralNet net) {
        ArrayList<Neuron> neurons = net.getNeurons();
        int ur = 0;
        for (int i = 0; i < net.getSize(); i++) {
            Neuron pos = neurons.get(i);
            for (int j = 0; j < net.getSize(); j++) {
                Neuron pre = neurons.get(j);
                 if (net.isPlasticityEnabled()) {
                        double p = 0.0;
                        double wi = net.getWeight(j, i);// .getCell(j, i);
                        if (wi == 0) {
                            continue;
                        }
                        double oj = pre.getFunction().exec(pre.getState() * 0.5);
                        double oi = pos.getFunction().exec(pos.getState() * 0.5);
                        
                        if (oi < pos.getAmp()) {
                            p = (pos.getAmp() - oi);
                        } else if (oi > pos.getShift()){
                            p = (oi - pos.getShift());
                        }

                       //double wi1 =  pos.getLearningRate() * p  * net.getLearningRate()  * (net.A * oj * oi + net.B * oj + net.C * oi + net.D);
                        double wi1 = 0.0;
                        double zij0 = (wi+1.0)/2.0;
                        if (zij0 < 0.0) {
                            zij0 = 0.0;
                        } else if (zij0 > 1.0) {
                            zij0 = 1.0;
                        }
                        
                        int method = pos.getLearningMethod();
                        if (method == 0) {
                          wi1 = pos.getLearningRate() * p * net.getLearningRate() * oj * oi;
                        } else if (method == 1) {
                          wi1 = pos.getLearningRate() * p * net.getLearningRate() * oi * (oj - zij0);
                        } else if (method == 2) {
                            wi1 = pos.getLearningRate() * p * net.getLearningRate() * oj * (oi - zij0);
                        } 
                        
                        
                        if (wi1 != 0) {
                            ur++;
                        }
                        net.setWeight(j, i, wi + wi1);
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

}
