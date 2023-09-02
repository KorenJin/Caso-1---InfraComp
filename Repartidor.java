package Clases;

import java.util.Random;

class Repartidor implements Runnable {
    private final Despachador despachador;
    private final Buffer buffer;

    public Repartidor(Despachador despachador, Buffer buffer) {
        this.despachador = despachador;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!despachador.todosProductosDespachados()) {
            Producto producto = despachador.entregarProducto();
            System.out.println("Repartidor: Producto entregado - " + producto.getId());

            // Simula el tiempo de entrega entre 3 y 10 segundos
            try {
                Thread.sleep(3000 + new Random().nextInt(8000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Repartidor: Terminó su trabajo.");
    }
}
