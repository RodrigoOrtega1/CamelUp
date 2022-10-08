package Extras;
import java.util.Iterator;

public class SimpleLinkedList<T>{
    
    private class Nodo{
        /* Elemento a almacenar */
        public T elemento;

        /* Apuntador al nodo siguiente */
        public Nodo siguiente;

        /* Crea un nodo a partir de un elemento <i>elemento</i> de tipo <i>T</i> */
        public Nodo(T elemento){
            this.elemento = elemento;
        }
    }

    /**
     * Iterador de la lista simplemente ligada
     */
    private class IteradorListaSimple implements Iterator<T> {
        /**
         * 
         */

        private Nodo iteradorLista;
        
        public IteradorListaSimple(Nodo cabeza) {
            iteradorLista = new Nodo(null);
            iteradorLista.siguiente = cabeza;
        }

        public boolean hasNext(){
            return iteradorLista.siguiente != null;
        }

        public T next(){
            iteradorLista = iteradorLista.siguiente;
            return iteradorLista.elemento;
        }
    }

    /** Nodo inicial de la lista */
    private Nodo cabeza;

    /** Nodo ultimos de la lista */
    private Nodo cola;

    /** Cantidad de elementos en la lista */
    private int longitud;
    
    /**
     * Inserta un nuevo elemento <i>e</i> en la posicion <i>i</i>
     * 
     * @param i la posicion donde agregar el elemento
     * @param e el elemento que vamos a agregar
     * @throws IndexOutOfBoundsException si el indice esta fuera de rango.
     */
    public void add(int i, T e) throws IndexOutOfBoundsException{
        if (i < 0 || i > longitud) {
            throw new IndexOutOfBoundsException("El indice no esta en el rango de 0 y el tamanio de la lista. ");
        }

        Nodo nuevo = new Nodo(e);

        // Caso para la lista vacia
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
            longitud ++;
            return;
        }

        // Agregar en la posicion 0.
        if(i == 0){
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
            longitud ++;
            return;
        }
        // agrega al final
        else if(i == longitud){
            cola.siguiente = nuevo;
            cola = nuevo;
            
            longitud ++;
            return;
        }

        // agrega en otra posicion

        // Creando un iterador en la cabeza. 
        Nodo iterador = cabeza;
        
        for (int j = 0; j < i-1; j++) {
            iterador = iterador.siguiente;
        }
        nuevo.siguiente = iterador.siguiente;
        iterador.siguiente = nuevo;
        longitud ++;


    }

    /**
     * Elimina el elemento en la posicion <i>i</i>
     * 
     * @param i la posicion del elemento a eliminar
     * @return el elemento eliminado.
     * @throws IndexOutOfBoundsException si el indice esta fuera de rango.
     */
    public T remove(int i) throws IndexOutOfBoundsException{
        if (i < 0 || i >= longitud) {
            throw new IndexOutOfBoundsException("El indice no esta en el rango de 0 y el tamanio de la lista. ");
        }
        T eliminado = null;
        
        // eliminar el unico elemento
        if(longitud == 1){
            eliminado = cabeza.elemento;
            clear();
            return eliminado;
        }

        // elimina el primer elemento
        if (i == 0) {
            eliminado = cabeza.elemento;
            cabeza = cabeza.siguiente;
            longitud --;
            return eliminado;
        }
        else if (i == longitud-1) {
            eliminado = cola.elemento;
            Nodo iterador = cabeza;
            for (int j = 0; j < longitud-2; j++) {
                iterador =  iterador.siguiente;
            }
            cola = iterador;
            cola.siguiente = null;
            longitud --;
            return eliminado;
        }

        Nodo iterador = cabeza;
        for (int j = 0; j < i-1; j++) {
            iterador = iterador.siguiente;
        }
        eliminado = iterador.siguiente.elemento;
        iterador.siguiente = iterador.siguiente.siguiente; 
        longitud --;
        return eliminado;
    }

    /**
     * Vacia la lista. Elimina todos los elementos.
     */
    public void clear(){
        cabeza = null;
        cola = null;
        longitud = 0;
    }

    /**
     * Obtiene el elemento en la posicion <i>i</i>
     * 
     * @param i el indice del elemento a obtener
     * @throws IndexOutOfBoundsException si el indice esta fuera de rango.
     * @return el elemento en la posicion i
     */
    public T get(int i) throws IndexOutOfBoundsException{
        if (i < 0 || i >= longitud) {
            throw new IndexOutOfBoundsException("El indice no esta en el rango de 0 y el tamanio de la lista. ");
        }
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("La lista esta vacia");
        }
        if (i == 0){
            return cabeza.elemento;
        }
        Nodo iterador = cabeza;
        // entonces i = [1,longitud]
        for (int j = 0; j < i ; j++) {
            iterador = iterador.siguiente;
        }

        return iterador.elemento;
    }

    /**
     * Verifica si un elemento esta contenido en la lista
     * 
     * @param e el elemento a verificar si esta contenido
     * @return true si el elemento si eta contenido, false en otro caso.
     */
    public boolean contains(T e){
        if (isEmpty()) {
            return false;
        }
        if (longitud == 1) {
            return e.equals(cabeza.elemento);
        }
        Nodo iterador = cabeza;
        for(int j = 0; j < longitud; j++){
            if (e.equals(iterador.elemento)) {
                return true;
            }
            iterador = iterador.siguiente;
        }
        return false;
    }


    /**
     * Verifica si la lista esta vacia.
     * 
     * @return true si la lista no contiene elementos, false en otro caso.
     */
    public boolean isEmpty(){
        // return cabeza == null;
        return longitud == 0; 

    }

    /**
     * Regresa la cantidad de elementos contenidos en la lista.
     * 
     * @return la cantidad de elementos contenidos.
     */
    public int size(){
        // podemos recorrer toda la lista e ir contando cada elemento. Complejidad O(n) -- Lineal sobre el tamaño de la lista.
        return longitud; //podemos guardar un  contador de elementos en la lista. Y actualizarlo tras cada movimiento. Complejidad(1) Constante. 
    
    }

    /**
     * Actividad 6
     * Busca el valor del nodo en una posición b en la primer mitad
     * de una lista
     * @param b el indice del elemento a buscar
     * @return 1 si lo encontro, -1 de otra manera
     * */ 
    public int encuentraValorMitad(int b){
        if(b < 0 || b > longitud || !contains(get(b))){
            return -1;
        }
        int mitad = (longitud / 2) + 1;
        if (b <= mitad){
            System.out.println("Elemento en indice " + b + ": " + get(b));
        } else {
            return -1;
        }
        return -1;
    }

    /**
     * Actividad 6
     * Invierte los valores de la lista
     */
    public void revert(){
        Nodo prev = null;
        Nodo current = cabeza;
        Nodo next = null;
        while (current != null) {
            next = current.siguiente;
            current.siguiente = prev;
            prev = current;
            current = next;
        }
        cabeza = prev;
    }

    public String toString(){
        Nodo iterador = cabeza;
        String cadena= "";
        while(iterador != null){
            cadena = cadena + iterador.elemento;
            if (iterador.siguiente != null){
                cadena += ", ";
            }
            iterador = iterador.siguiente;
        }
        return cadena;
    }

    public Iterator<T> iterador(){
        return new IteradorListaSimple(this.cabeza);
    }

    public void delete(T e){
        if (longitud == 1){
            clear();
        }
        Nodo iterador = cabeza;
        for(int i = 0; i < longitud; i++){
            if (e.equals(iterador.elemento)){
                remove(i);
            }
            iterador = iterador.siguiente;   
        }
    }

    public int indexOf(T e){
        Nodo iterador = cabeza;
        for(int j = 0; j < longitud; j++){
            if (e.equals(iterador.elemento)) {
                return j;
            }
            iterador = iterador.siguiente;
        }
        return -1;
    }
}
