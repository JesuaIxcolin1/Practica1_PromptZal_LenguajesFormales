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

    public analizadorLexico(String entrada) {
        this.entrada = entrada;
        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.numeroToken = 1;

        this.listaTokens = new ArrayList<>();
        this.listaErrores = new ArrayList<>();
    }

    public void analizador() {

        while (posicion < entrada.length()) {

            char actual = entrada.charAt(posicion);

            System.out.println(
                    "Caracter: [" + actual + "]"
                    + " | Fila: " + fila
                    + " | Columna: " + columna
            );
            
            //*********************ESPACIOS Y TABULACIONES************************

            if (actual == ' ' || actual == '\t') {

                posicion++;
                columna++;
                
                
            //*********************SALTOS DE LINEA*****************************
            } else if (actual == '\n') {

                posicion++;
                fila++;
                columna = 1;
                
                
            //*******************COMENTARIOS********************
            }else if(actual == '/' && posicion+1 < entrada.length() && entrada.charAt(posicion+1)== '/'){
                
                // Avanza dos caracteres por (//)
                posicion+=2;
                columna+=2;
                
                while(posicion < entrada.length() && entrada.charAt(posicion) != '\n'){
                    posicion++;
                    columna++;
                
                }
                
                
            //*********************COMENTARIOS DE BLOQUE*********************    
            }else if(actual == '/' && posicion +1 < entrada.length() && entrada.charAt(posicion +1 )=='*'){
                
                //Avanza dos caracteres por (/*)
                posicion +=2;
                columna +=2;
                
                while(posicion < entrada.length()){
                   if(entrada.charAt(posicion)== '*' && posicion +1 < entrada.length() && entrada.charAt(posicion +1 )== '/'){
                       
                       posicion +=2;
                       columna+=2;
                       break;
                   } 
                   
                   if(entrada.charAt(posicion) == '\n'){
                       posicion++;
                       fila++;
                       columna = 1;
                   
                   }else {
                       posicion++;
                       columna++;
                   }
                    
                }
            
            
            
            //*************************DIRECTIVAS*************************
            }else if (actual == '@') {

                int columnaInicio = columna;

                StringBuilder lexema = new StringBuilder();
                lexema.append(actual);

                posicion++;
                columna++;

                while (posicion < entrada.length()
                        && Character.isLetter(entrada.charAt(posicion))) {

                    lexema.append(entrada.charAt(posicion));

                    posicion++;
                    columna++;
                }

                String palabra = lexema.toString();

                tipoToken tipo = identificarDirectiva(palabra);

                if (tipo != null) {

                    Token token = new Token(
                            numeroToken,
                            palabra,
                            tipo,
                            fila,
                            columnaInicio
                    );

                    listaTokens.add(token);
                    numeroToken++;

                } else {
                    errorLexico error = new errorLexico(
                            palabra,
                            "Directiva no reconocida",
                            fila,
                            columnaInicio
                    );

                    listaErrores.add(error);
                }
                
                
                //**************CADENAS*****************
            } else if(actual == '"' ){
                int filaInicio = fila;
                int columnaInicio = columna;
                StringBuilder lexema = new StringBuilder();
                
                lexema.append(actual);
                
                posicion++;
                columna++;
                boolean cerrada = false;
               while (posicion < entrada.length()){
                   char caracter = entrada.charAt(posicion);
                   
                   if (caracter == '"'){
                       lexema.append(caracter);
                       
                       posicion++;
                       columna++;
                       cerrada = true;
                       break;
                   }
                   if(caracter == '\n'){
                       break;
                   }
                   lexema.append(caracter);
                   posicion++;
                   columna++;
            }
               if (cerrada){
                   Token token = new Token(numeroToken, lexema.toString(), tipoToken.CADENA
                   , filaInicio,columnaInicio);
                   listaTokens.add(token);
                   numeroToken++;
               }else{
                   errorLexico error = new errorLexico(lexema.toString(), "Cadena sin cerrar",
                   filaInicio, columnaInicio);
                   listaErrores.add(error);
               }
            
            }
            
            //****************NUMEROS*******************
            else if(Character.isDigit(actual)){
                int columnaInicio = columna;
                StringBuilder lexema = new StringBuilder();
                boolean tieneDecimal = false;
                
                while(posicion < entrada.length()){
                    char caracter = entrada.charAt(posicion);
                    
                    if(Character.isDigit(caracter)){
                        lexema.append(caracter);
                        posicion++;
                        columna++;
                    }else if(caracter == '.' && !tieneDecimal){
                        tieneDecimal = true;
                        lexema.append(caracter);
                        posicion++;
                        columna++;
                    }else{
                        break;
                    }
                }
                String numero = lexema.toString();
                
                if(tieneDecimal){
                    Token token = new Token(numeroToken, numero, tipoToken.DECIMAL, fila, columnaInicio);
                    listaTokens.add(token);
                    numeroToken++;
                }else{
                    Token token = new Token(numeroToken, numero, tipoToken.ENTERO, fila, columnaInicio);
                    listaTokens.add(token);
                    numeroToken++;
                
                }
            
            }
            
            
            // ***********IDENTIFICADORES Y PALABRAS RESERVADAS**************
            else if (Character.isLetter(actual) || actual == '_') {

                // Guarda la columna donde comienza el token
                int columnaInicio = columna;

                StringBuilder lexema = new StringBuilder();

                while (posicion < entrada.length()
                        && (Character.isLetterOrDigit(entrada.charAt(posicion))
                        || entrada.charAt(posicion) == '_')) {

                    // Agrega el carácter al lexema
                    lexema.append(entrada.charAt(posicion));

                    posicion++;
                    columna++;
                }

                // Convierte el lexema a String
                String palabra = lexema.toString();

                // Determina que tipo de token es
                tipoToken tipo = identificarPalabra(palabra);

                // Crea el token
                Token token = new Token(
                        numeroToken,
                        palabra,
                        tipo,
                        fila,
                        columnaInicio
                );

                listaTokens.add(token);
                numeroToken++;
                
                // **************CONECTOR************************
            }else if (actual == '-' && posicion+1 < entrada.length()
                    && entrada.charAt(posicion+1) == '>'){
                
                int columnaInicio = columna;
                Token token = new Token(numeroToken,"->",tipoToken.CONECTOR,fila,columnaInicio);
                listaTokens.add(token);
                numeroToken++;
                
                //Avanza dos posiciones por que el conector posee dos caracteres
                posicion+=2;
                columna+=2;
               
            // ******************OPERADORES***********************    
            }else if(actual == '='  ||  actual == '+'){
                int columnaInicio = columna;
                Token token = new Token(numeroToken, String.valueOf(actual), tipoToken.OPERADOR,
                fila, columnaInicio);
                
                listaTokens.add(token);
                numeroToken++;
                posicion++;
                columna++;
        
                    
            //*********************DELIMITADORES************************        
            }else if(actual == '{'|| actual == '}'|| actual == '('|| actual == ')'|| actual == ','){
                int columnaInicio = columna;
                Token token = new Token(numeroToken, String.valueOf(actual), tipoToken.DELIMITADOR
                , fila, columnaInicio);
            
                listaTokens.add(token);
                numeroToken++;
                posicion++;
                columna++;
            

            
            //*************CARACTER NO RECONOCIDO*****************
            }else {

                errorLexico error = new errorLexico(
                        String.valueOf(actual),
                        "Caracter no reconocido",
                        fila,
                        columna
                );

                listaErrores.add(error);

                posicion++;
                columna++;
            }
        }
    }
    

    private tipoToken identificarDirectiva(String lexema) {

        if (lexema.equals("@modelo")
                || lexema.equals("@rol")
                || lexema.equals("@formato")) {

            return tipoToken.DIRECTIVA;
        }

        return null;
    }

    private tipoToken identificarPalabra(String lexema) {

        if (lexema.equals("AGENTE")
                || lexema.equals("contexto")
                || lexema.equals("variable")
                || lexema.equals("EJECUTAR")
                || lexema.equals("EXPORTAR")) {

            return tipoToken.PALABRA_RESERVADA;
        }

        if (lexema.equals("PREGUNTAR")
                || lexema.equals("GENERAR")
                || lexema.equals("RESUMIR")
                || lexema.equals("ANALIZAR")
                || lexema.equals("TRADUCIR")
                || lexema.equals("CLASIFICAR")
                || lexema.equals("EXTRAER")) {

            return tipoToken.COMANDO_IA;
        }

        if (lexema.equals("SOBRE")
                || lexema.equals("DESDE")
                || lexema.equals("EN")
                || lexema.equals("COMO")) {

            return tipoToken.CONECTOR;
        }

        if (lexema.equals("CARGAR")) {

            return tipoToken.FUNCION;
        }

        return tipoToken.IDENTIFICADOR;
    }

    
    public ArrayList<Token> getlistaTokens() {
        return listaTokens;
    }

    public ArrayList<errorLexico> getlistaErrores() {
        return listaErrores;
    }
}
