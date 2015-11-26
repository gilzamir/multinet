package multinet.net;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import multinet.core.AbstractComponent;
import multinet.core.Cell;
import multinet.core.NeuronImpl;
import multinet.core.NumericalMethod;
import multinet.core.NumericalProducer;
import multinet.core.RungeKuttaMethod;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class Neuron extends AbstractComponent implements Serializable, NumericalProducer {

    /**
     *
     */
    private static final long serialVersionUID = -2028785805051744192L;
    private Net net;
    private int ID;
    private NeuronType type;
    private double state = 0.0;
    private double sensorValue = 0.0;
    private double timeConstant = 1.0;
    private NeuronImpl implementation;
    private NumericalMethod numericalMethod;
    private List<Cell> incomeSynapse;
    private List<Cell> outcomeSynapse;
    
    public Neuron() {
        implementation = new DefaultNeuronImpl();
        numericalMethod = new RungeKuttaMethod();
        incomeSynapse = new LinkedList<>();
        outcomeSynapse = new LinkedList<>();
    }

    public void setNet(Net net) {
        this.net = net;
    }

    public List<Cell> getIncomeSynapse() {
        return incomeSynapse;
    }

    public List<Cell> getOutcomeSynapse() {
        return outcomeSynapse;
    }

    public NeuronImpl getImplementation() {
        return implementation;
    }

    public void setImplementation(NeuronImpl implementation) {
        this.implementation = implementation;
    }
   
    public double getState() {
        return state;
    }

    public void setTimeConstant(double timeConstant) {
        this.timeConstant = timeConstant;
    }

    public double getTimeConstant() {
        return timeConstant;
    }

    public void setState(double state) {
        this.state = state;
    }

    public double process() {
        if (type != NeuronType.INPUT) {
            state = numericalMethod.nextState(state, this);
        }
        return state;
    }
     
    @Override
    public double produce(double currentState) {
        return implementation.step(this, net, currentState);
    }

    public void setSensorValue(double value) {
        this.sensorValue = value;
    }

    public double getSensorValue() {
        return sensorValue;
    }

    public Net getNet() {
        return net;
    }

    public void setType(NeuronType type) {
        this.type = type;
    }

    public NeuronType getType() {
        return type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public void prepare(Net net) {
        this.net = net;

        if (getType() != NeuronType.NORMAL && getType() != NeuronType.MODULATORY) {
            this.timeConstant = 1.0;
        }
    }

    public void setInput(double value) {
        implementation.setInput(this, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Type: ").append(this.type).append(", ");
        sb.append("TimeConstant: ").append(this.timeConstant).append(", ");
        
        Set<String> keys = pDouble.keySet();
        
        for (String k : keys) {
            double v = pDouble.get(k);
            sb.append(k).append(": ").append(v).append(" ");
        }
        
        
        return sb.toString();
    }
}
