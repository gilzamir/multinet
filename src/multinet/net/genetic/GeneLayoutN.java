package multinet.net.genetic;

import java.util.BitSet;

/**
 *
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class GeneLayoutN implements GeneLayout {

    private int size;

    public GeneLayoutN(int n) {
        size = n;
    }
    
    
    
    @Override
    public int getGeneSize() {
        return size;
    }

    @Override
    public GeneReader getReader() {
        return new GeneReader() {

            @Override
            public BitSet read(BitSet set, int idx) {
                final int n = getGeneSize();
                int begin = idx * n;
                int end = begin + n;
                BitSet r = set.get(begin, end);
                return r;
            };
        };
    }

    @Override
    public GeneWriter getWriter() {
        return new GeneWriter() {
            @Override
            public void writer(BitSet set, int idx, boolean[] v) {
                final int n = getGeneSize();
                int begin = idx * n;
                int end = begin + (n-1);
                int p = 0;
                for (int i = begin; i <= end; i++) {
                    set.set(i, v[p++]);
                }
            };
        };
    }

    @Override
    public long getMaxInteger() {
        return Encoding.MAX_INT;
    }
}
