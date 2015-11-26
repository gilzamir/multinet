package multinet.core;

public class Synapse extends AbstractComponent {
    private double intensity;
    private int source, target;
    
    public Synapse(int source, int target, double intensity){
        super();
        this.source = source;
        this.target = target;
        this.intensity = intensity;
    }
    
    public Synapse(int source, int target) {
        this(source, target, 0);
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    } 
}
