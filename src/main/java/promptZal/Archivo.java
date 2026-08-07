/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package promptZal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author jesua
 */
public class Archivo {
    //Ruta y contenido completo del archivo (.pz)
    
    public String leerArchivo(String ruta){
        StringBuilder contenido = new StringBuilder();
        
        try{
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            String linea;
            
            while((linea = lector.readLine()) != null){
                contenido.append(linea);
                contenido.append("\n");
            }
            lector.close();
        }catch (IOException e){
            System.out.println("Error al leer el archivo.");
            System.out.println(e.getMessage());
        
        }
        return contenido.toString();
    
    }
    
}
