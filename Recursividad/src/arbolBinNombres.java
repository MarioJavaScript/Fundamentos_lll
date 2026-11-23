public class arbolBinNombres {

    // Clase del nodo (faltaba en tu código)
    static class Nodo {
        int valor;
        Nodo izquierdo;
        Nodo derecho;

        Nodo(int valor) {
            this.valor = valor;
        }
    }

    // La raíz vive dentro del árbol, no se pasa como parámetro
    private Nodo raiz;

    // Método público: antes estaba vacío
    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }
        if (valor < nodo.valor) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, valor);
        } else {
            nodo.derecho = insertarRecursivo(nodo.derecho, valor);
        }
        return nodo;
    }

    public boolean buscar(int valor) {
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) return false;
        if (valor == nodo.valor) return true;
        return valor < nodo.valor
                ? buscarRecursivo(nodo.izquierdo, valor)
                : buscarRecursivo(nodo.derecho, valor);
    }

    public void inorden() {
        inorden(raiz);
        System.out.println();
    }

    private void inorden(Nodo nodo) {
        if (nodo == null) return;
        inorden(nodo.izquierdo);
        System.out.print(nodo.valor + " ");
        inorden(nodo.derecho);
    }

    public void preorden() {
        preorden(raiz);
        System.out.println();
    }

    private void preorden(Nodo nodo) {
        if (nodo == null) return;
        System.out.print(nodo.valor + " ");
        preorden(nodo.izquierdo);
        preorden(nodo.derecho);
    }

    public void postorden() {
        postorden(raiz);
        System.out.println();
    }

    private void postorden(Nodo nodo) {
        if (nodo == null) return;
        postorden(nodo.izquierdo);
        postorden(nodo.derecho);
        System.out.print(nodo.valor + " ");
    }

    public static void main(String[] args) {
        arbolBinNombres arbol = new arbolBinNombres();

        int[] valores = {50, 30, 70, 20, 40, 60, 80};
        for (int v : valores) {
            arbol.insertar(v);
        }

        System.out.print("Inorden:   ");
        arbol.inorden();
        System.out.print("Preorden:  ");
        arbol.preorden();
        System.out.print("Postorden: ");
        arbol.postorden();

        System.out.println("¿Existe 40? " + arbol.buscar(40));
        System.out.println("¿Existe 99? " + arbol.buscar(99));
    }
}