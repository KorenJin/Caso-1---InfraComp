package Clases;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nBienvenido a la planta de producción\n");

        int N = obtenerValorDesdeEntrada("Ingrese el número de productores (N): ");
        int M = obtenerValorDesdeEntrada("Ingrese el número de repartidores (M): ");
        int TAM = obtenerValorDesdeEntrada("Ingrese la capacidad de la bodega (TAM): ");
        int totalProductos = obtenerValorDesdeEntrada("Ingrese el número total de productos a producir: ");

        Bodega bodega = new Bodega(TAM);
        Buffer buffer = new Buffer(); // Crea una instancia de Buffer
        Despachador despachador = new Despachador(buffer, M);

        Thread[] productores = new Thread[N];
        Thread[] repartidores = new Thread[M];

        for (int i = 0; i < N; i++) {
            productores[i] = new Thread(new Productor(bodega, despachador, totalProductos / N));
            productores[i].start();
        }

        for (int i = 0; i < M; i++) {
            repartidores[i] = new Thread(new Repartidor(despachador, buffer));
            repartidores[i].start();
        }

        for (Thread productor : productores) {
            try {
                productor.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        despachador.todosProductosDespachados();

        for (Thread repartidor : repartidores) {
            repartidor.interrupt(); // Interrumpe a los repartidores para que terminen
        }

        System.out.println("\nTodos los threads han terminado su ejecución. El programa ha finalizado.");
    }

    private static int obtenerValorDesdeEntrada(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensaje);

        while (!scanner.hasNextInt()) {
            // Si el usuario no ingresa un entero, muestra un mensaje de error y vuelve a pedir entrada.
            System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
            System.out.print(mensaje);
            scanner.next(); // Limpia la entrada no válida
        }

        int valor = scanner.nextInt();
        return valor;
    }
}
