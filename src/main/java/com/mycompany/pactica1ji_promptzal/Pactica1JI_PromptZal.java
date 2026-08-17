/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pactica1ji_promptzal;

import java.io.File;
import java.util.Scanner;
import promptZal.Archivo;
import promptZal.Token;
import promptZal.analizadorLexico;
import promptZal.errorLexico;
import promptZal.reporteHTML;

/**
 *
 * @author jesua
 */
public class Pactica1JI_PromptZal {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("=========================================================================================");
        System.out.println("=========================================PROMPTZAL=======================================");
        System.out.println("=========================================================================================");
        System.out.println("\n");
        
        String ruta;
        
        while(true){
            System.out.println("Ingrese la ruta del archivo .pz:");
            ruta = scanner.nextLine();
            
            if(!ruta.endsWith(".pz")){
                System.out.println("Error: el archivo debe contener extension .pz");
                System.out.println("Intente nuevamente porfavor \n");
                continue;
            }
            
            File archivoExiste = new File(ruta);
            if(!archivoExiste.exists()){
                System.out.println("Error: el archivo ingresado no existe");
                System.out.println("Intente nuevamente porfavor \n");
                continue;
            }
            break;
        
        }
        
        Archivo archivo = new Archivo();
        
        // Se lee todo el contenido del archivo .pz
        String texto = archivo.leerArchivo(ruta);
        
        System.out.println("---------------------------------------CONTENIDO DEL ARCHIVO-------------------------------------");
        System.out.println(texto);

        // Creamos el analizador léxico
        analizadorLexico analizador = new analizadorLexico(texto);

        // Iniciamos el análisis
        analizador.analizador();
        
        System.out.println("\n------------------------------------------lISTA DE TOKENS---------------------------------------");
        System.out.printf("%-8s %-40s %-25s %-8s %-8s%n","NUMERO","LEXEMA","TIPO","FILA","COLUMNA");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for (Token token : analizador.getlistaTokens()) {
            System.out.printf("%-8d %-40s %-25s %-8d %-8d%n",token.getNumero(),token.getLexema(), token.getTipo(), token.getFila(),
            token.getColumna());
        
        }
        System.out.println("\n");
        System.out.println("\n-------------------------------------------LISTA DE ERRORES-------------------------------------");
        if(analizador.getlistaErrores().isEmpty()){
            System.out.println("NO SE ENCONTRARON ERRORES LEXICOS");
        }else{
            System.out.printf("%-30s %-30s %-8s %-8s%n", "LEXEMA", "TIPO DE ERROR", "FILA", "COLUMNA");
        
        System.out.println("--------------------------------------------------------------------------------------------------");
        for (errorLexico error : analizador.getlistaErrores()) {
           System.out.printf("%-30s %-30s %-8d %-8d%n", error.getLexema(), error.getTipoError(), error.getFila(), error.getColumna());
        }
    }
        System.out.println("\n");
        System.out.println("\n");
        reporteHTML reporte = new reporteHTML();
        
        reporte.generarReporteTokens(analizador.getlistaTokens());
        reporte.generarReporteErrores(analizador.getlistaErrores());
        scanner.close();
    }
}
