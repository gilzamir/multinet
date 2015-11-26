package multinet.core;

public interface IComponent {
    Double getDouble(String name);
    void setDouble(String name, Double v);
    Float getFloat(String name);
    void setFloat(String name, Float v);
    Long getLong(String name);
    void setLong(String name, Long v);
}
