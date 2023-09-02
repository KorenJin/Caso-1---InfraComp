package Clases;

import java.util.LinkedList;
import java.util.Queue;

class Buffer {
    private final Queue<Producto> productosEnBuffer;
    private boolean despachadorEsperando = false;

    public Buffer() {
        this.productosEnBuffer = new LinkedList<>();
    }

    public synchronized void colocarProducto(Producto producto) {
        productosEnBuffer.add(producto);
        System.out.println("Buffer: Producto colocado en el buffer - " + producto.getId());
        if (despachadorEsperando) {
            notify(); // Notifica al despachador si estaba esperando
        }
    }

    public synchronized Producto tomarProducto() {
        while (productosEnBuffer.isEmpty()) {
            try {
                System.out.println("Buffer: Buffer vacío, esperando para tomar...");
                despachadorEsperando = true;
                wait(); // Espera pasivamente si el buffer está vacío
                despachadorEsperando = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Producto producto = productosEnBuffer.poll();
        System.out.println("Buffer: Producto tomado del buffer - " + producto.getId());
        return producto;
    }
}
