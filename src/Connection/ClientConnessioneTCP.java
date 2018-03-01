/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    
    public final static int FILE_SIZE = Integer.MAX_VALUE; //max filesize
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
    FileInputStream filestream = null;
    BufferedInputStream filebuffer = null;
    BufferedOutputStream outfilebuffer = null;
    FileOutputStream recfilestream = null;
    
    
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
        System.out.println("Inserisci il tuo username: ");
        username = input.nextLine();
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
    
    public void online()
    {
        listen.changeState(0);
    }
    
    public void offline()
    {
        listen.changeState(1);
    }
    
    public void sendFile()
    {
        System.out.println("Inserisci il nome del file: ");
        String filename = input.nextLine();
        File filetosend = new File (filename);
        byte [] bytearray  = new byte [(int)filetosend.length()];
        try {
            filestream = new FileInputStream(filetosend);
        } catch (FileNotFoundException ex) {
            //error
        }
        filebuffer = new BufferedInputStream(filestream); //wrapping
        try {
            dOut.write(bytearray,0,bytearray.length);
            dOut.flush();
        } catch (IOException ex) {
            //error
        }
    }
    
    public void receiveFile()
    {
        int bytesRead = 0;
        int current = 0;
        byte [] receivedbyte  = new byte [FILE_SIZE];
        try {
            recfilestream = new FileOutputStream("received");
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
            
        } catch (FileNotFoundException ex) {
            //error
        }
        catch (IOException ex) {
            //error
        }  
      
    }
}
