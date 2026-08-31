/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package promptZal;

/**
 *
 * @author jesua
 */
public class errorLexico {
    
    // Caracter que produjo el error, Tipo de error encontrado, fila y columna donde ocurrio el error
    private String lexema;
    private String tipoError;
    private int fila;
    private int columna;
    
    //Constructor para los errores lexicos
    public errorLexico(String lexema, String tipoError, int fila, int columna){
        this.lexema = lexema;
        this.tipoError = tipoError;
        this.fila = fila;
        this.columna = columna;
    }
    
    //Getters
    public String getLexema(){
        return lexema;
    }
    
    public String getTipoError(){
        return tipoError;
    }
    
    public int getFila(){
        return fila;
    }
    
    public int getColumna(){
        return columna;
    }
    
    //Setters
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }
    
    public void setTipoError(String tipoError){
        this.tipoError = tipoError;
    }
    
    public void setFila(int fila){
        this.fila = fila;
    }
    
    public void setColumna(int columna){
        this.columna = columna;
    }
    
}
