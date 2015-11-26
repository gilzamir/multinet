package multinet.core;

import java.util.HashMap;
import java.util.Map;

public class AbstractComponent implements IComponent {
    protected Map<String, Double> pDouble;
    protected Map<String, Integer> pInteger;
    protected Map<String, Float> pFloat;
    protected Map<String, Long> pLong;
    protected Map<String, Function> pFunction;
    protected Map<String, Object> pObject;
    
    public AbstractComponent() {
        pDouble = new HashMap<>();
        pInteger = new HashMap<>();
        pFloat = new HashMap<>();
        pLong = new HashMap<>();
        pFunction = new HashMap<>();
        pObject = new HashMap<>();
    }
    
    @Override
    public Double getDouble(String name) {
        return pDouble.get(name);
    }

    @Override
    public void setDouble(String name, Double v) {
        this.pDouble.put(name, v);
    }
    
    @Override
    public Float getFloat(String name) {
        return pFloat.get(name);
    }

    @Override
    public void setFloat(String name, Float v) {
        this.pFloat.put(name, v);
    }
    
    public Integer getInteger(String name) {
        return pInteger.get(name);
    }

    public void setInteger(String name, Integer v) {
        this.pInteger.put(name, v);
    }
    
    @Override
    public Long getLong(String name) {
        return pLong.get(name);
    }

    @Override
    public void setLong(String name, Long v) {
        this.pLong.put(name, v);
    }
    
    public Function getFunction(String name) {
        return this.pFunction.get(name);
    }
    
    public void setFunction(String name, Function f) {
        this.pFunction.put(name, f);
    }
    
    public Object getObject(String name) {
        return pObject.get(name);
    }
    
    public void setObject(String name, Object value) {
        this.pObject.put(name, value);
    }
}
