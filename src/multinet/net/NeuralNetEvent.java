package multinet.net;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public interface NeuralNetEvent {
	String getMessage();
	NeuralNet getSource();
	Object[]  getData();
}
