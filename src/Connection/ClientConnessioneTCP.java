/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.DataInputStream;
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
        //Thread per l'ascolto dei messaggi
        Listener listen;
        
        String username;
        String othername;

        //apertura della connessione al server sulla porta specificata
        try{
            //inizializzo il messaggio che invieremo
            String msg = "";
            //Istanzio lo scanner
            input = new Scanner(System.in);
            //Realizzo la connessione vera e propria tramite ip e porta
            connection = new Socket(serverAddress, port);
            System.out.println("Connessione aperta");
            
            //OutputStream per l'invio dei dati
            DataOutputStream dOut = new DataOutputStream(connection.getOutputStream());
            //InputStream per la ricezione dei dati
            DataInputStream dIn = new DataInputStream(connection.getInputStream());
            //Istanzio anche il thread listener e lo avvio
            System.out.println("Inserisci il tuo username: ");
            username = input.nextLine();
            dOut.writeUTF(username);
            dOut.flush();
            othername = dIn.readUTF();
            listen = new Listener(dIn,othername + ": ");
            listen.start();
            //Mentre la connessione è attiva prendo i messaggi che scrivo e li invio
            while(connection.isConnected())
            {
                msg = input.nextLine();
                dOut.writeUTF(msg);
                dOut.flush();
            }
            
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
                    System.out.println("Connessione chiusa lato server!");
                }
            }
            catch(IOException e){
                System.err.println("Errore nella chiusura della connessione!");
            }
        }
    }
}
