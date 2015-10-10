package multinet.net.genetic;

import java.util.BitSet;


public class GeneLayout32 implements GeneLayout {

    private final int GENE_SIZE = 32;
    
    private GeneReader reader;
    private GeneWriter writer;
    
    public static final int MAX_INT24 = (int)Math.pow(2, 24);
    public static final int MAX_INT32 = Integer.MAX_VALUE;
    
    public GeneLayout32() {
        this.reader = new GeneReader() {
            @Override
            public BitSet read(BitSet set, int idx) {
                int i = idx * GENE_SIZE;
                int f = i + GENE_SIZE;
                return set.get(i, f);
            }
        };
        
        this.writer = new GeneWriter() {
            @Override
            public void writer(BitSet set, int idx, boolean[] v) {
                int i = idx * GENE_SIZE;
                int f = i + GENE_SIZE;
                int p = 0;
                for (int j = i; j < f; j++) {
                    set.set(j, v[p]);
                    p++;
                }
            }
        };
    }
    
    @Override
    public int getGeneSize() {
        return GENE_SIZE;
    }

    @Override
    public GeneReader getReader() {
        return reader;
    }

    @Override
    public GeneWriter getWriter() {
        return writer;
    }
    
    @Override
    public int getMaxInteger() {
        return MAX_INT32;
    }
}
