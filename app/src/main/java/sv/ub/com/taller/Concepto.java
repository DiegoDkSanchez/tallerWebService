package sv.ub.com.taller;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DK-Ragnar on 2/9/2018.
 */

class Concepto {

    int id;
    String name;
    double value;
    @SerializedName("categoryId")
    int id_categoria;
    @SerializedName("category")
    Categoria categoria;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
}
