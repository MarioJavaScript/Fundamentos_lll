import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class GrafoArrays {
    private String[] vertices;
    private int numVertices;
    private int[][] matrizAdyacencia;

    // Arcos recorridos por DFS() y BFS(). Son null hasta que se ejecute el método correspondiente.
    private ArrayList<String> arcosDFS = null;
    private ArrayList<String> arcosBFS = null;

    public GrafoArrays(String origen) {
        creaTablaVertices(origen);
        matrizAdyacencia = new int[numVertices][numVertices];
        populateMatrizAdyacencia(origen);
    }

    // Quita espacios, la llave inicial "{" y los punto y coma ";" (formato DOT)
    private static String limpiaLinea(String linea) {
        String arista = linea.trim();
        if (arista.startsWith("{")) {
            arista = arista.substring(1);
        }
        return arista.replace(";", "").trim();
    }

    private void creaTablaVertices(String origen) {
        String[] lineas = origen.split("\\R");
        vertices = new String[lineas.length * 2];
        numVertices = 0;
        for (String linea : lineas) {
            String arista = limpiaLinea(linea);
            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                agregarVertice(extremos[0].trim());
                agregarVertice(extremos[1].trim());
            }
        }
    }

    private void populateMatrizAdyacencia(String origen) {
        String[] lineas = origen.split("\\R");
        for (String linea : lineas) {
            String arista = limpiaLinea(linea);
            int peso = 1; // Peso por defecto
            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                int cierre = arista.indexOf(']', etiqueta);
                String atributos = cierre > etiqueta
                        ? arista.substring(etiqueta + 1, cierre)
                        : arista.substring(etiqueta + 1);
                try {
                    // Quita comillas y demás símbolos: label="3" -> 3
                    peso = Integer.parseInt(atributos.replaceAll("[^0-9]", ""));
                } catch (NumberFormatException e) {
                    System.out.println("Peso inválido en la línea: " + linea);
                }
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                agregarArista(extremos[0].trim(), extremos[1].trim(), peso);
            }
        }
    }

    private void agregarVertice(String vertice) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertice)) {
                return;
            }
        }
        vertices[numVertices] = vertice;
        numVertices++;
    }

    private int obtenerIndiceVertice(String vertice) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertice)) {
                return i;
            }
        }
        return -1;
    }

    private void agregarArista(String vertice1, String vertice2, int peso) {
        int indice1 = obtenerIndiceVertice(vertice1);
        int indice2 = obtenerIndiceVertice(vertice2);
        if (indice1 >= 0 && indice2 >= 0) {
            matrizAdyacencia[indice1][indice2] = peso;
            matrizAdyacencia[indice2][indice1] = peso; // Para grafos no dirigidos
        }
    }

    public void display() {
        System.out.println("Vértices del grafo (índice - nombre):");
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("[%2d] %-12s", i, vertices[i]);
            if ((i + 1) % 4 == 0 || i == numVertices - 1) {
                System.out.println();
            }
        }
        displayMatrizAdyacencia();
    }

    // Filas y columnas se identifican por el índice del vértice (ver lista de vértices)
    private void displayMatrizAdyacencia() {
        System.out.println("Matriz de Adyacencia:");
        System.out.print("     ");
        for (int j = 0; j < numVertices; j++) {
            System.out.printf("%4d", j);
        }
        System.out.println();
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%4d ", i);
            for (int j = 0; j < numVertices; j++) {
                System.out.printf("%4d", matrizAdyacencia[i][j]);
            }
            System.out.println();
        }
    }

    // Recorrido en profundidad. Guarda en arcosDFS el arco (padre--hijo) por el que se descubre cada vértice.
    public void DFS(String verticeInicial, String verticeFinal) {
        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(verticeFinal);
        if (indiceInicial == -1 || indiceFinal == -1) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        arcosDFS = new ArrayList<>(); // Cada recorrido reemplaza el contenido anterior
        boolean[] visitado = new boolean[numVertices];
        // Cada elemento de la pila guarda {vértice, vértice padre}
        Stack<int[]> pila = new Stack<>();
        pila.push(new int[]{indiceInicial, -1});
        System.out.println("Recorrido DFS desde " + verticeInicial + " hasta " + verticeFinal + ":");
        while (!pila.isEmpty()) {
            int[] actual = pila.pop();
            int vertice = actual[0];
            if (visitado[vertice]) {
                continue;
            }
            visitado[vertice] = true;
            System.out.print(vertices[vertice] + " ");
            if (actual[1] != -1) {
                arcosDFS.add(vertices[actual[1]] + "--" + vertices[vertice]);
            }
            if (vertice == indiceFinal) {
                System.out.println("\nSe ha alcanzado el vértice final.");
                return;
            }
            for (int i = 0; i < numVertices; i++) {
                if (matrizAdyacencia[vertice][i] != 0 && !visitado[i]) {
                    pila.push(new int[]{i, vertice});
                }
            }
        }
        System.out.println("\nEl vértice final no es alcanzable desde el inicial.");
    }

    // Recorrido a lo ancho. Guarda en arcosBFS el arco (padre--hijo) por el que se descubre cada vértice.
    public void BFS(String verticeInicial, String verticeFinal) {
        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(verticeFinal);
        if (indiceInicial == -1 || indiceFinal == -1) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        arcosBFS = new ArrayList<>(); // Cada recorrido reemplaza el contenido anterior
        boolean[] visitado = new boolean[numVertices];
        Queue<Integer> cola = new LinkedList<>();
        visitado[indiceInicial] = true;
        cola.add(indiceInicial);
        System.out.println("Recorrido BFS desde " + verticeInicial + " hasta " + verticeFinal + ":");
        while (!cola.isEmpty()) {
            int vertice = cola.poll();
            System.out.print(vertices[vertice] + " ");
            if (vertice == indiceFinal) {
                System.out.println("\nSe ha alcanzado el vértice final.");
                return;
            }
            for (int i = 0; i < numVertices; i++) {
                if (matrizAdyacencia[vertice][i] != 0 && !visitado[i]) {
                    visitado[i] = true;
                    arcosBFS.add(vertices[vertice] + "--" + vertices[i]);
                    cola.add(i);
                }
            }
        }
        System.out.println("\nEl vértice final no es alcanzable desde el inicial.");
    }

    // Número de arcos (aristas no dirigidas): cada arista se cuenta una sola vez (i < j)
    private int contarArcos() {
        int total = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] != 0) {
                    total++;
                }
            }
        }
        return total;
    }

    // Celdas no nulas de la matriz. Cada arista ocupa dos celdas (i,j) y (j,i).
    private int contarArcosOcupados() {
        int total = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] != 0) {
                    total++;
                }
            }
        }
        return total;
    }

    private void imprimeArcos(ArrayList<String> arcos) {
        if (arcos == null) {
            System.out.println("  (atributo nulo: aún no se ha ejecutado el recorrido)");
            return;
        }
        if (arcos.isEmpty()) {
            System.out.println("  (sin arcos)");
            return;
        }
        for (String arco : arcos) {
            System.out.println("  " + arco);
        }
        System.out.println("  Total: " + arcos.size());
    }

    public void despliegaArcos() {
        System.out.println("\n===== Resumen de arcos =====");
        System.out.println("Número total de vértices: " + numVertices);
        System.out.println("Número total de arcos: " + contarArcos());
        System.out.println("Número total de arcos ocupados: " + contarArcosOcupados()
                + " (celdas no nulas de la matriz " + numVertices + "x" + numVertices + ")");

        System.out.println("\nArcos en arcosDFS:");
        imprimeArcos(arcosDFS);
        System.out.println("\nArcos en arcosBFS:");
        imprimeArcos(arcosBFS);

        System.out.println();
        if (arcosDFS != null && arcosBFS != null) {
            int nDFS = arcosDFS.size();
            int nBFS = arcosBFS.size();
            if (nDFS < nBFS) {
                System.out.println("arcosDFS tiene MENOS elementos (" + nDFS + ") que arcosBFS (" + nBFS + ").");
            } else if (nBFS < nDFS) {
                System.out.println("arcosBFS tiene MENOS elementos (" + nBFS + ") que arcosDFS (" + nDFS + ").");
            } else {
                System.out.println("arcosDFS y arcosBFS tienen la misma cantidad de elementos (" + nDFS + ").");
            }
        } else {
            System.out.println("No se pueden comparar: ejecuta DFS() y BFS() antes de desplegar los arcos.");
        }
    }
}

public class grafos {
    // Uso: java grafos <archivo> <vértice inicial> <vértice final>
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java grafos <archivo> <vértice inicial> <vértice final>");
            return;
        }
        String texto = cargaArchivo(args[0]);
        if (texto.isEmpty()) {
            return;
        }
        System.out.println("Contenido del archivo:\n" + texto);
        GrafoArrays grafo = new GrafoArrays(texto);
        grafo.display();
        System.out.println();
        grafo.DFS(args[1], args[2]);
        System.out.println();
        grafo.BFS(args[1], args[2]);
        grafo.despliegaArcos();
    }

    public static String cargaArchivo(String ruta) {
        String contenido = "";
        try {
            java.io.File archivo = new java.io.File(ruta);
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNextLine()) {
                contenido += scanner.nextLine() + "\n";
            }
            scanner.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + ruta);
        }
        return contenido;
    }
}
