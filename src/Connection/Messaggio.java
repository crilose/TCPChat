/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.util.Scanner;

/**
 *
 * @author Cristiano
 */
public class Messaggio {
    
    String fromuser;
    String contenuto;
    String tipo;
    Scanner input;
    String colore;
    
    
    public Messaggio(String from, String tipo, String colore)
    {
        fromuser = from;
        this.tipo = tipo;
        input = new Scanner(System.in);
        this.colore = colore;
    }
    
    public String scrivi()
    {
        contenuto = input.nextLine();
        return (colore + fromuser + ": " + contenuto);
    }
    
}
