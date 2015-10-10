package multinet.net.genetic;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class RandomicEncodingGenerator implements EncodingGenerator {

    @Override
    public Encoding generate(Encoding input) {
        for (int i = 0; i < input.getNumberOfGenes(); i++) {
            boolean gene[] = new boolean[input.getGeneLayout().getGeneSize()];
            Encoding.randomBoolean(gene);
            input.setGene(i, gene);
        }
        
        return input;
    }
}
