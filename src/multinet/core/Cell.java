package multinet.core;

import java.io.Serializable;

/**
 * 
 * @author Gilzamir Gomes (gilzamir@gmail.com)
 */
public class Cell implements Serializable {
    private int x;
    private int y;
    
    public Cell() {
        this(0,0);
    }
    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setX(int nx) {
        x = nx;
    }
    
    public void setY(int ny) {
        y = ny;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cell other = (Cell) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  (new StringBuilder()).append("(").append(x).append(", ").
                append(y).append(")").toString();
    }
}
