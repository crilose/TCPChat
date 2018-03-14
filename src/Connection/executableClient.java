/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;


/**
 *
 * La classe eseguibile del client.
 * 
 * Questa classe istanzia un oggetto client, avvia la sua connessione, imposta il suo username
 * e si mette in perenne stato di comunicazione mediante un ciclo while.
 * @author Cristiano Ceccarelli
 */

public class executableClient {
public static void main(String[] args) {
        
        ClientConnessioneTCP client = new ClientConnessioneTCP();
        client.startConnection();
        client.setUsername();
        while(true){
            client.communicate();
        }
      }
    
}
