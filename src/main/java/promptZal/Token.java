/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package promptZal;

/**
 *
 * @author jesua
 */
public class Token {
    
    // Caracteristicas de los Tokens
    //Numero, Texto exacto encontrado, categoria del Token, fila y columna donde se inicia el Token
    private int numero;
    private String lexema;
    private tipoToken tipo;
    private int fila;
    private int columna;
    
     public Token(int numero, String lexema, tipoToken tipo, int fila, int columna) {
        this.numero = numero;
        this.lexema = lexema;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
    }
     // Obteniendo los valores de cada atributo
      public int getNumero() {
        return numero;
    }

    public String getLexema() {
        return lexema;
    }

    public tipoToken getTipo() {
        return tipo;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
    
    // Para modificar los atributos
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setTipo(tipoToken tipo) {
        this.tipo = tipo;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
    @Override
    public String toString() {
        return numero + " | "
                + lexema + " | "
                + tipo + " | "
                + fila + " | "
                + columna;
    }

}
