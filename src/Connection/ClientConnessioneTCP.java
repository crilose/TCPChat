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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Monica Ciuchetti
 */
public class ClientConnessioneTCP {
    
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
    String msg = "";
    DataOutputStream dOut;
    DataInputStream dIn;
    
    
    public void startConnection()
    {
        
        
        try {
            //Istanzio lo scanner
            input = new Scanner(System.in);
            //Realizzo la connessione vera e propria tramite ip e porta
            connection = new Socket(serverAddress, port);
            System.out.println("Connessione aperta");
            //OutputStream per l'invio dei dati
            dOut = new DataOutputStream(connection.getOutputStream());
            //InputStream per la ricezione dei dati
            dIn = new DataInputStream(connection.getInputStream());
            
            
        }catch(ConnectException e){
            System.err.println("Server non disponibile!");
        }
        catch(UnknownHostException e1){
            System.err.println("Errore DNS!");
            
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
        
    }
    
    public void setUsername()
    {
            try {
                System.out.println("Inserisci il tuo username: ");
                username = input.nextLine();
                dOut.writeUTF(username);
                dOut.flush();
                othername = dIn.readUTF();
                
            } catch (IOException ex) {
                System.err.println(ex);
            }
            
            
        
    }
       
    public void communicate()
    {
        //Istanzio anche il thread listener e lo avvio
        listen = new Listener(dIn);
        listen.start();
        //Mentre la connessione è attiva prendo i messaggi che scrivo e li invio
        while(connection.isConnected())
        {     
            try {
                Messaggio msg = new Messaggio(username,(char)27 + "[34m",this);
                dOut.writeUTF(msg.scrivi() + "\u001B[0m");
                dOut.flush();
                
                
            } catch (IOException ex) {
                System.err.println(ex);
            }
                
        }
    }
    
    public void closeConnection()
    {
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
