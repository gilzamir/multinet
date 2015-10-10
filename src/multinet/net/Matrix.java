package multinet.net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Matrix implements Serializable {

    private final Map<Cell, Double> values;
    private int max = 0;
    
    public Matrix() {
        values = new HashMap<Cell, Double>();
    }

    public int countLines() {
        return max+1;
    }

    public int countCells() {
        return values.size();
    }
    
    Set<Cell> getCells() {
        return values.keySet();
    }

    public void setCell(Cell cell, double value) {
        
        if (cell.getX() > max) {
            max = cell.getX();
        }
        
        if (cell.getY() > max) {
            max = cell.getY();
        }
        
        values.put(cell, value);
    }

    public void setCell(int i, int j, double value) {
        setCell(new Cell(i, j), value);
    }

    public double getCell(int i, int j) {
        return getCell(i, j, 0.0);
    }

    public double getCell(int i, int j, double defaultValue) {
        return getCell(new Cell(i, j), defaultValue);
    }

    public double getCell(Cell cell, double defaultValue) {
        Double d = values.get(cell);
        if (d != null) {
            return d;
        } else {
            return defaultValue;
        }
    }

    public double getCell(Cell cell) {
        return getCell(cell, 0.0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < countLines(); i++) {
            for (int j = 0; j < countLines(); j++) {
                sb.append(getCell(i, j)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /*
    public static void main(String args[]){
        Matrix m = new Matrix();
        m.setCell(0, 0, 1);
        m.setCell(0, 1, 0);
        m.setCell(1, 0, 0);
        m.setCell(1, 1, 1);
        System.out.println(m);
    }*/
}


