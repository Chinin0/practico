/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.edu.uagrm.ficct.inf310sb.ed202201.arboles;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author CRISTIAN
 */
public class NodoMVias<K,V> {
    private List<K>listaDeClaves;
    private List<V>listaDeValores;
    private List<NodoMVias<K,V>>listaDeHijos;
    
    public NodoMVias(int orden){
        this.listaDeClaves=new LinkedList<>();
        this.listaDeValores=new LinkedList<>();
        listaDeHijos=new LinkedList<>();
        for(int i=0;i<orden-1;i++){
            listaDeClaves.add((K)NodoMVias.datoVacio());
            listaDeValores.add((V)NodoMVias.datoVacio());
            listaDeHijos.add(NodoMVias.nodoVacio());
        }
        listaDeHijos.add(NodoMVias.nodoVacio());
    }
    public NodoMVias(int orden,K primerClave,V primerValor){
        this(orden);
        listaDeClaves.set(0, primerClave);
        listaDeValores.set(0, primerValor);
    }
    public static NodoMVias nodoVacio(){
        return null;
    }
    public static NodoMVias datoVacio(){
        return null;
    }
    public NodoMVias<K,V>getHijo(int posicion){
        return listaDeHijos.get(posicion);
    }
    
    public K getClave(int posicion){
        return listaDeClaves.get(posicion);
    }
    public V getValor(int posicion){
        return listaDeValores.get(posicion);
    }
    public void setClave(int pos,K clave){
        listaDeClaves.set(pos, clave);
    }
    public void setValor(int posicion,V clave){
        this.listaDeValores.set(posicion,clave);
    }
    public void setHijo(int posicion,NodoMVias<K,V>nodo){
        this.listaDeHijos.set(posicion, nodo);
    }
    public static boolean esNodoVacio(NodoMVias nodo){
       return nodo==NodoMVias.nodoVacio();
    }
    
    public boolean esClavesVacia(int posicion){
        return this.listaDeClaves.get(posicion)==NodoMVias.datoVacio();
    }
    public boolean esHijoVacio(int posicion){
       return  this.listaDeHijos.get(posicion)==NodoMVias.nodoVacio();
    }
    public boolean esHoja(){
        for(int i=0;i<listaDeHijos.size();i++){
                if(!esHijoVacio(i)){
                    return false;
                }
        }
       return true; 
     }
    public boolean estanClavesLlenas(){
        for(int i=0;i<this.listaDeClaves.size();i++){
            if(this.esClavesVacia(i)){
                return false;
            }
        }
        return true;
    }
    public int cantidadDeClavesNoVacias(){
     int cantidad=0;
      for(int i=0;i<this.listaDeClaves.size();i++){
            if(!this.esClavesVacia(i)){
                cantidad++;
            }
        }
      return cantidad;
    }
    public int cantidadDeHijosVacion(){
        int cantidad=0;
            for(int i=0;i<this.listaDeHijos.size();i++){
                if(esHijoVacio(i)){
                    cantidad++;
                }
            }
            return cantidad;
    }
    public int cantidadDeHijosNoVacios(){
        int cantidad=0;
        for(int i=0;i<listaDeHijos.size();i++){
            if(!this.esHijoVacio(i)){
                cantidad++;
            }
        }
        return cantidad;
    }
}
