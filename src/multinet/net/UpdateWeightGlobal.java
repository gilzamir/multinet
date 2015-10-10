package multinet.net;

import java.util.HashSet;
import java.util.Set;

public class UpdateWeightGlobal implements UpdateWeightStrategy {

    @Override
    public void update(final NeuralNet net) {

        Set<Integer> listOfUpdated = new HashSet<>();

        
        double mi[] = new double[net.getSize()];
        for (int i = 0; i < net.getSize(); i++) {
            double sum = 0.0;
            for (int j = 0; j < net.getSize(); j++) {
                Neuron pre = net.getNeuron(j);
                if (pre.getType() == NeuronType.MODULATORY) {
                    sum += net.getWeight(j, i) * pre.getFunction().exec(pre.getState() * 0.5);
                }
            }
            mi[i] = sum;
        }

        for (int j = 0; j < net.getSize(); j++) {
            for (int i = 0; i < net.getSize(); i++) {
                if (net.getWeight(j, i) != 0.0) {
                    Neuron preSyn = net.getNeuron(j);
                    Neuron posSyn = net.getNeuron(i);

                    if (posSyn.isPlasticityEnabled()) {
                        if (mi[i] != 0.0) {
                            listOfUpdated.add(j);
                        }

                        double oj = preSyn.getFunction().exec(preSyn.getState() * 0.5);
                        double oi = posSyn.getFunction().exec(posSyn.getState() * 0.5);
                        //this.weight[i][j] +=  posSyn.getFunction().exec(mi[i]*0.5) * this.getLearningRate() * (A * oj * oi + B * oj + C * oi + D);
                        double wi1 = posSyn.getFunction().exec(mi[i] * 0.5) * net.getLearningRate() * (net.A * oj * oi + net.B * oj + net.C * oi + net.D);
                        double wi = net.getWeight(i, i);// .getCell(j, i);
                        net.setWeight(j, i, wi + wi1);
                       
                    }
                }
            }
        }

        net.numberOfUpdates += listOfUpdated.size();
        
        if (net.getListener() != null) {
            final StringBuilder sb = new StringBuilder();
            sb.append("updating rate: ").append((double) listOfUpdated.size() / net.getSize()).append("\n");
            for (Integer i : listOfUpdated) {
                Neuron ne =  net.getNeurons().get(i);
                sb.append(i).append("[").append(ne.getPlastiticy()).append("]").append("\n");
                sb.append("Potential: ").append(ne.getFunction().exec(ne.getState())).append("\n");
            }

            net.getListener().handleUpdateWeight(new NeuralNetEvent() {

                @Override
                public NeuralNet getSource() {
                    return net;
                }

                @Override
                public String getMessage() {

                    return sb.toString();
                }

                @Override
                public Object[] getData() {
                    return new Object[]{};
                }
            });
        }
    }

}
