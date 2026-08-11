/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package promptZal;

import java.util.ArrayList;

/**
 *
 * @author jesua
 */
public class analizadorLexico {
    
    private String entrada;
    private int posicion;
    private int fila;
    private int columna;
    private int numeroToken;
    private ArrayList<Token> listaTokens;
    private ArrayList<errorLexico> listaErrores;
    
    public analizadorLexico (String entrada){
        this.entrada = entrada;
        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.numeroToken = 1;
        this.listaTokens = new ArrayList<>();   
        this.listaErrores = new ArrayList<>();
    }
    
    public void analizador(){
        while(posicion < entrada.length()){
            char actual = entrada.charAt(posicion);
            System.out.println(
                    "Carácter: [" + actual + "]"
                    + " | Fila: " + fila
                    + " | Columna: " + columna
            );
            
            if(actual == '\n'){
                fila++;
                columna = 1;
            }else{
                columna++;
            }
            
            posicion++;

        }
    
    }
    
    public ArrayList<Token> getlistaTokens(){
        return listaTokens;
    }
    
    public ArrayList<errorLexico> getlistaErrores(){
        return listaErrores;
    }
            
}
