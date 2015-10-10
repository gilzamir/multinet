package multinet.net.genetic;

public interface  GeneLayout {
    int getGeneSize();
    GeneReader getReader();
    GeneWriter getWriter();
    int getMaxInteger();
}
