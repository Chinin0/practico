/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bo.edu.uagrm.ficct.inf310sb.ed202201.arboles;

import java.util.List;

/**
 *
 * @author CRISTIAN
 * @param <K>
 * @param <V>
 */
public interface IArbolBusqueda<K extends Comparable<K>,V> {
    void insertar(K claveAInsertar, V valorAInsertar) throws NullPointerException;
    V eliminar(K claveAEliminar) throws NullPointerException, ExcepcionClaveNoExiste; 
    V buscar (K claveBuscar) throws NullPointerException;
    boolean contiene(K claveBuscar) throws NullPointerException;
    int size();
    int altura();
    void vaciarArbol();
    boolean esArbolVacio();
    List<K> recorridoPorNiveles();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnInOrden();
    List<K> recorridoEnPostOrden();
}