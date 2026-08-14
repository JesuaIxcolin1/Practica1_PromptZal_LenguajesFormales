/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pactica1ji_promptzal;

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
                System.out.println("----------PROMPTZAL----------");
        
        System.out.println("Ingrese la ruta del archivo .pz: ");
        
        String ruta = scanner.nextLine();
        if (!ruta.endsWith(".pz")) {

            System.out.println(
                    "Error: el archivo debe tener extension .pz"
            );

            scanner.close();
            return;
        }
        Archivo archivo = new Archivo();
        
        // Se lee todo el contenido del archivo .pz
        String texto = archivo.leerArchivo(ruta);
        
        System.out.println("-----------Contenido del Archivo----------");
        System.out.println(texto);

        // Creamos el analizador léxico
        analizadorLexico analizador = new analizadorLexico(texto);

        // Iniciamos el análisis
        analizador.analizador();
        
        System.out.println("\n========== TOKENS ==========");
        for (Token token : analizador.getlistaTokens()) {
            System.out.println(token);
        
        }
        
        System.out.println("\n========== ERRORES ==========");
        for (errorLexico error : analizador.getlistaErrores()) {
           System.out.println(error);
        }
        
        reporteHTML reporte = new reporteHTML();
        
        reporte.generarReporteTokens(analizador.getlistaTokens());
        reporte.generarReporteErrores(analizador.getlistaErrores());
        scanner.close();
    }
}
