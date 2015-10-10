package multinet.net.genetic;

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
