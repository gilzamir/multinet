
package multinet.net.test;

import multinet.net.NeuralNet;
import multinet.net.NeuronType;

public class NeuralNetTest {
    public static void main(String args[]){
        NeuralNet net = new NeuralNet(null);
        int i1 = net.addCell(NeuronType.INPUT);
        int i2 = net.addCell(NeuronType.INPUT);
        int n1 = net.addCell(NeuronType.NORMAL);
        int o1 = net.addCell(NeuronType.OUTPUT);
        
        net.prepare();
       
        net.setWeight(i1, n1, 0.5f);
        net.setWeight(i2, n1, 0.5f);
        net.setWeight(n1, o1, 1.0f);
        
        net.setInput(new double[]{1.0f, 1.0f});
        net.process();
        System.out.println(net.getNeuron(o1).getState());
        
    }
}
