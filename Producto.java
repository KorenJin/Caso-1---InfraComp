package Clases;


class Producto {
    private static int contador = 0;
    private final int id;

    public Producto() {
        this.id = ++contador;
    }

    public int getId() {
        return id;
    }
}
