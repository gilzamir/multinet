package multinet.net;

import java.io.Serializable;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public interface UpdateWeightStrategy extends Serializable {
    void update(NeuralNet net);
}
