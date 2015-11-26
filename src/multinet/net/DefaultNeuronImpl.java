package multinet.net;

import multinet.core.Function;
import multinet.core.NeuronImpl;
import multinet.core.Sigmoid;
import multinet.core.Sin;
import multinet.core.Synapse;

public class DefaultNeuronImpl extends NeuronImpl {
    private Function sigmoid = new Sigmoid();
    private Function sin = new Sin();
    
    public DefaultNeuronImpl() {
        super();
        setDouble("bias", 0.0);
        setDouble("inputgain", 1.0);
        setDouble("outputgain", 1.0);
        setDouble("inputweight", 1.0);
        setDouble("sensorweight", 1.0);
        setDouble("weightgain", 1.0);
        setDouble("stateshift", -5.0);
        setFunction("neuronfunction", sigmoid);
        setFunction("weightfunction", sin);
    }
    
    
    @Override
    public double step(Neuron target, Net net, double currentState) {
        
        int nNeurons = net.getSize();
        double s = 0;
        double weightGain = net.getDouble("weightgain");
        double dopamine = net.getDouble("dopamine");
        
        for (int i = 0; i < nNeurons; i++) {
            Neuron ne =  net.getNeuron(i);
            if (ne.getType() != NeuronType.MODULATORY) {
                if (Math.random() <= dopamine) {
                    Synapse syn = net.getSynapse(i, target.getID());
                    if (syn != null) {
                        s += sigmoid.exec(ne.getState()) 
                            * weightGain * dopamine * sin.exec(syn.getIntensity()); //inputs of neuron id == column id
                    }
                }
            }
        }

        return (-(currentState - net.getDouble("inputrest")) + s) / (target.getTimeConstant());
    }

    @Override
    public double getPotetial(Neuron n) {
        return sigmoid.exec(n.getState()+getDouble("bias"));
    }

    @Override
    public double setInput(Neuron ne, double inv) {
        ne.setSensorValue(inv * getDouble("inputgain"));
        return ne.getSensorValue();
    }

    @Override
    public double getOutput(Neuron ne) {
        return getPotetial(ne) * getDouble("outputgain");
    }
}
