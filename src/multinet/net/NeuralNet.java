package multinet.net;

import multinet.core.Cell;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import multinet.net.genetic.Evaluable;
import multinet.core.AbstractComponent;
import multinet.core.Synapse;

/**
 * Continues Time Recurrent Neural Network.
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class NeuralNet extends AbstractComponent implements Serializable, Evaluable, Net {

    private int size;
    private Map<Integer, Neuron> neurons;
    private Map<Cell, Synapse> synapses;
    private Stack<Integer> pool;
    private float score;
    private UpdateWeightStrategy updateStrategy;
    private NeuralNetListener listener;
    
    public NeuralNet(UpdateWeightStrategy updateStr) {
        super();
        neurons = new HashMap<>();
        synapses = new HashMap<>();
        pool = new Stack<>();
        this.updateStrategy = updateStr;
        setDouble("inputrest", 0.0);
        setDouble("weightgain", 1.0);
        setDouble("dopamine", 1.0);
        this.listener = new NeuralNetListener() {

            @Override
            public void handleUpdateWeight(NeuralNetEvent evt) {
            }
        };
    }

    public NeuralNetListener getListener() {
        return listener;
    }

    public void setListener(NeuralNetListener listener) {
        this.listener = listener;
    }

    public UpdateWeightStrategy getUpdateStrategy() {
        return updateStrategy;
    }
    
    public NeuralNet createParameter(String name) {
        setDouble(name, 0.0);
        return this;
    }

    public Map<Integer, Neuron> getNeurons() {
        return neurons;
    }

    public Map<Cell, Synapse> getSynapses() {
        return synapses;
    }

    @Override
    public void proccess() {
        Set<Integer> ID = neurons.keySet();
        for (Integer id : ID) {
           neurons.get(id).process();
        }
    }

    @Override
    public void update() {
        if (updateStrategy != null) {
            updateStrategy.update(this);
        }
    }
    
    public Synapse  createSynapse(int source, int target, double weight) {
        if (neurons.containsKey(source) && neurons.containsKey(target)) {
            Cell position = new Cell(source, target);

            Neuron ni = neurons.get(source);
            Neuron nj = neurons.get(target);
            ni.getOutcomeSynapse().add(position);
            nj.getIncomeSynapse().add(position);
            
            Synapse syn = new Synapse(source, target, weight);
            synapses.put(position, syn);
            return syn;
        } else {
            return null;
        }
    }
    
    public Neuron createNeuron() {
        int id = generateID();
        if (id >= 0) {
            Neuron ne  = new Neuron();
            ne.setID(id);
            ne.setNet(this);
            neurons.put(id, ne);
            size++;
            return ne;
        }
        return null;
    }
    
    public boolean removeSynapse(Cell pos) {
        return synapses.remove(pos) != null;
    }
    
    public boolean removeNeuron(int id) {
        Neuron ne = neurons.remove(id);
        if (ne != null) {
            releaseID(ne.getID());
            size--;
            for (Cell syncell : ne.getIncomeSynapse()) {
                int i = syncell.getX();
                Neuron ni = getNeuron(i);
                if (ni != null) {
                    ni.getOutcomeSynapse().remove(syncell);
                }
                removeSynapse(syncell);
            }
            
            for (Cell syncell : ne.getOutcomeSynapse()) {
                int j = syncell.getY();
                Neuron nj = getNeuron(j);
                if (nj != null) {
                    nj.getIncomeSynapse().remove(syncell);
                }
                removeSynapse(syncell);
            }
            
            return true;
        }
        return false;
    }
    
    public void setScore(float score) {
        this.score = score;
    }
    
    @Override
    public float getValue() {
        return score;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Synapse getSynapse(int source, int target) {
        return synapses.get(new Cell(source, target));
    }

    @Override
    public Neuron getNeuron(int id) {
        return neurons.get(id);
    }
    
    private int nextID = 0;
    public int generateID() {
        if (nextID < Integer.MAX_VALUE) {
            int id = nextID;
            nextID++;
            return id;
        } else {
            if (!pool.isEmpty()) {
                return pool.pop();
            } else {
                return -1;
            }
        }
    }
    
    public void releaseID(int id) {
        pool.push(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Network{\n");
        sb.append("Weight [ ");
        double w = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                
                Synapse syn = getSynapse(i, j);
                if (syn != null) {
                    w = syn.getIntensity();
                } else {
                    w = 0;
                }
                sb.append(w).append(" ");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
}
