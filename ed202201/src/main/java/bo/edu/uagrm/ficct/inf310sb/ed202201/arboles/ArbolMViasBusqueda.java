/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.edu.uagrm.ficct.inf310sb.ed202201.arboles;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author CRISTIAN
 * @param <K>
 * @param <V>
 */
public class ArbolMViasBusqueda <K extends Comparable<K>,V> implements IArbolBusqueda<K,V> {
    
     protected NodoMVias<K,V>raiz;
    protected int orden;
    protected static final int POSICION_NO_VALIDA=-1;
    protected static final int ORDEN_MINIMO=3;
    public ArbolMViasBusqueda(){
        this.orden=ArbolMViasBusqueda.ORDEN_MINIMO;
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) throws NullPointerException {
        if(esArbolVacio()){
             this.raiz=new NodoMVias<>(orden,claveAInsertar,valorAInsertar);
             return;
         }
         NodoMVias<K,V>nodoActual=this.raiz;
                while(!NodoMVias.esNodoVacio(nodoActual)){
                    int posicionClaveEnNodo=existeClaveEnNodo(nodoActual,claveAInsertar);
                    if(posicionClaveEnNodo!=POSICION_NO_VALIDA){
                        nodoActual.setValor(posicionClaveEnNodo, valorAInsertar);
                        nodoActual=NodoMVias.nodoVacio();
                    }
                    if(nodoActual.esHoja()){
                        if(!nodoActual.estanClavesLlenas()){
                            insertarDatosOrdenadosEnNodo(nodoActual,claveAInsertar,valorAInsertar); 
                        }else{
                             int posicionPorDondeBajar=porDondeBajar(nodoActual,claveAInsertar);
                             NodoMVias<K,V>nuevo=new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                             nodoActual.setHijo(posicionPorDondeBajar,nuevo);
                        }
                        nodoActual=NodoMVias.nodoVacio();
                    }else{
                        int posicionBajar=porDondeBajar(nodoActual,claveAInsertar);                            
                            if(NodoMVias.esNodoVacio(nodoActual.getHijo(posicionBajar))){
                                NodoMVias<K,V>nuevoHijo=new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                                nodoActual.setHijo(posicionBajar,nuevoHijo);
                                nodoActual=NodoMVias.nodoVacio();
                            }else{
                                nodoActual=nodoActual.getHijo(posicionBajar);
                            }
                    }
                }
    }
        private int existeClaveEnNodo(NodoMVias<K,V>nodoActual,K claveABuscar){
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            K claveActual=nodoActual.getClave(i);
                if(claveActual.compareTo(claveABuscar)==0){
                    return i;
                }
        }
        return -1;
    }
        
        public void insertarDatosOrdenadosEnNodo(NodoMVias<K,V>nodoActual,K claveAInsertar,V valorAInsertar){
        int res=0;
        for(int i=nodoActual.cantidadDeClavesNoVacias()-1;i>=0;i--){
                K claveActual=nodoActual.getClave(i);
                    if(claveActual.compareTo(claveAInsertar)>0){
                        nodoActual.setClave(i+1, claveActual);
                    }else{
                     res=i;
                     break;
                    }
          
        }
        nodoActual.setClave(res+1, claveAInsertar);
        nodoActual.setValor(res+1, valorAInsertar);
       
    }
        
        public int porDondeBajar(NodoMVias<K,V>nodoActual,K claveABuscar){
        int i=0;
        boolean llegoAlFinal=false;
            while(i<nodoActual.cantidadDeClavesNoVacias()){
                K claveActual=nodoActual.getClave(i);
                    if(claveActual.compareTo(claveABuscar)<0){
                        i++;
                    }else{
                        break;
                    }       
            }
            if(nodoActual.getClave(nodoActual.cantidadDeClavesNoVacias()-1).compareTo(claveABuscar)<0){
                return nodoActual.cantidadDeClavesNoVacias();
            }
            return i;
    }
        
        
         @Override
    public V eliminar(K claveAEliminar) throws NullPointerException, ExcepcionClaveNoExiste {
    if(claveAEliminar==null){
         throw new IllegalArgumentException("La clave no puede ser nula");
     }
     V valorRetorno=this.buscar(claveAEliminar);
        if(valorRetorno!=null){
            //throw new IllegalArgumentException("La clave no Existe en el arbol");
            this.raiz=eliminar(this.raiz,claveAEliminar);
             return valorRetorno;
        }
     throw new IllegalArgumentException("La clave no Existe en el arbol");
     
    }
    public NodoMVias<K,V> eliminar(NodoMVias<K,V>nodoActual,K claveAEliminar){
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
                K claveActual=nodoActual.getClave(i);
                if( claveAEliminar.compareTo(claveActual)==0){
                    if(nodoActual.esHoja()){
                        this.eliminarDatoDelNodo(nodoActual,i);
                        if(nodoActual.cantidadDeClavesNoVacias()==0){
                            return NodoMVias.nodoVacio();
                        }
                        return nodoActual;
                    }else{//SI SE LLEGA ACA ENTOCES LA CLAVE NO ESTA EN UNA HOJA
                          K claveReemplazo;
                            if(this.hayDatosMasAdelante(nodoActual,i)){
                             claveReemplazo=this.buscarSucesorEnInOrden(nodoActual,claveAEliminar);
                            }else{
                             claveReemplazo=this.buscarPredecesorEnInOrden(nodoActual,claveAEliminar);    
                            }
                            V valorDeReemplazo=this.buscar(claveReemplazo);
                            nodoActual=eliminar(nodoActual,claveReemplazo);
                            nodoActual.setClave(i, claveReemplazo);
                            nodoActual.setValor(i, valorDeReemplazo);
                            return nodoActual;
                    }           
                }
                if(claveAEliminar.compareTo(claveActual)<0){
                    NodoMVias<K,V>posibleHijo=this.eliminar(nodoActual.getHijo(i), claveAEliminar);
                    nodoActual.setHijo(i, posibleHijo);
                    return nodoActual;
                    
                }
        }//SI LLEGA AQUI EL FINAL DEL FOR HAY QUE BUSCAR POR EL LADO DERECHO DEL NODO LA POSICION ORDEN;
            NodoMVias<K,V>supuesto=this.eliminar(nodoActual.getHijo(orden-1),claveAEliminar);
            nodoActual.setHijo(orden-1, supuesto);
            return nodoActual;
        
    }
    public K buscarPredecesorEnInOrden(NodoMVias<K,V>nodoActual,K claveABuscar){
     K claveDeRetorno=(K)NodoMVias.datoVacio();
     int posicion=this.porDondeBajar(nodoActual, claveABuscar);
     NodoMVias<K,V>nodoAuxiliar=nodoActual.getHijo(posicion);
        while(!NodoMVias.esNodoVacio(nodoAuxiliar)){
            claveDeRetorno=nodoAuxiliar.getClave(0);
            nodoAuxiliar=nodoAuxiliar.getHijo(0);
        }
        return claveDeRetorno;
    }
    public K buscarSucesorEnInOrden(NodoMVias<K,V>nodoActual,K claveABuscar){
        int posicion=this.porDondeBajar(nodoActual, claveABuscar)+1;
        K claveDeRetorno=(K)NodoMVias.datoVacio();
        NodoMVias<K,V>nodoAuxiliar=nodoActual.getHijo(posicion);
        while(!NodoMVias.esNodoVacio(nodoAuxiliar)){
            claveDeRetorno=nodoAuxiliar.getClave(nodoAuxiliar.cantidadDeClavesNoVacias()-1);
            nodoAuxiliar=nodoAuxiliar.getHijo(0);
        }
      return claveDeRetorno;      
    }
    
    public void eliminarDatoDelNodo(NodoMVias<K,V>nodoActual,int posicion){
        int i=posicion;
        
        for(;i<=nodoActual.cantidadDeClavesNoVacias()-1;i++){
            nodoActual.setClave(i,nodoActual.getClave(i+1));
            nodoActual.setValor(i,nodoActual.getValor(i+1));
        }
    }   
    
    public boolean hayDatosMasAdelante(NodoMVias<K,V>nodoActual,int posicion){
       
      return nodoActual.cantidadDeClavesNoVacias()-1>posicion;
    
    }

    @Override
    public V buscar(K claveBuscar) throws NullPointerException {
      if(claveBuscar==null){
          throw new NullPointerException("Clave a buscar no puede ser nula");
      }
        
        NodoMVias<K,V>nodoActual=this.raiz;  
            while(!NodoMVias.esNodoVacio(nodoActual)){
                boolean huboCambioDeNodoActual=false;
                for(int i=0; i<nodoActual.cantidadDeClavesNoVacias() && !huboCambioDeNodoActual;i++){
                    K claveDeNodoActual=nodoActual.getClave(i);
                        if(claveBuscar.compareTo(claveDeNodoActual)==0){
                            return nodoActual.getValor(i);
                        }
                        if(claveBuscar.compareTo(claveDeNodoActual)<0){
                            nodoActual=nodoActual.getHijo(i);
                            huboCambioDeNodoActual=true;
                        }
                        
                   } 
                 if(!huboCambioDeNodoActual){
                         nodoActual=nodoActual.getHijo(orden-1);
                        }
               
            }
        return null;
    }

    @Override
    public boolean contiene(K claveBuscar) throws NullPointerException {
        return buscar(claveBuscar)!=null;
    }

    @Override
    public int size() {
        if(esArbolVacio()){
            return 0;
        }
        int cantidad=0;
        NodoMVias<K,V>nodoActual=NodoMVias.nodoVacio();
        Queue<NodoMVias<K,V>>colaDeNodos=new LinkedList<>();
        colaDeNodos.offer(this.raiz);
            while(!colaDeNodos.isEmpty()){
                nodoActual=colaDeNodos.poll();
                cantidad++;
                    for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
                        if(!nodoActual.esHijoVacio(i)){
                            colaDeNodos.offer(nodoActual.getHijo(i));
                        }
                    }
                    if(!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())){
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                    }
            }
            return cantidad;
    }

    @Override
    public int altura() {
         if(esArbolVacio()){
           return 0;
       }
       int alturaDelArbol=0;
       Queue<NodoMVias<K,V>>colaDeNodos=new LinkedList<>();
       colaDeNodos.offer(this.raiz);
       NodoMVias<K,V>nodoActual=NodoMVias.nodoVacio();
                while(!colaDeNodos.isEmpty()){
                    int cantidadDeNodosEnLaCola=colaDeNodos.size();
                    int i=0;
                    while(i<cantidadDeNodosEnLaCola){
                      nodoActual=colaDeNodos.poll();
                        for(int x=0;x<nodoActual.cantidadDeClavesNoVacias();x++){
                            if(!nodoActual.esHijoVacio(x)){
                                colaDeNodos.offer(nodoActual.getHijo(x));
                            }
                        }
                        if(!NodoMVias.esNodoVacio(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()))){
                            colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                        }
                        i++;
                    }
                    alturaDelArbol++;
                }
                return alturaDelArbol;
    }

    @Override
    public void vaciarArbol() {
        this.raiz=NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public List<K> recorridoPorNiveles() {
       List<K>listaDeClaves=new LinkedList<>();
        if(esArbolVacio()){
            return listaDeClaves;
        }
        Queue<NodoMVias<K,V>>colaDeNodos=new LinkedList<>();
        colaDeNodos.offer(this.raiz);
            while(!colaDeNodos.isEmpty()){
                NodoMVias<K,V>nodoActual=colaDeNodos.poll();
                for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
                    listaDeClaves.add(nodoActual.getClave(i));
                    if(!NodoMVias.esNodoVacio(nodoActual.getHijo(i))){
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
                if(!NodoMVias.esNodoVacio(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()))){
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                }
                 
            }
        return listaDeClaves;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K>recorrido=new LinkedList<>();
       recorridoEnPreOrden(this.raiz,recorrido);
       return recorrido;
    }
    public void recorridoEnPreOrden(NodoMVias<K,V>nodoActual,List<K>recorrido){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        
        recorrido.add(nodoActual.getClave(0));
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            recorrido.add(nodoActual.getClave(i));
            recorridoEnPreOrden(nodoActual.getHijo(i),recorrido);
        }
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()),recorrido);
    }

    @Override
    public List<K> recorridoEnInOrden() {
       List<K>recorrido=new LinkedList<>();
       recorridoEnInOrden(this.raiz,recorrido);
       return recorrido;
    }
    private void recorridoEnInOrden(NodoMVias<K,V>nodoActual,List<K>recorrido){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            recorridoEnInOrden(nodoActual.getHijo(i),recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
        
        recorridoEnInOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()),recorrido);
    }

    @Override
    public List<K> recorridoEnPostOrden() {
          List<K>recorrido=new LinkedList<>();
       recorridoEnPostOrden(this.raiz,recorrido);
       return recorrido;
    }
    private void recorridoEnPostOrden(NodoMVias<K,V>nodoActual,List<K>recorrido){
        
        if(NodoMVias.esNodoVacio(nodoActual)){
            return ;
        }
        
        recorridoEnPostOrden(nodoActual.getHijo(0),recorrido);
        for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            recorridoEnPostOrden(nodoActual.getHijo(i+1),recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
    }
    
         protected int obtenerPosicionDeClave(NodoMVias<K,V> nodoActual, K claveABuscar){
    for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++)
    {
        K claveActual= nodoActual.getClave(i);
        if(claveABuscar.compareTo(claveActual)==0){
            return i;
        }
    }
    return ArbolMViasBusqueda.POSICION_NO_VALIDA;
    }
     
    protected int obtenerPosicionPorDondeBajar(NodoMVias<K,V>nodoActual,K claveABuscar){
    for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
    K claveActual=nodoActual.getClave(i);
    if (claveABuscar.compareTo(claveActual)<00){
    return i;}
    }
    return nodoActual.cantidadDeClavesNoVacias();
    }
    }