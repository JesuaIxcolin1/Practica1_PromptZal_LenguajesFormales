/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package promptZal;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author jesua
 */
public class GraphvizAFD {
    
    public void generarDOT(){
        
        try{
        FileWriter archivo = new FileWriter("afd.dot");
        
        archivo.write("digraph AFD {\n");
        archivo.write("rankdir=LR;\n\n");
        
        // Estado inicial
        archivo.write("inicio [shape=point];\n");
        archivo.write("inicio -> INICIAL;\n\n");
        
         // Estados de aceptacion
        archivo.write("DIRECTIVA [shape=doublecircle];\n");
        archivo.write("CADENA [shape=doublecircle];\n");
        archivo.write("ENTERO [shape=doublecircle];\n");
        archivo.write("DECIMAL [shape=doublecircle];\n");
        archivo.write("IDENTIFICADOR [shape=doublecircle];\n");
        archivo.write("CONECTOR [shape=doublecircle];\n");
        archivo.write("OPERADOR [shape=doublecircle];\n");
        archivo.write("DELIMITADOR [shape=doublecircle];\n\n");
        
        // Estado de error
        archivo.write("ERROR [shape=circle];\n\n");
        
        // Transiciones desde el estado inicial
        archivo.write("INICIAL -> DIRECTIVA [label=\"@\"];\n");
        archivo.write("INICIAL -> CADENA [label=\"comilla\"];\n");
        archivo.write("INICIAL -> ENTERO [label=\"digito\"];\n");
        archivo.write("INICIAL -> IDENTIFICADOR [label=\"letra, _\"];\n");
        archivo.write("INICIAL -> COMENTARIO_LINEA [label=\"//\"];\n");
        archivo.write("INICIAL -> COMENTARIO_BLOQUE [label=\"/*\"];\n");
        archivo.write("INICIAL -> GUION [label=\"-\"];\n");
        archivo.write("INICIAL -> OPERADOR [label=\"=, +\"];\n");
        archivo.write("INICIAL -> DELIMITADOR [label=\"{ } ( ) , ;\"];\n");
        archivo.write("INICIAL -> ERROR [label=\"otro\"];\n\n");

        
        //Transiciones de DIRECTIVAS
        archivo.write("DIRECTIVA -> DIRECTIVA"+ "[label=\"letra\"]; \n\n");
        //Transiciones de IDENTIFICADORES
        archivo.write("IDENTIFICADOR -> IDENTIFICADOR"+ "[label=\"letra, digito, _\"]; \n\n");
        //Transiciones de NUMEROS
        archivo.write("ENTERO -> ENTERO "+ "[label=\".\"]; \n\n");
        archivo.write("PUNTO_DECIMAL -> DECIMAL"+ "[label=\"digito\"]; \n\n");

        
    
    }catch (IOException e){
    
    }
    }
    
}
