/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pactica1ji_promptzal;

import promptZal.Token;
import promptZal.analizadorLexico;
import promptZal.errorLexico;

/**
 *
 * @author jesua
 */
public class Pactica1JI_PromptZal {
    
    public static void main(String[] args) {

        // Texto de prueba para nuestro analizador
        String texto = "\"Hola mundo\" 10 80 0.7 3.1416 \"HOLA";

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
    }
}
