package multinet.net.genetic;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public interface  GeneLayout {
    int getGeneSize();
    GeneReader getReader();
    GeneWriter getWriter();
    long getMaxInteger();
}
