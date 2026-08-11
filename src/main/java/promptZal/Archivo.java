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
        
        //Para almacenar el contenido del archivo
        StringBuilder contenido = new StringBuilder();
        
        try{
            //Se abre el archivo para leer el contenido
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            String linea;
            
            //Se lee el archivo linea por linea completo, hasta el final
            while((linea = lector.readLine()) != null){
                contenido.append(linea);
                contenido.append("\n");
            }
            lector.close();
        }catch (IOException e){
            System.out.println("Error al leer el archivo.");
            System.out.println(e.getMessage());
        
        }
        // Se devuelve todo el contenido del archivo en String
        return contenido.toString();
    
    }
    
}
