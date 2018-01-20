/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Monica Ciuchetti
 */
public class ServerConnessioneTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // porta del server maggiore di 1024 
        int port=2000;
        //oggetto ServerSocket necessario per accettare richieste dal client
        ServerSocket sSocket = null;
        //oggetto da usare per realizzare la connessione TCP
        Socket connection;
        //Thread per l'ascolto dei messaggi
        Listener listen;
        //Scanner per l'input di una stringa
        Scanner input;

        while(true){
            try{
                String msg = "";
                input = new Scanner(System.in);
                // il server si mette in ascolto sulla porta voluta
                sSocket = new ServerSocket(port);
                System.out.println("In attesa di connessioni!");
                //si è stabilita la connessione
                connection = sSocket.accept();
                //Stampo le informazioni sulla connessione
                System.out.println("Connessione stabilita!");
                System.out.println("Socket server: " + connection.getLocalSocketAddress());
                System.out.println("Socket client: " + connection.getRemoteSocketAddress());
                //OutputStream per l'invio dei dati
                DataOutputStream dOut = new DataOutputStream(connection.getOutputStream());
                //InputStream per la ricezione dei dati
                DataInputStream dIn = new DataInputStream(connection.getInputStream());
                //Istanzio anche il thread listener e lo avvio
                listen = new Listener(dIn,"Client: ");
                listen.start();
                //Mentre la connessione è attiva prendo i messaggi che scrivo e li invio
                while(connection.isConnected())
                {
                    msg = input.nextLine();
                    dOut.writeUTF(msg);
                    dOut.flush();
                }
            }
               catch(IOException e){
                   System.err.println(e);
            }
            
            //chiusura della connessione con il client
            try {
                if (sSocket!=null) sSocket.close();
            } catch (IOException ex) {
                System.err.println("Errore nella chiusura della connessione!");
            }
            System.out.println("Connessione chiusa lato client!");
        }
      }
}
