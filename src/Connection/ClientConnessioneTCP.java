/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ConnectException;
import java.util.Scanner;

/**
 *
 * @author Monica Ciuchetti
 */
public class ClientConnessioneTCP {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //oggetto da usare per realizzare la connessione TCP
        Socket connection = null;
        //nome o IP del server
        String serverAddress = "localhost";
        //porta del server in ascolto
        int port = 2000;
        //Scanner per l'input di una stringa
        Scanner input;

        //apertura della connessione al server sulla porta specificata
        try{
            input = new Scanner(System.in);
            connection = new Socket(serverAddress, port);
            //OutputStream per l'invio dei dati
            DataOutputStream dOut = new DataOutputStream(connection.getOutputStream());
            System.out.println("Connessione aperta");
            System.out.println("Scrivi il tuo messaggio al server: ");
            String msg = input.nextLine();
            dOut.writeUTF(msg);
            dOut.flush();
            
        }
        catch(ConnectException e){
            System.err.println("Server non disponibile!");
        }
        catch(UnknownHostException e1){
            System.err.println("Errore DNS!");
        }

        catch(IOException e2){//
            System.err.println(e2);
            e2.printStackTrace();
        }

        //chiusura della connnessione
        finally{
                try {
            if (connection!=null)
                {
                    connection.close();
                    System.out.println("Connessione chiusa lato client!");
                }
            }
            catch(IOException e){
                System.err.println("Errore nella chiusura della connessione!");
            }
        }
    }
}
