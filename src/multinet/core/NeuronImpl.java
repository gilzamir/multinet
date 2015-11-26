package multinet.core;

import multinet.net.Net;
import multinet.net.Neuron;

public abstract class NeuronImpl extends AbstractComponent {
    public abstract double step(Neuron target, Net net, double currentState);
    public abstract double getPotetial(Neuron n);
    public abstract double setInput(Neuron ne, double inv);
    public abstract double getOutput(Neuron ne);
}
