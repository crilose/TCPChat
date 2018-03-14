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
 * La classe Client.
 * 
 * Questa classe implementa tutti i metodi necessari per la comunicazione TCP
 * con un server, avvia la comunicazione, la interrompe, gestisce l'invio di messaggi, l'esecuzione
 * del listener e le eccezioni di rete che si possono verificare.
 *
 * @author Cristiano Ceccarelli
 */
public class ClientConnessioneTCP {
    
    public final static int FILE_SIZE = 40; /** La massima grandezza di un file gestibile dall'applicativo */
    Socket connection = null; /** Socket per la connessione */
    String serverAddress = "localhost"; /** Indirizzo IP del server con cui comunicare */
    int port = 2000; /**Porta del server con cui comunicare */
    Scanner input; /**Scanner per l'input di stringhe. */
    Listener listen; /** Oggetto Listener per l'ascolto dei messaggi. */
    String username; /** Il proprio username. */
    /** Vengono dichiarati gli stream di input ed output. */
    DataOutputStream dOut;
    DataInputStream dIn;
     /** Gli stream per la lettura e l'invio di file. */
    FileInputStream filestream = null;
    BufferedInputStream filebuffer = null;
    BufferedOutputStream outfilebuffer = null;
    FileOutputStream recfilestream = null;
    
    
    
    /**
 * Avvia la connessione mediante l'utilizzo di un Socket.
 * <p>
 * Questo metodo viene utilizzato per avviare la connessione con il server
 * utilizzando un indirizzo ip ed una porta. Una volta stabilita la connessione
 * si istanziano anche gli stream di input ed output per l'invio e la ricezione
 * di dati.
 *
 * @return      void
 */
    
    public void startConnection()
    {
        try {
            input = new Scanner(System.in);
            /**Realizzo la connessione vera e propria tramite ip e porta */
            connection = new Socket(serverAddress, port);
            System.out.println("Connessione aperta");
            dOut = new DataOutputStream(connection.getOutputStream());
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

/**
 * Imposta il proprio nome utente
 * <p>
 * Questo metodo si utilizza per impostare il proprio nome utente da utilizzare
 * nella comunicazione.
 * @return      void
 */
    public void setUsername()
    {
        System.out.println("Inserisci il tuo username: ");
        username = input.nextLine();
    }

    
 /**
 * Comunicazione testuale
 * <p>
 * Questo metodo si utilizza per comunicare inviando e ricevendo le stringhe testuali oggetto
 * della conversazione.
 * @return      void
 */
    
    public void communicate()
    {
        listen = new Listener(dIn,this);
        listen.start();
        /**Mentre la connessione è attiva prendo i messaggi che scrivo e li invio*/
        while(connection.isConnected())
        {     
            try {
                if(listen.getStato()==0 || input.next().contentEquals("/online"))
                {
                    Messaggio msg = new Messaggio(username,(char)27 + "[35m",this);
                    dOut.writeUTF(msg.scrivi() + "\u001B[0m");
                    dOut.flush();
                }
                
                
                
            } catch (IOException ex) {
                System.err.println(ex);
            }
                
        }
    }


 /**
 * Chiusura della connessione
 * <p>
 * Questo metodo si utilizza per interrompere la comunicazione.
 * @return      void
 */
    
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


/**
 * Stato online
 * <p>
 * Questo metodo si utilizza per riprendere la comunicazione dopo essere
 * andati temporaneamente offline.
 * @return      void
 */
    
    public void online()
    {
        listen.changeState(0); /** 0 Significa online */
    }

    
    /**
 * Stato offline
 * <p>
 * Questo metodo si utilizza per interrompere momentaneamente l'invio
 * e la ricezione di messaggi.
 * @return      void
 */
    public void offline()
    {
        listen.changeState(1); /** 1 Significa offline */
    } 

/**
 * Invio di un file
 * <p>
 * Questo metodo si utilizza per inviare un file specificandone il nome
 * e il percorso.
 * @return      void
 */
    
    public void sendFile()
    {
        try{
            
        dOut.writeUTF("sendfile");
        System.out.println("Inserisci il nome del file: ");
        String filename = input.nextLine();
        File filetosend = new File (filename);
        byte [] bytearray  = new byte [(int)filetosend.length()];
        filestream = new FileInputStream(filetosend);
        filebuffer = new BufferedInputStream(filestream); /** Wrapping delle classi */
        dOut.write(bytearray,0,bytearray.length);
        dOut.flush();
        System.out.println("Fatto!");
        } 
        catch (IOException ex) {
            //error
        }
    }

 /**
 * Invio di uno smile
 * <p>
 * Questo metodo si utilizza per inviare uno smile come messaggio
 * all'utente client collegato.
 * @return      void
 */       
    public void sendSmile()
    {
        try {
            Messaggio msg = new Messaggio(username,(char)27 + "[35m",this);
            dOut.writeUTF(msg.scriviSmile() + "\u001B[0m");
            dOut.flush();

        } catch (IOException ex) {
           //errore
        }
    }

    
    /**
 * Ricezione di un file
 * <p>
 * Questo metodo si utilizza per ricevere un file e salvarlo
 * sul proprio disco con un nome predefinito, da dover poi
 * rinominare per leggerne le informazioni effettivamente ricevute
 * nel formato corretto.
 * @return      void
 */
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


/**
 * Echo del messaggio ricevuto
 * <p>
 * Questo metodo si utilizza per inviare all'interlocutore
 * una copia esatta dell'ultimo messaggio ricevuto.
 * @return      void
 */    
    public void echo()
    {
            try {
                String received = listen.getLastMsg();
                Messaggio msg = new Messaggio(username,(char)27 + "[35m",this);
                dOut.writeUTF(msg.echoString(received) + "\u001B[0m");
                dOut.flush();
            } catch (IOException ex) {
                //eccezione
            }
    }
}
