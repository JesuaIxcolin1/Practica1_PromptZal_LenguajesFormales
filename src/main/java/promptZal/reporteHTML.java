/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package promptZal;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jesua
 */
public class reporteHTML {
    
    
    //*************REPORTE DE TOKENS********************
    public void generarReporteTokens(ArrayList<Token> listaTokens){
        
        try{
            FileWriter archivo = new FileWriter("reporteTokens.html");
            
            // Estructura del reporte HTML
            archivo.write("<!DOCTYPE html>\n");
            archivo.write("<html>\n");
            archivo.write("<head>\n");
            archivo.write("<meta charset = \"UTF-8\">\n");
            archivo.write("<title>REPORTE DE TOKENS</title>\n");
            
            //Estilos de la tabla
           archivo.write("<style>\n");
           archivo.write("body { font-family: Arial; margin: 40px; background-color: #C9C9C9 }\n");
           archivo.write("h1 { text-align: center; }\n");
           archivo.write("table { border-collapse: collapse; width: 100%; }\n");
           archivo.write("th, td { border: 3px solid #333; padding: 8px; text-align: center; }\n");
           archivo.write("th { background-color: black; color: white; font-weight: bold; }\n");
           archivo.write("</style>\n");
            
            archivo.write("</head>\n");
            archivo.write("<body>\n");
            
            archivo.write("<h1>REPORTE DE TOKENS</h1>\n");
            
            archivo.write("<table>\n");
            archivo.write("<th>Numero</th>\n");
            archivo.write("<th>Lexema</th>\n");
            archivo.write("<th>Tipo</th>\n");
            archivo.write("<th>Fila</th>\n");
            archivo.write("<th>Columna</th>\n");
            archivo.write("</tr>\n");
            
            //Recorre la lista de Tokens
            for(Token token : listaTokens){
                archivo.write("<tr>\n");
                archivo.write("<td>"+token.getNumero()+ "</td>\n");
                archivo.write("<td>"+token.getLexema()+ "</td>\n");
                archivo.write("<td>"+token.getTipo()+ "</td>\n");
                archivo.write("<td>"+token.getFila()+ "</td>\n");
                archivo.write("<td>"+token.getColumna()+ "</td>\n");
                archivo.write("</tr>\n");
            }
            
            archivo.write("</table>\n");
            archivo.write("</body>\n");
            archivo.write("</html>\n");
            
            archivo.close();
            System.out.println("Reporte de Tokens generado con exito");

        
        }catch(IOException e){
            System.out.println("Error al generar el reporte de Tokens");
            System.out.println(e.getMessage());
        
        
        }
    
    }
    
    //*******************REPORTE DE ERRORES LEXICOS*************************
    public void generarReporteErrores(ArrayList<errorLexico> listaErrores){
        
        try{
            FileWriter archivo = new FileWriter("reporteDeErrores.html");
            
            archivo.write("<!DOCTYPE html>\n");
            archivo.write("<html>\n");
            archivo.write("<head>\n");
            archivo.write("<meta charset=\"UTF-8\">\n");
            archivo.write("<title>REPORTE DE ERRORES</title>\n");
            
           archivo.write("<style>\n");
           archivo.write("body { font-family: Arial; margin: 40px; background-color: #C9C9C9 }\n");
           archivo.write("h1 { text-align: center; }\n");
           archivo.write("table { border-collapse: collapse; width: 100%; }\n");
           archivo.write("th, td { border: 3px solid #333; padding: 8px; text-align: center; }\n");
           archivo.write("th { background-color: black; color: white; font-weight: bold;}\n");
           archivo.write("td: nth-child(1) {"+"text-align: left;"+"}\n");
           archivo.write("</style>\n");;
            
            archivo.write("</head>\n");
            archivo.write("<body>\n");

            archivo.write("<h1>REPORTE DE ERRORES</h1>\n");
            
            // Si no hay errores
            if(listaErrores.isEmpty()){
                archivo.write("<p>No se encontraron Errores Lexicos</p>\n");
                
            }else{
                archivo.write("<table>\n");

                archivo.write("<tr>\n");
                archivo.write("<th>Lexema</th>\n");
                archivo.write("<th>Tipo de Error</th>\n");
                archivo.write("<th>Fila</th>\n");
                archivo.write("<th>Columna</th>\n");
                archivo.write("</tr>\n");
                
                //Recorre la lista de errores
                for(errorLexico error : listaErrores){
                    archivo.write("<tr>\n");
                    archivo.write("<td>"+error.getLexema()+"</td>\n");
                    archivo.write("<td>"+error.getTipoError()+"</td>\n");
                    archivo.write("<td>"+error.getFila()+"</td>\n");
                    archivo.write("<td>"+error.getColumna()+"</td>\n");
                    archivo.write("</tr>\n");
                }
                archivo.write("<table>\n");
            
            }
            archivo.write("</body>\n");
            archivo.write("</html>\n");
            
            archivo.close();
            
            System.out.println("Reporte de Errores generado con exito");
        
        }catch(IOException e){
            
            System.out.println("Error al generar el reporte de Errores");
            System.out.println(e.getMessage());
        
        }
        
    
    }
    
}
