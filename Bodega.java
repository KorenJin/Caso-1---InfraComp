package Clases;

import java.util.LinkedList;
import java.util.Queue;

class Bodega {
    private final int capacidadMaxima;
    private final Queue<Producto> productos;

    public Bodega(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.productos = new LinkedList<>();
    }

    public synchronized boolean depositarProducto(Producto producto) {
        if (productos.size() < capacidadMaxima) {
            productos.add(producto);
            System.out.println("Bodega: Producto depositado - " + producto.getId());
            return true; // Producto depositado con éxito
        } else {
            System.out.println("Bodega: Bodega llena, esperando para depositar...");
            return false; // Bodega llena, no se puede depositar
        }
    }

    public synchronized Producto tomarProducto() {
        while (productos.isEmpty()) {
            try {
                System.out.println("Bodega: Bodega vacía, esperando para tomar...");
                wait(); // Espera pasivamente si la bodega está vacía
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Producto producto = productos.poll();
        System.out.println("Bodega: Producto tomado - " + producto.getId());
        return producto;
    }
}
