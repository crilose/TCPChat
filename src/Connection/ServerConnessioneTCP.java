/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import static Connection.ClientConnessioneTCP.FILE_SIZE;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristiano Ceccarelli
 */
public class ServerConnessioneTCP {
    
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
        DataOutputStream dOut;
        DataInputStream dIn;
        String username;
        String othername;
        String msg = "";
        FileInputStream filestream = null;
        BufferedInputStream filebuffer = null;
        BufferedOutputStream outfilebuffer = null;
        FileOutputStream recfilestream = null;
    
    
    public void startConnection()
    {
            try {
                // il server si mette in ascolto sulla porta voluta
                sSocket = new ServerSocket(port);
                System.out.println("In attesa di connessioni!");
                input = new Scanner(System.in);
                //si è stabilita la connessione
                connection = sSocket.accept();
                //OutputStream per l'invio dei dati
                dOut = new DataOutputStream(connection.getOutputStream());
                //InputStream per la ricezione dei dati
                dIn = new DataInputStream(connection.getInputStream());
                
                
                
            } catch (IOException ex) {
                System.err.println(ex);
            }
            
    }
    
    public void getInfo()
    {
        //Stampo le informazioni sulla connessione
                System.out.println("Connessione stabilita!");
                System.out.println("Socket server: " + connection.getLocalSocketAddress());
                System.out.println("Socket client: " + connection.getRemoteSocketAddress());
    }
    
    public void setUsername()
    {
        System.out.println("Inserisci il tuo username: ");
        username = input.nextLine();
        
    }
    
    public void communicate()
    {
        //Istanzio anche il thread listener e lo avvio
        listen = new Listener(dIn,this);
        listen.start();
        //Mentre la connessione è attiva prendo i messaggi che scrivo e li invio
        while(connection.isConnected())
        {     
            try {
                Messaggio msg = new Messaggio(username,(char)27 + "[35m",this);
                dOut.writeUTF(msg.scrivi() + "\u001B[0m");
                dOut.flush();
                
                
            } catch (IOException ex) {
                System.err.println(ex);
            }
                
        }
    }
    
    public void closeConnection()
    {
        //chiusura della connessione con il client
            try {
                if (sSocket!=null) sSocket.close();
                connection.close();
            } catch (IOException ex) {
                System.err.println("Errore nella chiusura della connessione!");
            }
            System.out.println("Connessione chiusa lato client!");
    }
    
    public void online()
    {
        listen.changeState(0); //0 per online
    }
    
    public void offline()
    {
        listen.changeState(1); //1 per offline
    }
    
    public void sendFile()
    {
         try{
            
        dOut.writeUTF("sendfile");
        System.out.println("Inserisci il nome del file: ");
        String filename = input.nextLine();
        File filetosend = new File (filename);
        byte [] bytearray  = new byte [(int)filetosend.length()];
        filestream = new FileInputStream(filetosend);
        filebuffer = new BufferedInputStream(filestream); //wrapping
        dOut.write(bytearray,0,bytearray.length);
        dOut.flush();
        System.out.println("Fatto!");
        } 
        catch (IOException ex) {
            //error
        }
    }
    
    public void receiveFile()
    {
        int bytesRead = 0;
        int current = 0;
        byte [] receivedbyte  = new byte [FILE_SIZE];
        try {
            File outputFile = new File("receivedfile");
            recfilestream = new FileOutputStream(outputFile);
            outfilebuffer = new BufferedOutputStream(recfilestream);
            bytesRead = dIn.read(receivedbyte,0,receivedbyte.length);
            current = bytesRead;
            do {
                bytesRead = dIn.read(receivedbyte, current, (receivedbyte.length-current));
                if(bytesRead >= 0) current += bytesRead;
                }
            while(bytesRead > -1);
            outfilebuffer.write(receivedbyte, 0 , current);
            outfilebuffer.flush();
            System.out.println("File ricevuto");
            
        } catch (FileNotFoundException ex) {
            //error
        }
        catch (IOException ex) {
            //error
        }  
      
    }
}
