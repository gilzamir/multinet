package multinet.net;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import multinet.core.NeuronImpl;
import multinet.core.Synapse;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class UpdateWeightGil implements UpdateWeightStrategy {

    public final static int OUTPUTS = 3;
    
    @Override
    public void update(final NeuralNet net) {
        if (net.getUpdateStrategy() == null) {
            return;
        }

        int numberOfUpdates = 0;
        Collection<Synapse> synapses = net.getSynapses().values();
        
        for (Synapse syn : synapses) {
            Neuron pos = net.getNeuron(syn.getTarget());
            NeuronImpl ni = pos.getImplementation();
            double outputgain = net.getDouble("outputgain");
            double oi = ni.getFunction("neuronfunction").exec(pos.getState()) * outputgain;
 
            double shift = pos.getDouble("shift");
            double amp = pos.getDouble("amp");
            
            double h = shift + amp;
            double l = shift - amp;
            
            double p = 0.0;
            
            if (oi < l) {
                p = 1.0 - oi/l;
            } else if (oi > h) {
                p = 1.0 - oi/h;
            }

            Neuron pre = net.getNeuron(syn.getSource());
                
            double wi = syn.getIntensity();
            
            double oj = pre.getImplementation().getFunction("neuronfunction").
                    exec(pre.getState()) * outputgain;

            if (wi == 0) {
                p = 0;
            } else if (wi  < 0) {
                p = -p;
            }
                
            double plasticity = syn.getDouble("plasticity");
            //System.out.println(plasticity);
            double wi1 = 0;
                
            if (pos.getInteger("method") == 0) {
                wi1 = plasticity * p * Math.abs(oj);
            }
                
            if (wi1 > 0){  
                numberOfUpdates++;
            }
            wi = wi + wi1;

            syn.setIntensity(wi);
        }
        
        final double rate = numberOfUpdates/(double)(net.getSize()*net.getSize());
        net.setDouble("updaterate", rate);
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
        
        if (net != null) {
            net.getListener().handleUpdateWeight(ev);
        }
    }

    @Override
    public void init(NeuralNet net) {
       
    } 
}
