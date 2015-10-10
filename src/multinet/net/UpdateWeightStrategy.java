package multinet.net;

import java.io.Serializable;

public interface UpdateWeightStrategy extends Serializable {
    void update(NeuralNet net);
}
