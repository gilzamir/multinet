package multinet.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import multinet.net.genetic.Evaluable;

/**
 * Continues Time Recurrent Neural Network.
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class NeuralNet implements Serializable, Evaluable {

    private static final long serialVersionUID = 2453110203237905144L;
    protected Matrix weight;    
    protected Matrix plasticity;
    protected ArrayList<Neuron> neurons;
    protected int inputs[];
    protected int outputs[];
    protected int normalNeurons[];
    protected double learningRate;
    protected boolean plasticityEnabled = true;
    protected int inputSize = 0;
    protected int outputSize = 0;
    protected int numberOfNormalNeurons = 0;
    protected int size;
    private NeuralNetListener listener;
    private UpdateWeightStrategy updateWeightStrategy;
    public double restInput = 0.0, weightGain=1.0, lambda = 0.000000001f, outputGain=0.0f;
    private Map<String, Double> parameter;
    
    public double numberOfUpdates = 0;
 
    private float score;
    
    public NeuralNet(UpdateWeightStrategy uwStrategy) {
        this.updateWeightStrategy = uwStrategy;
        this.neurons = new ArrayList<>();
        parameter = new HashMap<>();
        this.reset();
    }
    
    public NeuralNet createParameter(String name) {
        this.parameter.put(name, 0.0);
        return this;
    }
    
    public Double getDouble(String name) {
        return this.parameter.get(name);
    }
    
    public NeuralNet setDouble(String name, double v) {
        this.parameter.put(name, v);
        return this;
    }
    
    public void setListener(NeuralNetListener listener) {
        this.listener = listener;
    }

    public NeuralNetListener getListener() {
        return listener;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public void setOutputSize(int outputSize) {
        this.outputSize = outputSize;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setWeight(double w[][]) {
        //this.weight = w;
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i].length; j++) {
                this.weight.setCell(new Cell(i, j), w[i][j]);
            }
        }
    }

    public boolean isPlasticityEnabled() {
        return plasticityEnabled;
    }

    public void setPlasticityEnabled(boolean plasticityEnabled) {
        this.plasticityEnabled = plasticityEnabled;
    }

    final public void reset() {
        this.neurons = new ArrayList<>();
        inputSize = 0;
        outputSize = 0;
        numberOfNormalNeurons = 0;
        size = 0;
    }

    public void setNeurons(ArrayList<Neuron> neurons) {
        this.neurons = neurons;
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public int getSize() {
        return size;
    }

    public int addCell(NeuronType type) {
        Neuron ne = new Neuron();
        ne.setType(type);
        ne.setID(this.neurons.size());
        neurons.add(ne);

        if (type == NeuronType.INPUT) {
            inputSize++;
        } else if (type == NeuronType.OUTPUT) {
            outputSize++;
        } else if (type == NeuronType.NORMAL || type == NeuronType.MODULATORY) {
            this.numberOfNormalNeurons++;
        }

        return ne.getID();
    }

    public void setInput(int ID, double value) {
        this.neurons.get(ID).setInput(value);
    }

    public void setInput(double values[]) {
        for (int i = 0; i < inputs.length; i++) {
            this.neurons.get(inputs[i]).setSensorValue(values[i]);
        }
    }

    public void process() {
        for (int i = 0; i < inputs.length; i++) {
            this.neurons.get(inputs[i]).process();
        }

        for (int i = 0; i < normalNeurons.length; i++) {
            this.neurons.get(normalNeurons[i]).process();
        }

        for (int i = 0; i < outputs.length; i++) {
            this.neurons.get(outputs[i]).process();
        }
        
        if (isPlasticityEnabled()) {
            updateWeights();
        }
    }

    public double getOutput(int idx) {
        Neuron ne = neurons.get(outputs[idx]);
        return ne.getFunction().exec(ne.getState()) * outputGain;
    }

    public Map<Integer, Double> getOutputMap() {
        HashMap<Integer, Double> map = new HashMap<>();
        for (int i = 0; i < outputs.length; i++) {
            Neuron ne = this.getNeuron(outputs[i]);
            map.put(ne.getID(), ne.getState());
        }
        return map;
    }

    public double[] getOutput() {
        double out[] = new double[outputs.length];
        for (int i = 0; i < outputs.length; i++) {
            Neuron ne = this.getNeuron(outputs[i]);
            out[i] = ne.getFunction().exec(ne.getState()) * outputGain;
        }
        return out;
    }

    public void prepare() {
        this.prepare(false, 0.0f, 0.0f);
    }

    
    public void prepare(double min, double max) {
        prepare(true, min, max);
    }
    
    private void prepare(boolean randomize, double wmin, double wmax) {
        inputs = new int[this.inputSize];
        outputs = new int[this.outputSize];
        normalNeurons = new int[this.numberOfNormalNeurons];

        int inputsIdx = 0;
        int outputsIdx = 0;
        int normalIdx = 0;

        for (int i = 0; i < neurons.size(); i++) {
            Neuron ne = neurons.get(i);
            if (ne.getType() == NeuronType.INPUT) {
                inputs[inputsIdx++] = i;
            } else if (ne.getType() == NeuronType.OUTPUT) {
                outputs[outputsIdx++] = i;
            } else if (ne.getType() == NeuronType.NORMAL || ne.getType() == NeuronType.MODULATORY) {
                normalNeurons[normalIdx++] = i;
            }
        }

        size = inputSize + numberOfNormalNeurons + outputSize;

        if (weight == null) {
            //this.weight = new double[size][size];
            this.weight = new Matrix();
            this.plasticity = new Matrix();
        }

        if (randomize) {
            randomizeMatrix(weight, wmin, wmax);
            setMatrix(plasticity, 0);
        }

        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).prepare(this);
        }
        updateWeightStrategy.init(this);
    }

    public double getWeight(int i, int j) {
        return this.weight.getCell(i, j);
    }

    public void setWeight(int i, int j, double value) {
        this.weight.setCell(i, j, value);
    }
    
    public double getPlasticity(int i, int j) {
        return this.plasticity.getCell(i, j);
    }
    
    public void setPlasticity(int i, int j, double v) {
        this.plasticity.setCell(i,j, v);
    }

    public void randomizeMatrix(Matrix m, double min, double max) {
        for (int i = 0; i < weight.countLines(); i++) {
            for (int j = 0; j < weight.countLines(); j++) {
                m.setCell(i, j, Math.random() * (max-min) + min);
            }
        }
    }
    
    public void setMatrix(Matrix m, double value) {
        for (int i = 0; i < weight.countLines(); i++) {
            for (int j = 0; j < weight.countLines(); j++) {
                m.setCell(i, j, value);
            }
        }
    }

    public int getInputSize() {
        return inputSize;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public Neuron getNeuron(int ID) {
        return this.neurons.get(ID);
    }


    public void updateWeights() {
        if (this.updateWeightStrategy != null && isPlasticityEnabled()) {
            this.updateWeightStrategy.update(this);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Network{\n");
        sb.append("Weight [ ");
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                //sb.append(this.weight[i][j]).append(" ");
                sb.append(this.weight.getCell(i, j)).append(" ");
            }
            sb.append("\n");
        }
        sb.append(" ] \n");
        sb.append("LearningRate [").append(this.learningRate).append("]\n");
        sb.append("SensoryPerturbation [").append(this.restInput).append(" ]\n");
        sb.append("OutputGain [").append(this.outputGain).append(" ]\n");
        Set<String> parameters = parameter.keySet();
        for(String n: parameters) {
            sb.append(n).append(": ").append(getDouble(n)).append("; ");
        }
        sb.append("\n");
        sb.append("Neurons [\n");
        for (int i = 0; i < neurons.size(); i++) {
            sb.append(neurons.get(i)).append("\n");
        }
        sb.append("]\n");
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public float getValue() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int[] getInputs() {
        return inputs;
    }

    public int[] getOutputs() {
        return outputs;
    }

    public int[] getNormalNeurons() {
        return normalNeurons;
    }
}
