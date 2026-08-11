/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pactica1ji_promptzal;

import promptZal.analizadorLexico;

/**
 *
 * @author jesua
 */
public class Pactica1JI_PromptZal {
    
    public static void main(String[] args) {

        // Texto de prueba para nuestro analizador
        String texto = "ABC\nDEF\nGHI";

        // Creamos el analizador léxico
        analizadorLexico analizador = new analizadorLexico(texto);

        // Iniciamos el análisis
        analizador.analizador();
    }
}
