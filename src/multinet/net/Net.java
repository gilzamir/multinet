package multinet.net;

import multinet.core.IComponent;
import multinet.core.Synapse;

public interface Net extends IComponent {
    int getSize();
    Synapse getSynapse(int source, int target);
    Neuron getNeuron(int id);
    void proccess();
    void update();
}
