package multinet.net.genetic;

import java.io.Serializable;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public abstract class Genome implements Serializable {
    
    protected Encoding chromossome[];
    protected EncodingGenerator generator[];
    protected float fitness;
    
    public Genome(int size) {
        chromossome = new Encoding[size];
        generator = new EncodingGenerator[size];
        for (int i = 0; i < size; i++) {
            generator[i] = new RandomicEncodingGenerator();
        }
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public void setGenerator(int i, EncodingGenerator  enc) {
        this.generator[i] = enc;
    }
    
    public Encoding getChromossome(int i){
        return chromossome[i];
    }
    
    public void setChromossome(int i, Encoding e) {
        this.chromossome[i] = e;
    }
    
    public int size() {
        return chromossome.length;
    }
             
    public void generate(int i){
        this.chromossome[i] = generator[i].generate(this.chromossome[i]);
    }
    
    abstract public Evaluable decode();
}
