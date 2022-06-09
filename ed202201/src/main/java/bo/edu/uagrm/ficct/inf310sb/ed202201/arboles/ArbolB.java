/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.edu.uagrm.ficct.inf310sb.ed202201.arboles;

/**
 *
 * @author CRISTIAN
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
public class ArbolB <K extends Comparable<K>,V> extends ArbolMViasBusqueda<K,V>{
    
    private final int nroMaximoDeDatos;
     private final int nroMinimoDeDatos;
      private final int nroMinimoDeHijos;
     // private final int nroMaxDeHijos=nroMaximoDeDatos+1;
      
public ArbolB(){
  super();
  this.nroMaximoDeDatos=2;
  this.nroMinimoDeHijos=2;
  this.nroMinimoDeDatos=1;
  
}

   
    public ArbolB(int orden){
//super(orden);
this.nroMaximoDeDatos=orden-1;
this.nroMinimoDeDatos=this.nroMaximoDeDatos/2;
this.nroMinimoDeHijos=this.nroMinimoDeDatos + 1;
}


    private void dividir(NodoMVias<K,V>nodoActualActual, Stack<NodoMVias<K,V>>pilaDeAncestros) {}
     /*int med=(nodoActual.getNroDatos()/2)+1;
       if (nodoActual.getPadre()==null){
         //primer caso  
         NodoMVias padre=new NodoMVias(null, nroMaxDeDatos, nroMaxDeHijos);
         padre.InsertarOrd(nodoActual.getDato(med));
         
         NodoMVias nodoActualIzq=new NodoMVias(padre, nroMaxDeDatos, nroMaxDeHijos);
         for (int i=1;i<med;i++) nodoActualIzq.InsertarOrd(nodoActual.getDato(i));
         
         NodoMVias nodoActualDer=new NodoMVias(padre, nroMaxDeDatos, nroMaxDeHijos);
         for (int i=nroMaxDeDatos+1;i>med;i--) nodoActualDer.InsertarOrd(nodoActual.getDato(i));
         
           if (nodoActual.tieneHijos()){
              for (int i=1;i<=med;i++){
                 nodoActualIzq.setHijo(nodoActual.getHijo(i), i);
                 nodoActualIzq.getHijo(i).setPadre(nodoActualIzq);
              }  
              int k=1;
              for (int i=med+1;i<=nroMaxDeHijos+1;i++){
                 nodoActualDer.setHijo(nodoActual.getHijo(i), k);
                 nodoActualDer.getHijo(k).setPadre(nodoActualDer);
                 k++;
              }  
           }
         
           padre.setHijo(nodoActualIzq, 1);
           padre.setHijo(nodoActualDer, 2);
           raiz=padre;
       }else{
          //segundo caso 
          nodoActual.getPadre().InsertarOrd(nodoActual.getDato(med));
          
          NodoMVias nodoActualIzq=new NodoMVias(nodoActual.getPadre(), nroMaxDeDatos, nroMaxDeHijos);
          for (int i=1;i<med;i++) nodoActualIzq.InsertarOrd(nodoActual.getDato(i));
          nodoActual.getPadre().setHijo(nodoActualIzq, nodoActual.getPadre().indiceDeLlaves(nodoActual.getDato(med)));
         
          NodoMVias nodoActualDer=new NodoMVias(nodoActual.getPadre(), nroMaxDeDatos, nroMaxDeHijos);
          for (int i=nroMaxDeDatos+1;i>med;i--)nodoActualDer.InsertarOrd(nodoActual.getDato(i));
         
          int indiceDatos=nodoActual.getPadre().indiceDeLlaves(nodoActual.getDato(med))+1;
            if (nodoActual.getPadre().getHijo(indiceDatos)==null){
              nodoActual.getPadre().setHijo(nodoActualDer, indiceDatos);
            }else{
                if (nodoActual.getPadre().getHijo(indiceDatos+1)==null){
                  nodoActual.getPadre().setHijo(nodoActual.getPadre().getHijo(indiceDatos), indiceDatos+1);
                  nodoActual.getPadre().setHijo(nodoActualDer, indiceDatos);
                }else{
                  nodoActual.getPadre().setHijo(nodoActual.getPadre().getHijo(indiceDatos+1), indiceDatos+2);
                  nodoActual.getPadre().setHijo(nodoActual.getPadre().getHijo(indiceDatos), indiceDatos+1);
                  nodoActual.getPadre().setHijo(nodoActualDer, indiceDatos);
                }
            }
           //tercer caso
             if (nodoActual.getPadre().estaLleno()){
                dividir(nodoActual.getPadre());
             }   
       }
    }
   
/**********************PRACTICO*****************************/
/****PREGUNTA 8: Para el arbol B implementar el metodo insertar****/
@Override
public void insertar(K claveAInsertar, V valorAInsertar) throws NullPointerException{
    if (claveAInsertar==null){
    throw new NullPointerException("Clave a insertar no puede ser nulo");
    }
   if (valorAInsertar==null){
    throw new NullPointerException("Valor a insertar no puede ser nulo");
    }
   if(this.esArbolVacio()){
   this.raiz = new NodoMVias<>(this.orden +1 , claveAInsertar, valorAInsertar);
   return;
   }
   Stack<NodoMVias<K,V>> pilaDeAncestros= new Stack();
   NodoMVias<K,V>nodoActual=this.raiz;
   while (!NodoMVias.esNodoVacio(nodoActual)){
   int posicionDeClaveAInsertar=this.obtenerPosicionDeClave(nodoActual, claveAInsertar);
   if(posicionDeClaveAInsertar != ArbolMViasBusqueda.POSICION_NO_VALIDA){
   nodoActual.setValor(posicionDeClaveAInsertar, valorAInsertar);
   nodoActual=NodoMVias.nodoVacio();
   }else if (nodoActual.esHoja()){
   super.insertarDatosOrdenadosEnNodo(nodoActual, claveAInsertar,valorAInsertar);
   if(nodoActual.cantidadDeClavesNoVacias()>this.nroMaximoDeDatos){
   this.dividir(nodoActual,pilaDeAncestros);
   }
   nodoActual=NodoMVias.nodoVacio();
   }else{
    
   int posicionPorDondeBajar=this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
           pilaDeAncestros.push(nodoActual);
           nodoActual=nodoActual.getHijo(posicionPorDondeBajar);
           }
   
   }

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
}
