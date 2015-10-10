package multinet.net.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public abstract class Genetic {
   private Genome organism[];
   private float mutationProbability = 0.001f;
   private float crossoverProbability = 0.6f;
   
   public Genetic(int numberOfOrganisms) {
       organism = new Genome[numberOfOrganisms];
   }
   
   public void setupPopulation() {
       for (int i = 0; i < organism.length; i++) {
           organism[i] = newGenome();
       }
   }
   
   /**
    * This method evalutes current population based in performance 
    * calculaed by <code>evuator</code>.
    * @param evaluator Genome performance evaluator.
    */
   public float evaluate(Evaluator evaluator) {
       float fitness = 0.0f;
       for (Genome genome : organism) {
           evaluator.evaluate(genome);
           fitness += genome.getFitness();
       }       
       if (organism.length > 0) {
            return fitness/organism.length;
       } else {
           return 0.0f;
       }
   }

   /**
    * This method is to select better genome and generate your 
    * offspring with mutation.
    */
   public void next() {
       Arrays.sort(organism, new Comparator<Genome>(){
           @Override
           public int compare(Genome o1, Genome o2) {
               if (o1.getFitness() < o2.getFitness()) {
                   return 1;
               } else if (o1.getFitness() > o2.getFitness()) {
                   return -1;
               } else {
                   return 0;
               }
           }
       
       });
       
       float max = organism[0].getFitness();
       float min = organism[organism.length-1].getFitness();
       float delta = max - min;
       
       ArrayList<Integer> selected = new ArrayList<>(this.getNumberOfOrganisms());
       
       for (int i = 0; i < organism.length; i++) {
           float prob =  (organism[i].getFitness() - min)/delta;
           if (Math.random() <= prob) {
               selected.add(i);
           }
       }
       
       
       int p = organism.length-1;
       SELECTION_OUT:
       for (int i = 0; i < selected.size(); i++) {
           for (int j = i+1; j < selected.size(); j++) {
               if (i != j && Math.random() < crossoverProbability) {
                   int a = selected.get(i);
                   int b = selected.get(j);
                   Genome A = this.organism[a];
                   Genome B = this.organism[b];
                   int point = (int)(A.getChromossome(0).getNumberOfGenes() * Math.random());
                   organism[p] = crossover(A, B, point);
                   mutation(organism[p]);
                   p--;
                   if (p < 0) {
                       break SELECTION_OUT;
                   }
                   organism[p] = crossover(B, A, point);
                   mutation(organism[p]);
                   p--;
                   if (p < 0) {
                       break SELECTION_OUT;
                   }
               }
           }
       }
   }
   
    public Genome[] getOrganism() {
        return organism;
    }
   
   public int getNumberOfOrganisms() {
       return organism.length;
   }

    public void setMutationProbability(float mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public float getMutationProbability() {
        return mutationProbability;
    } 

    public float getCrossoverProbability() {
        return crossoverProbability;
    }

    public void setCrossoverProbability(float crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }
    
    public void mutation(Genome ge) {
        for (int i = 0; i < ge.size(); i++) {
            Encoding e = ge.getChromossome(i);
            for (int j = 0; j < e.getNumberOfGenes(); j++) {
               if (Math.random() <= mutationProbability) {
                   e.set(j, !e.get(j));
               }
            }
        }
    }
    
    public Genome crossover(Genome g1, Genome g2, int point) {
        Genome g = newGenome();
        for (int i = 0; i < g1.size(); i++){
            Encoding c1 = g1.getChromossome(i);
            Encoding c2 = g2.getChromossome(i);
            Encoding r = crossover(c1, c2, point);
            g.setChromossome(i, r);
        }
        return g;
    }
    
    private Encoding crossover(Encoding e1, Encoding e2, int point) {
        Encoding r = new Encoding(e1.getNumberOfGenes(), e1.getGeneLayout());
        int n = r.getNumberOfGenes() * r.getGeneLayout().getGeneSize();
        int i;
        for (i = 0; i < point; i++) {
            r.set(i, e1.get(i));
        }
        for (; i < n; i++) {
            r.set(i, e2.get(i));
        }
        return r;
    }
    
    protected abstract Genome newGenome();
}

