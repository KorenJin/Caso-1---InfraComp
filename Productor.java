package Clases;

import java.util.Random;

class Productor implements Runnable {
    private final Bodega bodega;
    private final int cantidadProductos;
    private final Despachador despachador;

    public Productor(Bodega bodega, Despachador despachador, int cantidadProductos) {
        this.bodega = bodega;
        this.cantidadProductos = cantidadProductos;
        this.despachador = despachador;
    }

    @Override
    public void run() {
        for (int i = 0; i < cantidadProductos; i++) {
            Producto producto = new Producto(); // Crea un nuevo producto
            System.out.println("Productor: Producto producido - " + producto.getId());

            // Intenta depositar el producto en la bodega
            while (!bodega.depositarProducto(producto)) {
                // Espera pasivamente si la bodega está llena
                synchronized (bodega) {
                    try {
                        System.out.println("Productor: Bodega llena, esperando para depositar...");
                        bodega.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Dormir un poco antes de producir el siguiente producto
            try {
                Thread.sleep(new Random().nextInt(1000)); // Simula la producción de un producto
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Productor: Terminó su trabajo.");
        despachador.setProductosPorRepartidor(cantidadProductos);
    }
}
