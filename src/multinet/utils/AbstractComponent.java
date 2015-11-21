package multinet.utils;

import java.util.HashMap;
import java.util.Map;

public class AbstractComponent {
    protected Map<String, Double> pDouble;
    protected Map<String, Integer> pInteger;
    protected Map<String, Float> pFloat;
    protected Map<String, Long> pLong;
    
    public AbstractComponent() {
        pDouble = new HashMap<>();
        pInteger = new HashMap<>();
        pFloat = new HashMap<>();
        pLong = new HashMap<>();
    }
    

    public Double getDouble(String name) {
        return pDouble.get(name);
    }

    public void setDouble(String name, double v) {
        this.pDouble.put(name, v);
    }
    
    public Float getFloat(String name) {
        return pFloat.get(name);
    }

    public void setFloat(String name, float v) {
        this.pFloat.put(name, v);
    }
    
    public Integer getInteger(String name) {
        return pInteger.get(name);
    }

    public void setInteger(String name, int v) {
        this.pInteger.put(name, v);
    }
    
    public Long getLong(String name) {
        return pLong.get(name);
    }

    public void setLong(String name, long v) {
        this.pLong.put(name, v);
    }
}
