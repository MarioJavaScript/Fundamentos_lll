import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.LinkedList;

public class Rover {
    public static void main(String[] args) {
        String cadena = args.length > 0 ? args[0] : "C:\\Users\\thexl\\OneDrive\\Desktop\\fundamentos3\\Fundamentos_lll\\fundamentos_3_2026\\Rover\\path1.txt";

        ArrayList<String> lineas = leerArchivo(cadena);
        Queue<String> queue = addToQueue(lineas);

        // Copia de la cola para armar la pila ANTES de vaciar la original.
        Deque<String> stack = addToStack(new LinkedList<>(queue));
        queue = stackToQueue(stack); // Reconstruye la cola original para el recorrido normal.
        travelQueue(queue); // Recorrido normal

        System.out.print("Recorrido normal:  ");
        travelQueue(queue);

        Queue<String> invertida = stackToQueue(stack);
        System.out.print("Recorrido inverso: ");
        travelQueue(invertida);
    }

    public static Deque<String> addToStack(Queue<String> queue) {
        Deque<String> deque = new ArrayDeque<>();
        while (!queue.isEmpty()) {
            String linea = queue.poll();
            if (linea != null) {
                deque.add(linea);
            }
        }
        return deque;
    }

    // Toma una pila y devuelve una cola con el orden invertido.
    public static Queue<String> stackToQueue(Deque<String> stack) {
        Queue<String> queue = new LinkedList<>();
        while (!stack.isEmpty()) {
            String linea = stack.pollLast();
            if (linea != null) {
                queue.add(linea);
            }
        }
        return queue;
    }

    public static void travelQueue(Queue<String> queue) {
        while (!queue.isEmpty()) {
            String linea = queue.poll();
            if (linea != null) {
                System.out.print(linea);
            }
        }
        System.out.println();
    }

    public static Queue<String> addToQueue(ArrayList<String> lineas) {
        Queue<String> queue = new LinkedList<>();
        for (String linea : lineas) {
            queue.add(linea);
        }
        return queue;
    }

    public static ArrayList<String> leerArchivo(String nombreArchivo) {
        ArrayList<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return lineas;
    }
}