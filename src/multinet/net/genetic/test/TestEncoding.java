package multinet.net.genetic.test;

import java.util.BitSet;
import multinet.net.genetic.Encoding;
import multinet.net.genetic.GeneLayout32;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class TestEncoding {
    public static void main(String args[]) {
        Encoding encoding = new Encoding(10, new GeneLayout32());
        
        for (int i = 0; i < 10; i++) {
            encoding.setGene(i,i);
        }
        
        for (int i = 0; i < 10; i++) {
            BitSet gene = encoding.getGene(i);
            System.out.println(encoding.getAsInteger(i));
            System.out.println(encoding.getAsFloat(i, 100, -20, 20));
            System.out.println(encoding.getAsFloat(i, -20, 20));
            System.out.println(Encoding.toInt(gene, 0, 7));
            System.out.println("-----------------------");
            
        }
        
    }
}
