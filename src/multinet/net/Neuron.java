package multinet.net;

import java.io.Serializable;

public class Neuron implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2028785805051744192L;
    private NeuralNet net;
    private int ID;
    private Function function;
    private NeuronType type;
    private double state = 0.0;
    private double sensorValue = 0.0;
    private double bias = 0.0;
    private double timeConstant = 1.0;
    private final double rungeKuttaStep = 0.001;
    private double gain = 1.0;
    private double amp = 2.0;
    private double shift = -1.0;
    private double plastiticy = 0.0;
    private boolean plasticityEnabled = true;
    private double learningRate;
    private int learningMethod;

    public Neuron() {
        learningRate = 0;
    }

    public void setLearningMethod(int learningMethod) {
        this.learningMethod = learningMethod;
    }

    public int getLearningMethod() {
        return learningMethod;
    }
    
    
    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setPlasticityEnabled(boolean plasticityAcvate) {
        this.plasticityEnabled = plasticityAcvate;
    }

    public boolean isPlasticityEnabled() {
        return plasticityEnabled;
    }

    public double getAmp() {
        return amp;
    }

    public void setAmp(double amp) {
        this.amp = amp;
    }

    public double getShift() {
        return shift;
    }

    public void setShift(double shift) {
        this.shift = shift;
    }

    public double getPlastiticy() {
        return plastiticy;
    }

    public void setPlastiticy(double plastiticy) {
        this.plastiticy = plastiticy;
    }

    public double getState() {
        return state;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double getBias() {
        return bias;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
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
            if (type == NeuronType.NORMAL || type == NeuronType.MODULATORY) {
                //4th Order Runge-Kutta ====================================================
                double k1, k2, k3, k4;

                double pState = state;

                k1 = computeInterneuronInput(this.getID());
                state = pState + k1 * rungeKuttaStep / 2.0;

                k2 = computeInterneuronInput(getID());
                state = pState + k2 * rungeKuttaStep / 2.0;

                k3 = computeInterneuronInput(getID());
                state = pState + k3 * rungeKuttaStep;

                k4 = computeInterneuronInput(getID());
                state = pState + (k1 + 2 * k2 + 2 * k3 + k4) * rungeKuttaStep / 6.0;
                if (state > 100) {
                    state = 100;
                } else if (state < -100) {
                    state = -100;
                }
            } else {
                double sum = 0.0;
                int q = 0;
                for (int i = 0; i < net.getSize(); i++) {
                    Neuron ne = net.getNeuron(i);
                    if (ne.getType() != NeuronType.MODULATORY) {
                        Function func = ne.getFunction();
                        double w = net.getWeight(ne.getID(), getID());
                        if (w != 0.0) {
                            sum += w * func.exec(ne.getState() * ne.getGain());
                            q++;
                        }
                    }
                }
                if (q > 0) {
                    sum = sum / q;
                }
                if (sum > 100) {
                    sum = 100;
                } else if (sum < -100) {
                    sum = -100;
                }
                setState(sum);
            }
        } else {
            double sensoryStimulus = sensorValue;
            state = sensoryStimulus;
        }

        return state;
    }

    private double computeInterneuronInput(int id) {
        int nNeurons = net.getSize();
        float s = 0;

        for (int i = 0; i < nNeurons; i++) {
            Neuron ne =  net.getNeuron(i);
            if (ne.getType() != NeuronType.MODULATORY) {
                Function func = ne.getFunction();
                s += func.exec((ne.getState() * ne.getGain())) * net.getWeight(i, id); //inputs of neuron id == column id
            }
        }

        double r = (net.restInput - (getState() - getBias()) + s) / (getTimeConstant());
		//double r = (- getState() + s)/(getTimeConstant());

		//if(brainOutputLogic == 1)
        //if(neurons[id]->getType() == ntEfferent)
        //r = ((-neurons[id]->state + s)/(neurons[id]->time));
        //else
        //#endif
        //r = ((stimulus - neurons[id]->state + s)/(neurons[id]->time));
        return r;
    }

    public void setSensorValue(double value) {
        this.sensorValue = value;
    }

    public NeuralNet getNet() {
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

    public void setFunction(Function f) {
        this.function = f;
    }

    public Function getFunction() {
        return function;
    }

    public void prepare(NeuralNet net) {
        this.net = net;
        if (this.function == null) {
            this.setFunction(new Tanh());
        }

        if (getType() != NeuronType.NORMAL && getType() != NeuronType.MODULATORY) {
            this.timeConstant = 1.0;
        }
    }

    public void setInput(double value) {
        this.setSensorValue(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Type: ").append(this.type).append(", ");
        sb.append("TimeConstant: ").append(this.timeConstant).append(", ");
        sb.append("Bias: ").append(this.bias).append(", ");
        sb.append("Amp: ").append(this.amp).append(", ");
        sb.append("Shift: ").append(this.shift).append(" ");
        return sb.toString();
    }
}
