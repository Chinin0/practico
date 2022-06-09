/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.edu.uagrm.ficct.inf310sb.ed202201.arboles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author CRISTIAN
 * @param <K>
 * @param <V>
 */
public class ArbolBinarioBusqueda <K extends Comparable<K>, V>implements IArbolBusqueda<K,V>{
    protected NodoBinario<K,V> raiz;

    public ArbolBinarioBusqueda(NodoBinario<K, V> raiz) {
        this.raiz = raiz;
    }

    public ArbolBinarioBusqueda() {
    }

    public ArbolBinarioBusqueda(ArrayList<Integer> claveInOrden, ArrayList<String> valoresInOrden, ArrayList<Integer> clavesPostOrden, ArrayList<String> valoresPostOrden) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) throws NullPointerException {
        if(claveAInsertar==null){
        throw new NullPointerException("Clave a insertar no puede ser nula");
        }
        if(valorAInsertar==null){
        throw new NullPointerException("Valor a insertar no puede ser nula");
        }
        if(this.esArbolVacio()){
        this.raiz= new NodoBinario<>(claveAInsertar, valorAInsertar);
        return;
        }
        NodoBinario<K,V>nodoAnterior=new NodoBinario<>(claveAInsertar, valorAInsertar);
        NodoBinario<K,V>nodoActual=this.raiz;
        
        while(!NodoBinario.esNodoVacio(nodoActual)){
        K claveDeNodoActual= nodoActual.getClave();
            if(claveAInsertar.compareTo(claveDeNodoActual)<0){
            nodoActual=nodoActual.getHijoIzquierdo();            
            }
            else if(claveAInsertar.compareTo(claveDeNodoActual)>0){
            nodoActual=nodoActual.getHijoDerecho();
            }
            else{
            nodoActual.setValor(valorAInsertar);
            return;
            }
        
        }
        NodoBinario<K,V> nuevoNodo= new NodoBinario<>(claveAInsertar, valorAInsertar);
        K claveDeNodoAnterior=nodoAnterior.getClave();
            if(claveAInsertar.compareTo(claveDeNodoAnterior)<0){
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
            }
            else nodoAnterior.setHijoDerecho(nuevoNodo);
            return;
    }

    @Override
    public V eliminar(K claveAEliminar) {
        V valorRetorno=this.buscar(claveAEliminar);
        if(valorRetorno==null){
          return null;
        }
        this.raiz=eliminar(raiz,claveAEliminar);
        return valorRetorno;
    }
    private NodoBinario<K,V>eliminar(NodoBinario<K,V>nodoActual,K claveAEliminar){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return (NodoBinario<K,V>)NodoBinario.nodoVacio();
        }
            K claveActual=nodoActual.getClave();
                if(claveAEliminar.compareTo(claveActual)<0){
                    NodoBinario<K,V>izquierdo=eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
                    nodoActual.setHijoIzquierdo(izquierdo);
                    return nodoActual;
                }
                if(claveAEliminar.compareTo(claveActual)>0){
                   NodoBinario<K,V>derecho=eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
                   nodoActual.setHijoDerecho(derecho);
                   return nodoActual;
                }
             /// SI SE LLEGA A ESTE PUNTO SE ENCONTRO LA CLAVE A ELIMINAR
             ///YA QUE LA CLAVE A ELIMINAR NO ES MENOR NI MAYOR ,SINO IGUAL
            // # caso 1 el nodo a eliminar es una hoja
            if(nodoActual.esHoja()){
                return (NodoBinario<K,V>)NodoBinario.nodoVacio();
            }
            //# CASO 2 LA CLAVE A ELIMINAR ES UN NODO INCOMPLETO
            if(nodoActual.esHijoDerechoVacio() && !nodoActual.esHijoIzquierdoVacio()){
                return nodoActual.getHijoIzquierdo();
            }
            if(!nodoActual.esHijoDerechoVacio() && nodoActual.esHijoIzquierdoVacio()){
                return nodoActual.getHijoDerecho();
            }
            // # CASO 3 LA CLAVE A ELIMINAR ES UN NODO COMPLETO 
            // HAY QUE BUSCAR SU NODO SUCESOR
            NodoBinario<K,V>nodoSucesor=cambiar(nodoActual.getHijoDerecho());
            NodoBinario<K,V>posibleNuevo=eliminar(nodoActual.getHijoDerecho(),nodoSucesor.getClave());
            nodoActual.setHijoDerecho(posibleNuevo);
            nodoSucesor.setHijoDerecho(nodoActual.getHijoDerecho());
            nodoSucesor.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
            nodoActual.setHijoDerecho((NodoBinario<K,V>)NodoBinario.nodoVacio());
            nodoActual.setHijoIzquierdo((NodoBinario<K,V>)NodoBinario.nodoVacio());
        
        return nodoSucesor;
    
    }
    public NodoBinario<K,V> cambiar(NodoBinario<K,V>nodoActual){ 
         NodoBinario<K,V>anterior=(NodoBinario<K,V>)NodoBinario.nodoVacio();
         if(NodoBinario.esNodoVacio(nodoActual)){
             return (NodoBinario<K,V>)NodoBinario.nodoVacio();
         }
         while(!NodoBinario.esNodoVacio(nodoActual)){
             anterior=nodoActual;
             nodoActual=nodoActual.getHijoIzquierdo();
         }
         return anterior;
    }
    

    @Override
    public V buscar(K claveBuscar) throws NullPointerException {
        if(claveBuscar==null){
            throw new NullPointerException("Clave buscar no puede ser nula");
        }
        NodoBinario<K,V> nodoActual=this.raiz;
            while(!NodoBinario.esNodoVacio(nodoActual)){
                K claveActual=nodoActual.getClave();
                if(claveBuscar.compareTo(claveActual)<0){
                    nodoActual=nodoActual.getHijoIzquierdo();
                }
                if(claveBuscar.compareTo(claveActual)>0){
                    nodoActual=nodoActual.getHijoDerecho();
                }
                else{
                    return nodoActual.getValor();
                }
            }
            return null;
    }
public String mostrarNiveles(){
        String cadena="";
        if(esArbolVacio()){
            return cadena;
        }
        Queue<NodoBinario<K,V>>colaDeNodos=new LinkedList<>();
        colaDeNodos.offer(raiz);
            while(!colaDeNodos.isEmpty()){
                int cantidadDeNodos=colaDeNodos.size();
                int i=0;
                    while(i<cantidadDeNodos){
                        NodoBinario<K,V>nodoActual=colaDeNodos.poll();
                        cadena=cadena+nodoActual.getClave().toString()+"    ";
                        if(!nodoActual.esHijoIzquierdoVacio()){
                            colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                        }if(!nodoActual.esHijoDerechoVacio()){
                            colaDeNodos.offer(nodoActual.getHijoDerecho());
                        }
                        i++;
                    }
                  cadena=cadena+"\n";  
            }
            return cadena;
    }
    @Override
    public boolean contiene(K claveBuscar) throws NullPointerException {
        return this.buscar(claveBuscar)!=null;
    }

    @Override
    public int size() {
        Stack<NodoBinario<K,V>>pilaNodos=new Stack<>();
       int cantidad=0;
       if(this.esArbolVacio()){
           return cantidad;
       }
       pilaNodos.push(this.raiz);
       while(!pilaNodos.empty()){
           NodoBinario<K,V>nodo=pilaNodos.pop();
           cantidad++;
            if(!nodo.esHijoDerechoVacio()){
                pilaNodos.push(nodo.getHijoDerecho());
            }
            if(!nodo.esHijoIzquierdoVacio()){
                pilaNodos.push(nodo.getHijoIzquierdo());
            }
       }
       return cantidad;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }
    protected int altura(NodoBinario<K,V>nodo){
        if(NodoBinario.esNodoVacio(nodo)){
            return 0;
        }else{
            int a=altura(nodo.getHijoDerecho());
            int b=altura(nodo.getHijoIzquierdo());
            return a>b?a+1:b+1;
        }
        
    }

    @Override
    public void vaciarArbol() {
        this.raiz=NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz); 
    }
    
    public void  eliminarLasHojas(){
       
      this.raiz=eliminarLasHojas(this.raiz);
       
  }
  private NodoMVias<K,V>eliminarHojas(NodoMVias<K,V>nodoActual){
      if(NodoMVias.esNodoVacio(nodoActual)){
          return NodoMVias.nodoVacio();
      }
      if(nodoActual.esHoja()){
          return (NodoMVias<K,V>)NodoMVias.nodoVacio();
      }
      for(int i=0;i<nodoActual.cantidadDeClavesNoVacias();i++){
            NodoMVias<K,V>eliminados=eliminarHojas(nodoActual.getHijo(i));
            nodoActual.setHijo(i,eliminados);
        }
      NodoMVias<K,V>nodoEl=eliminarHojas(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
      nodoActual.setHijo(nodoActual.cantidadDeClavesNoVacias(),nodoEl);
      return nodoActual;
  
  }

    @Override
    public List<K> recorridoPorNiveles() {
       List<K> recorrido= new ArrayList<>();
       if(this.esArbolVacio()){
           return recorrido;
       }
        Queue<NodoBinario<K,V>> colaDeNodo = new LinkedList<>();
        colaDeNodo.offer(this.raiz);
            while(!colaDeNodo.isEmpty()){
                NodoBinario<K,V> nodoActual= colaDeNodo.poll();
                recorrido.add(nodoActual.getClave());
            }
            return null;
    }

    @Override
        public List<K> recorridoEnPreOrden() {
       Stack<NodoBinario<K,V>>pilaNodos=new Stack<>();
       List<K>lista=new ArrayList();
       if(this.esArbolVacio()){
           return lista;
       }
       pilaNodos.push(this.raiz);
       while(!pilaNodos.empty()){
           NodoBinario<K,V>nodo=pilaNodos.pop();
           lista.add(nodo.getClave());
            if(!nodo.esHijoDerechoVacio()){
                pilaNodos.push(nodo.getHijoDerecho());
            }
            if(!nodo.esHijoIzquierdoVacio()){
                pilaNodos.push(nodo.getHijoIzquierdo());
            }
       }
       return lista;
    }
        
        public K predecesorInOrden(K clave){
     if(!this.contiene(clave)){
       throw new IllegalArgumentException("El nodo no existe en el arbol");
   }
   NodoBinario<K,V>nodo=precesorInOrdenAux(clave);
   if(NodoBinario.esNodoVacio(nodo)){
       return null;
   }
   return nodo.getClave();
        }
        
   private NodoBinario<K,V>precesorInOrdenAux(K clave){
    NodoBinario<K,V>auxiliar=this.raiz;
    
    boolean sw=false;    
        while(sw==false){
             K claveAuxiliar=auxiliar.getClave();
            if(clave.compareTo(claveAuxiliar)<0){
                auxiliar=auxiliar.getHijoIzquierdo();
            }else if(clave.compareTo(claveAuxiliar)>0){
                auxiliar=auxiliar.getHijoDerecho();
            }else{
                sw=true;
            }
        }
        NodoBinario<K,V>nodoAnterior=auxiliar;
        if(sw==true){
                if(!NodoBinario.esNodoVacio(auxiliar.getHijoIzquierdo())){
                    nodoAnterior=auxiliar;
                    auxiliar=auxiliar.getHijoIzquierdo();
                        while(!NodoBinario.esNodoVacio(auxiliar)){
                            nodoAnterior=auxiliar;
                            auxiliar=auxiliar.getHijoDerecho();
                        }
                    
                }else{
                    nodoAnterior=(NodoBinario<K,V>)NodoBinario.nodoVacio();
                }
        }
       return nodoAnterior;
}
   
   public int cantidadDeHijosConInOrden(){
    if(esArbolVacio()){
        return 0;
    }
    int size=0;
    Stack<NodoBinario<K,V>>pila=new Stack<>();
    meterPilaEnInParaSize(pila,this.raiz);
        while(!pila.isEmpty()){
            NodoBinario<K,V>nodoActual=pila.pop();
            size++;
                if(!nodoActual.esHijoDerechoVacio()){
                    meterPilaEnInParaSize(pila,nodoActual.getHijoDerecho());
                }
        }
    
    return size;
    
}
   
   private void meterPilaEnInParaSize(Stack<NodoBinario<K,V>>pila,NodoBinario<K,V>nodoActual){
    while(!NodoBinario.esNodoVacio(nodoActual)){
        pila.push(nodoActual);
        if(!nodoActual.esHijoIzquierdoVacio()){
            nodoActual=nodoActual.getHijoIzquierdo();
        }else{
            nodoActual=(NodoBinario<K,V>)NodoBinario.nodoVacio();
        }
    }

}


    @Override
    public List<K> recorridoEnInOrden() {
       List<K>recorrido=new ArrayList<>();
       recorridoIn(recorrido,this.raiz);
       return recorrido;
    }
    private void recorridoIn(List<K> lista,NodoBinario<K,V>nodo){
        if(NodoBinario.esNodoVacio(nodo)){
        }else{
         recorridoIn(lista,nodo.getHijoIzquierdo());
         lista.add(nodo.getClave());
         recorridoIn(lista,nodo.getHijoDerecho());
        }
    }

    
    //public List<K> recorridoEnPostOrdenIterativo() {
    @Override
    public List<K> recorridoEnPostOrden() {
        List<K>lista=new ArrayList<>();
        if(esArbolVacio()){
            return lista;
        }
        Stack<NodoBinario<K,V>>pilaNodos=new Stack<>();
        NodoBinario<K,V>actual=this.raiz;
        //el procesos inicial antes de iterar en la pila
        meterPilaParaPostOrden(pilaNodos,actual);
        //empezamos a iterar sobre la pila
            while(!pilaNodos.isEmpty()){
                actual=pilaNodos.pop();
                lista.add(actual.getClave());
                    if(!pilaNodos.isEmpty()){
                        NodoBinario<K,V>tope=pilaNodos.peek();
                            if(!tope.esHijoDerechoVacio() && (tope.getHijoDerecho() != actual)){
                                meterPilaParaPostOrden(pilaNodos,tope.getHijoDerecho());
                            }
                    }
            }
            return lista;
    }
        public void meterPilaParaPostOrden(Stack<NodoBinario<K,V>>pila,NodoBinario<K,V>nodo){
        while(!NodoBinario.esNodoVacio(nodo)){
            pila.push(nodo);
                if(!nodo.esHijoIzquierdoVacio()){
                    nodo=nodo.getHijoIzquierdo();
                }else{
                    nodo=nodo.getHijoDerecho();
                }
        }
    }
        
    private NodoBinario<K,V>eliminarLasHojas(NodoBinario<K,V> nodo){
        if(NodoBinario.esNodoVacio(nodo)){
            return null;
        }
        NodoBinario<K,V>aux=eliminarLasHojas(nodo.getHijoDerecho());
        NodoBinario<K,V>aux2=eliminarLasHojas(nodo.getHijoIzquierdo());
            if(nodo.esHoja()){
                return null;
            }else{
              nodo.setHijoDerecho(aux);
              nodo.setHijoIzquierdo(aux2);
            }
            return nodo;
    }
        public Queue<K>sacarPorNiveles(int nivelDado){
       //se usa una lista o vector para almacenar cada clave y su hijo de izquierda a derecha 
      Queue<NodoBinario<K,V>>colaNodos=new LinkedList<>();
      Queue<K>colaClaves=new LinkedList<>();
       if(esArbolVacio()){
          return colaClaves;
      }
      
      int nivelActual=0;
     // Queue<NodoBinario<K,V>>colaNodos=new LinkedList<>();
      colaNodos.offer(this.raiz);
       nivelActual++;
        while(!colaNodos.isEmpty()&& nivelActual<=nivelDado){
            int cantidadDeNodosEnLaCola=colaNodos.size();
            int i=0;
            nivelActual++;
           while(i<cantidadDeNodosEnLaCola){    
           NodoBinario<K,V> actual=colaNodos.poll();
           if(!actual.esHijoIzquierdoVacio()){
               colaNodos.offer(actual.getHijoIzquierdo());
           }
           if(!actual.esHijoDerechoVacio()){
               colaNodos.offer(actual.getHijoDerecho());
           }
           i++;
          }
          
        }  
             while(!colaNodos.isEmpty()){
                colaClaves.offer(colaNodos.poll().getClave());
             }   
      return colaClaves;
        }
        
    public void eliminarLosNodosDeUnNivel(int nivelAEliminar){
        if(esArbolVacio()){
            throw new IllegalArgumentException("El arbol es vacio");
        }
    Queue<K>clavesAEliminar=new LinkedList<>();
    clavesAEliminar=sacarPorNiveles(nivelAEliminar);
        while(!clavesAEliminar.isEmpty()){
            eliminar(clavesAEliminar.poll());
        }
    
    }
    public int alturaIt(){
       //se usa una lista o vector para almacenar cada clave y su hijo de izquierda a derecha 
      if(esArbolVacio()){
          return 0;
      }
      int alturaArbol=0;
      Queue<NodoBinario<K,V>>colaNodos=new LinkedList<>();
      colaNodos.offer(this.raiz);  
        while(!colaNodos.isEmpty()){
            int cantidadDeNodosEnLaCola=colaNodos.size();
            int i=0;
           while(i<cantidadDeNodosEnLaCola ){    
           NodoBinario<K,V> actual=colaNodos.poll();
           if(!actual.esHijoIzquierdoVacio()){
               colaNodos.offer(actual.getHijoIzquierdo());
           }
           if(!actual.esHijoDerechoVacio()){
               colaNodos.offer(actual.getHijoDerecho());
           }
           i++;
          }
          alturaArbol++;
        }  
      return alturaArbol;           
    }
    public int incompletoPorIzquierdaEnUnNIvel(int nivelABuscar){
        return incompletosPorIzquierdaEnUnNivel(this.raiz,0,nivelABuscar);
    }
    private int incompletosPorIzquierdaEnUnNivel(NodoBinario<K,V>nodoActual,int contador,int nivelABuscar){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        int porIzquierda=incompletosPorIzquierdaEnUnNivel(nodoActual.getHijoIzquierdo(),contador+1,nivelABuscar);
         int porDerecha=incompletosPorIzquierdaEnUnNivel(nodoActual.getHijoDerecho(),contador+1,nivelABuscar);
            if(contador==nivelABuscar){
                if(nodoActual.esIncompletoPorDerecha()){
                    return  porIzquierda+porDerecha+1;
                }
            
         }
         return porIzquierda+porDerecha;
    }

    
    public int nivel() {
        return this.altura()-1;
    }

  
    public K minimo() {
       if(esArbolVacio()){
           return null;
       }
       NodoBinario<K,V>actual=this.raiz;
       NodoBinario<K,V>anterior=(NodoBinario<K,V>)NodoBinario.nodoVacio();
        while(!NodoBinario.esNodoVacio(actual)){
            anterior=actual;
            actual=actual.getHijoIzquierdo();
        }
       return anterior.getClave();
    }

   
    public K maximo() {
       if(esArbolVacio()){
           return null;
       }
        NodoBinario<K,V>actual=this.raiz;
        NodoBinario<K,V>anterior=(NodoBinario<K,V>)NodoBinario.nodoVacio();
        while(!NodoBinario.esNodoVacio(actual)){
            anterior=actual;
            actual=actual.getHijoDerecho();
        }
        return anterior.getClave();
    }  
    
    public boolean verificarSiSonArbolesSimilares(ArbolBinarioBusqueda<K,V> arbol){
    return verificarSiSonArbolesSimilares(this.raiz,arbol.raiz);
 }
    private boolean verificarSiSonArbolesSimilares(NodoBinario<K,V>nodoActual1,NodoBinario<K,V>nodoActual2){
     
        if(NodoBinario.esNodoVacio(nodoActual1) && NodoBinario.esNodoVacio(nodoActual2)){
            return true;
        }
        boolean respuestaPorIzquierda=verificarSiSonArbolesSimilares(nodoActual1.getHijoIzquierdo(),nodoActual2.getHijoIzquierdo());
        boolean respuestaPorDerecha=verificarSiSonArbolesSimilares(nodoActual1.getHijoDerecho(),nodoActual2.getHijoDerecho());
        if(NodoBinario.esNodoVacio(nodoActual1) && !NodoBinario.esNodoVacio(nodoActual2)){
            return false;
        }
        if(!NodoBinario.esNodoVacio(nodoActual1) && NodoBinario.esNodoVacio(nodoActual2)){
         return false;
        }
        return respuestaPorIzquierda && respuestaPorDerecha;
 }
    
    }
    
   