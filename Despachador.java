package Clases;

class Despachador {
    private final Buffer buffer;
    private final int numRepartidores;
    private volatile boolean todosProductosDespachados = false;
    private int productosEntregados = 0;
    private int productosPorRepartidor;

    public Despachador(Buffer buffer, int numRepartidores) {
        this.buffer = buffer;
        this.numRepartidores = numRepartidores;
        this.productosPorRepartidor = 0;
    }

    public synchronized Producto entregarProducto() {
        Producto producto = buffer.tomarProducto();

        // Notifica a los productores que pueden depositar más productos si estaban esperando
        synchronized (buffer) {
            buffer.notifyAll();
        }

        // Realiza un seguimiento del número total de productos entregados
        productosEntregados++;
        if (productosEntregados == numRepartidores * productosPorRepartidor) {
            todosProductosDespachados = true;
        }

        return producto;
    }

    public synchronized boolean todosProductosDespachados() {
        return todosProductosDespachados;
    }

    public void setProductosPorRepartidor(int productosPorRepartidor) {
        this.productosPorRepartidor = productosPorRepartidor;
    }
}
