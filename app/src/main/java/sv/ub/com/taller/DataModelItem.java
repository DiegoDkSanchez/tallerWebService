package sv.ub.com.taller;

/**
 * Created by DK-Ragnar on 2/9/2018.
 */

public class DataModelItem {

    int id;
    String name;
    double value;
    String categoria;

    public DataModelItem(int id, String name, double value, String categoria) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public String getCategoria() {
        return categoria;
    }
}
