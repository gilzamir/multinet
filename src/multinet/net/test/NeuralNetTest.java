
package multinet.net.test;

import multinet.net.NeuralNet;
import multinet.net.Neuron;
import multinet.net.NeuronType;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class NeuralNetTest {
    public static void main(String args[]){
        NeuralNet net = new NeuralNet(null);
        Neuron i1 = net.createNeuron();
        Neuron i2 = net.createNeuron();
        Neuron n1 = net.createNeuron();
        Neuron o1 = net.createNeuron();
        
        i1.setType(NeuronType.INPUT);
        i2.setType(NeuronType.INPUT);
        
        n1.setType(NeuronType.NORMAL);
        o1.setType(NeuronType.OUTPUT);
        
        net.createSynapse(i1.getID(), n1.getID(), 0.5f);
        net.createSynapse(i2.getID(), n1.getID(), 0.5f);
        net.createSynapse(n1.getID(), o1.getID(), 1.0f);
        
        net.proccess();
        System.out.println(o1.getImplementation().getOutput(o1));
    }
}
