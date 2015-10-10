package multinet.net.genetic;


import java.io.Serializable;
import java.util.BitSet;
import java.util.Random;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class Encoding implements Serializable {
    private BitSet code;
    private GeneLayout geneLayout;
    private int numberOfGenes;
    
    public Encoding(int numberOfGenes, GeneLayout geneLayout){
        this.numberOfGenes = numberOfGenes;
        this.geneLayout = geneLayout;
        code = new BitSet(numberOfGenes * geneLayout.getGeneSize());
    }

    public int getNumberOfGenes() {
        return numberOfGenes;
    }

    public GeneLayout getGeneLayout() {
        return geneLayout;
    }

    public void set(int idx, boolean v) {
         if (idx >= getNumberOfGenes() * geneLayout.getGeneSize()) {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
        code.set(idx, v);
    }
    
    public boolean get(int idx) {
        if (idx >= getNumberOfGenes() * geneLayout.getGeneSize()) {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
        return code.get(idx);
    }
    
    public BitSet getGene(int idx) {
        if (idx >= getNumberOfGenes()) {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
        return geneLayout.getReader().read(code, idx);
    }
    
    public void setGene(int idx, boolean[] value) {
        geneLayout.getWriter().writer(code, idx, value);
    }
    
    public void setGene(int idx, int value) {
        String str = Integer.toBinaryString(value);
        boolean[] gene = new boolean[geneLayout.getGeneSize()];
        int p  = str.length()-1;
        for (int i = 0; i < gene.length-1 && p >= 0; i++) {
            gene[i] = (str.charAt(p) == '0' ? false : true);
            p--;
        }
        setGene(idx, gene);
    }
    
    public int getAsInteger(int idx){
        BitSet v = getGene(idx);
        long value = 0;
        
        for (int i = 0; i < getGeneLayout().getGeneSize(); i++) {
            value += Math.pow(2, i) * (v.get(i) ? 1 : 0 );
        }
        return (int)value;
    }

    public float getAsFloat(int idx, float min, float max) {
        return (getAsInteger(idx)/(float)Integer.MAX_VALUE) * (max-min) + min;
    }
    
    public float getAsFloat(int idx, int resolution, float min, float max) {
        return (getAsInteger(idx)/(float)resolution) * (max-min) + min;
    }
    
    public static int toInt(BitSet s, int a, int b) {
        long v = 0;
        int p = 0;
        for (int i = a; i < b; i++) {
            v += Math.pow(2, p) * (s.get(i) ? 1 : 0);    
            p++;
        }
        
        return  (int)v;
    }
    
    public static void randomBoolean(boolean b[]) {
        Random rnd = new Random();
        for (int i = 0; i < b.length; i++) {
            b[i] = rnd.nextBoolean();
        }
    }
    
    public static void setBoolean(boolean b[], int i, int f, boolean value) {
        for (int p = i; p < f; p++) {
            b[p] = value;
        }
    }
}
