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
    private estadoAFD estado;
    private ArrayList<Token> listaTokens;
    private ArrayList<errorLexico> listaErrores;

    public analizadorLexico(String entrada) {
        this.entrada = entrada;
        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.numeroToken = 1;
        
        this.estado = estadoAFD.INICIAL;

        this.listaTokens = new ArrayList<>();
        this.listaErrores = new ArrayList<>();
    }

    public void analizador() {

        while (posicion < entrada.length()) {

            char actual = entrada.charAt(posicion);

            
            //*********************ESPACIOS Y TABULACIONES************************
            //Se ignoran pero se actualiza la posicion
            if (actual == ' ' || actual == '\t') {

                posicion++;
                columna++;
                
                
            //*********************SALTOS DE LINEA*****************************
            // Aumenta la fila y la columna vuelve a 1
            } else if (actual == '\n') {

                posicion++;
                fila++;
                columna = 1;
                
                
            //*******************COMENTARIOS********************
            // Ignora los comentarios
            }else if(actual == '/' && posicion+1 < entrada.length() && entrada.charAt(posicion+1)== '/'){
                
                estado = estadoAFD.COMENTARIO_LINEA;
                // Avanza dos caracteres por (//)
                posicion+=2;
                columna+=2;
                
                while(posicion < entrada.length() && entrada.charAt(posicion) != '\n'){
                    posicion++;
                    columna++;
                
                }
            // Regresa al estado inicial
            estado = estadoAFD.INICIAL;
                
                
            //*********************COMENTARIOS DE BLOQUE*********************    
            }else if(actual == '/' && posicion +1 < entrada.length() && entrada.charAt(posicion +1 )=='*'){
                
                estado = estadoAFD.COMENTARIO_BLOQUE;
                
                int filaInicio = fila;
                int columnaInicio = columna;
                
                StringBuilder lexema = new StringBuilder();
                lexema.append('/');
                lexema.append('*');
                
                //Avanza dos caracteres por (/*)
                posicion +=2;
                columna +=2;
                boolean comentarioCerrado = false;
                
                while(posicion < entrada.length()){
                    char caracter = entrada.charAt(posicion);
                    
                    if(caracter == '*'){
                        estado = estadoAFD.ASTERISCO_COMENTARIO;
                        
                        lexema.append(caracter);
                        posicion++;
                        columna++;
                        
                        if(posicion < entrada.length() && entrada.charAt(posicion) == '/'){
                            lexema.append('/');
                            
                            posicion++;
                            columna++;
                            comentarioCerrado = true;
                            break;
                        }else{
                            estado = estadoAFD.COMENTARIO_BLOQUE;
                        }
                    }else if( caracter == '\n'){
                        
                        lexema.append(caracter);
                        posicion++;
                        fila++;
                        columna = 1;
                    }else{
                        
                        lexema.append(caracter);
                        posicion++;
                        columna++;
                    }
                    
                }
                
                if(!comentarioCerrado){
                    estado = estadoAFD.ERROR;
                    
                    errorLexico error = new errorLexico("/*", "Comentario de Bloque sin cerrar", filaInicio, columnaInicio);
                    listaErrores.add(error);
                }
                
                estado = estadoAFD.INICIAL;
            
            
            
            //*************************DIRECTIVAS*************************
            }else if (actual == '@') {
                
                estado = estadoAFD.DIRECTIVA;

                int columnaInicio = columna;
                //Construye el lexema carter por caracter
                StringBuilder lexema = new StringBuilder();
                lexema.append(actual);

                posicion++;
                columna++;
                //Se leen las letas que forman la directiva
                while (posicion < entrada.length()
                        && Character.isLetter(entrada.charAt(posicion))) {

                    lexema.append(entrada.charAt(posicion));

                    posicion++;
                    columna++;
                }

                String palabra = lexema.toString();
                //Verifica si la directiva pertenece al lenguaje
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
                
                estado = estadoAFD.INICIAL;
                
                
                //**************CADENAS*****************
            } else if(actual == '"' ){
                
                estado = estadoAFD.CADENA;
                
                int filaInicio = fila;
                int columnaInicio = columna;
                StringBuilder lexema = new StringBuilder();
                
                lexema.append(actual);
                
                posicion++;
                columna++;
                boolean cerrada = false;
                
                //Lee el la cadena hasta encontrar la comilla de cierre
               while (posicion < entrada.length()){
                   char caracter = entrada.charAt(posicion);
                   
                   if (caracter == '"'){
                       lexema.append(caracter);
                       
                       posicion++;
                       columna++;
                       cerrada = true;
                       break;
                   }
                   // Si hay salto de line, no es una cadena
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
               
               estado = estadoAFD.INICIAL;
            
            }
            
            //****************NUMEROS******************
            else if (Character.isDigit(actual)) {
                
            // Todo numero empieza como entero 
            estado = estadoAFD.ENTERO;
            int columnaInicio = columna;

          StringBuilder lexema = new StringBuilder();
         
             boolean tienePunto = false;
             boolean decimalValido = false;
         
             while (posicion < entrada.length()) {
                char caracter = entrada.charAt(posicion);
       

             // Estado ENTERO
             if (estado == estadoAFD.ENTERO) {
             
              if (Character.isDigit(caracter)) {
                    lexema.append(caracter);
                 
                     posicion++;
                    columna++;
             
                 } else if (caracter == '.') {

                  // Se encontro el punto,
                  // pero aun no sabemos si el decimal es valido
                  estado = estadoAFD.PUNTO_DECIMAL;
                  tienePunto = true;
               
                 lexema.append(caracter);
               
                  posicion++;
                 columna++;
             
                 } else {
                     break;
              }
              
        // Estado PUNTO_DECIMAL
             } else if (estado == estadoAFD.PUNTO_DECIMAL) {
             
              if (Character.isDigit(caracter)) {

              // Ya existe al menos un numero despues del punto
                 estado = estadoAFD.DECIMAL;
                 decimalValido = true;
                 lexema.append(caracter);
             
                 posicion++;
                 columna++;
             
                 } else {

                // Si despues del punto no viene un numero,
                // el decimal es incorrecto
                break;
            }

         // Estado DECIMAL
             } else if (estado == estadoAFD.DECIMAL) {
            
                   if (Character.isDigit(caracter)) {
                   lexema.append(caracter);
                   posicion++;
                   columna++;
             
                 } else {
                    break;
              }   
          }
        }
             String numero = lexema.toString();

            // Si nunca aparecio punto, es entero
            if (!tienePunto) {
                Token token = new Token(
                numeroToken,
                numero,
                tipoToken.ENTERO,
                fila,
                columnaInicio );

                listaTokens.add(token);
                numeroToken++;

             // Si hubo punto y despues hubo digitos, es decimal
            } else if (decimalValido) {

                Token token = new Token(
                numeroToken,
                numero,
                tipoToken.DECIMAL,
                fila,
                columnaInicio );

                listaTokens.add(token);
                 numeroToken++;

                 // Si termino en PUNTO_DECIMAL, hay error
              } else {
                
                errorLexico error = new errorLexico(numero,"Numero decimal invalido",fila,columnaInicio );
                listaErrores.add(error);
            }
            estado = estadoAFD.INICIAL;
            
            }
            
            
            // ***********IDENTIFICADORES Y PALABRAS RESERVADAS**************
            else if (Character.isLetter(actual) || actual == '_') {
                
                estado = estadoAFD.IDENTIFICADOR;

                // Guarda la columna donde comienza el token
                int columnaInicio = columna;

                StringBuilder lexema = new StringBuilder();

                while (posicion < entrada.length()
                        && (Character.isLetterOrDigit(entrada.charAt(posicion))
                        || entrada.charAt(posicion) == '_')) {
                    lexema.append(entrada.charAt(posicion));

                    posicion++;
                    columna++;
                }

                String palabra = lexema.toString();
                
                // Reconoce el tipo de Token
                tipoToken tipo = identificarPalabra(palabra);

                Token token = new Token(
                        numeroToken,
                        palabra,
                        tipo,
                        fila,
                        columnaInicio
                );

                listaTokens.add(token);
                numeroToken++;
                
                estado = estadoAFD.INICIAL;
                
                
                // **************CONECTOR************************
            }else if (actual == '-' && posicion+1 < entrada.length()
                    && entrada.charAt(posicion+1) == '>'){
                
                estado = estadoAFD.CONECTOR;
                
                int columnaInicio = columna;
                Token token = new Token(numeroToken,"->",tipoToken.CONECTOR,fila,columnaInicio);
                listaTokens.add(token);
                numeroToken++;
                
                //Avanza dos posiciones por que el conector posee dos caracteres
                posicion+=2;
                columna+=2;
                
                estado = estadoAFD.INICIAL;
               
            // ******************OPERADORES***********************    
            }else if(actual == '='  ||  actual == '+'){
                
                estado = estadoAFD.OPERADOR;
                
                int columnaInicio = columna;
                Token token = new Token(numeroToken, String.valueOf(actual), tipoToken.OPERADOR,
                fila, columnaInicio);
                
                listaTokens.add(token);
                numeroToken++;
                posicion++;
                columna++;
                
                estado = estadoAFD.INICIAL;
        
                    
            //*********************DELIMITADORES************************        
            }else if(actual == '{'|| actual == '}'|| actual == '('|| actual == ')'|| actual == ',' || actual == ';'){
                
                estado = estadoAFD.DELIMITADOR;
                int columnaInicio = columna;
                Token token = new Token(numeroToken, String.valueOf(actual), tipoToken.DELIMITADOR
                , fila, columnaInicio);
            
                listaTokens.add(token);
                numeroToken++;
                posicion++;
                columna++;
                
                estado = estadoAFD.INICIAL;
            

            
            //*************CARACTER NO RECONOCIDO*****************
            }else {
                
                estado = estadoAFD.ERROR;

                errorLexico error = new errorLexico(
                        String.valueOf(actual),
                        "Caracter no reconocido",
                        fila,
                        columna
                );

                listaErrores.add(error);

                posicion++;
                columna++;
                
                estado = estadoAFD.INICIAL;
            }
        }
    }
    
    // Se comprueba si el lexema es una directiva valida
    private tipoToken identificarDirectiva(String lexema) {

        if (lexema.equals("@modelo")
                || lexema.equals("@rol")
                || lexema.equals("@formato")) {

            return tipoToken.DIRECTIVA;
        }

        return null;
    }
    
   
    private tipoToken identificarPalabra(String lexema) {
        
        // PALABRAS RESERVADAS
        if (lexema.equals("AGENTE")
                || lexema.equals("contexto")
                || lexema.equals("variable")
                || lexema.equals("EJECUTAR")
                || lexema.equals("EXPORTAR")
                || lexema.equals("CODIFICAR")) {

            return tipoToken.PALABRA_RESERVADA;
        }
        
        //COMANDOS DE IA
        if (lexema.equals("PREGUNTAR")
                || lexema.equals("GENERAR")
                || lexema.equals("RESUMIR")
                || lexema.equals("ANALIZAR")
                || lexema.equals("TRADUCIR")
                || lexema.equals("CLASIFICAR")
                || lexema.equals("EXTRAER")) {

            return tipoToken.COMANDO_IA;
        }
        
        //CONECTORES
        if (lexema.equals("SOBRE")
                || lexema.equals("DESDE")
                || lexema.equals("EN")
                || lexema.equals("COMO")) {

            return tipoToken.CONECTOR;
        }
        
        //COMANDO DE IA
        if (lexema.equals("CARGAR")) {

            return tipoToken.FUNCION;
        }
        
        //En caso de no reconocer los anteriores, es un identificador
        return tipoToken.IDENTIFICADOR;
    }

    
    public ArrayList<Token> getlistaTokens() {
        return listaTokens;
    }

    public ArrayList<errorLexico> getlistaErrores() {
        return listaErrores;
    }
}
