package multinet.net.test;

import java.util.Scanner;
import multinet.core.Synapse;
import multinet.net.NeuralNet;
import multinet.net.Neuron;
import multinet.net.UpdateWeightGil;

public class TestHomeostaticProperty {

    public static void main(String args[]) {
        NeuralNet net = new NeuralNet(new UpdateWeightGil());
        Neuron n = net.createNeuron();
        final double min  = 0.49;
        final double max = 0.5;
        boolean stoped = false;
        
        n.setTimeConstant(10);
        n.getImplementation().setDouble("bias", 0.0);
        n.getImplementation().setDouble("inputgain", 1.0);
        n.getImplementation().setDouble("outputgain", 1.0);        
        n.getImplementation().setDouble("inputweight", 1.0);
        n.getImplementation().setDouble("sensorweight", 1.0);
        n.getImplementation().setDouble("weightgain", 20.0);
        Synapse synapse = net.createSynapse(n.getID(), n.getID(), 0.2);
        
        
        while (!stoped) {
            double state = n.process();
            double pot = n.getImplementation().getPotetial(n);
            double p = 0.0;
            
            if (pot < min) {
                p = 1 - pot/min;
            } else if (pot > max) {
                p = 1 - pot/max;
            }
            
            synapse.setIntensity(synapse.getIntensity() + 0.8 * p * (1-pot));
             
            Console.WriteLine("plast({0})", p); 
            Console.WriteLine("state({0}) : pot({1}) : w({2})", state, pot, synapse.getIntensity()); 
            Console.WriteLine("Press q to exit; c to supply 2.0v to neuron; or ENTER to supply 0.0v to neuron..."); 
            switch(Console.ReadKey()) {
                case 'q':
                    stoped = true;
                    break;
                case 'c':
                    n.setInput(2.0);
                    break;
            }
        }
    }
}

class Console {
    public static void WriteLine(String pattern,  Object ... values) {
        for (int i = 0; i < values.length; i++) {
            pattern = pattern.replaceAll("\\{" + i + "\\}", values[i]+"");
        }
        System.out.println(pattern);
    }
    
    public static int ReadKey(){ 
        try {
            return System.in.read();
        } catch(Exception e) {
            return 0;
        }
    }
}

