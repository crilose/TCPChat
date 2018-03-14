/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

/**
 *
 * La classe eseguibile del server.
 * 
 * Questa classe istanzia un oggetto server, avvia la sua connessione, imposta il suo username
 * e si mette in perenne stato di comunicazione mediante un ciclo while.
 * @author Cristiano Ceccarelli
 */
public class executableServer {
    
    
    
    public static void main(String[] args) {
        
        ServerConnessioneTCP server = new ServerConnessioneTCP();
        server.startConnection();
        server.setUsername();
        while(true){
            server.communicate();
        }
      }
}
