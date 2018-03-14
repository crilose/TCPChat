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
 * La classe Server.
 * 
 * Questa classe implementa tutti i metodi necessari per la comunicazione TCP
 * con un client, avvia la comunicazione, la interrompe, gestisce l'invio di messaggi, l'esecuzione
 * del listener e le eccezioni di rete che si possono verificare.
 *
 * @author Cristiano Ceccarelli
 */
public class ServerConnessioneTCP {

    
        int port=2000; /** La porta utilizzata per l'ascolto in ricezione. */
        ServerSocket sSocket = null; /** Oggetto ServerSocket necessario per accettare richieste dal client. */
        Socket connection;  /** Oggetto da usare per realizzare la connessione TCP.*/
        Listener listen; /** Oggetto Listener per l'ascolto dei messaggi. */
        Scanner input; /**Scanner per l'input di stringhe. */
        
        /** Vengono dichiarati gli stream di input ed output. */
        DataOutputStream dOut;
        DataInputStream dIn;
        String username; /** Il proprio username. */
        /** Gli stream per la lettura e l'invio di file. */
        FileInputStream filestream = null;
        BufferedInputStream filebuffer = null;
        BufferedOutputStream outfilebuffer = null;
        FileOutputStream recfilestream = null;
    
    
   /**
 * Avvia la connessione mediante l'utilizzo di un Socket.
 * <p>
 * Questo metodo viene utilizzato per avviare l'ascolto su una porta in attesa
 * di un client interessato a collegarsi. Una volta stabilita la connessione
 * si istanziano anche gli stream di input ed output per l'invio e la ricezione
 * di dati.
 *
 * @return      void
 */
    public void startConnection()
    {
            try {
                sSocket = new ServerSocket(port); /**Il server si mette in ascolto sulla porta voluta */
                System.out.println("In attesa di connessioni!");
                input = new Scanner(System.in);
                /**Si è stabilita la connessione */
                connection = sSocket.accept();
                dOut = new DataOutputStream(connection.getOutputStream());
                dIn = new DataInputStream(connection.getInputStream());
                
                
                
            } catch (IOException ex) {
                System.err.println(ex);
            }
            
    }
    
        /**
 * Visualizza le informazioni di stato sulla connessione attuale
 * <p>
 * Questo metodo si utilizza per stampare a schermo le informazioni principali sulla
 * connessione in atto: indirizzo ip e porta del client, indirizzo ip e porta del server.
 *
 * @return      void
 */
    
    public void getInfo()
    {
                System.out.println("Connessione stabilita!");
                System.out.println("Socket server: " + connection.getLocalSocketAddress());
                System.out.println("Socket client: " + connection.getRemoteSocketAddress());
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
                    Messaggio msg = new Messaggio(username,(char)27 + "[34m",this);
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
        /** Chiusura della connessione */
            try {
                if (sSocket!=null) sSocket.close();
                connection.close();
            } catch (IOException ex) {
                System.err.println("Errore nella chiusura della connessione!");
            }
            System.out.println("Connessione chiusa lato client!");
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
            Messaggio msg = new Messaggio(username,(char)27 + "[34m",this);
            dOut.writeUTF(msg.scriviSmile() + "\u001B[0m");
            dOut.flush();
        } catch (IOException ex) {
           //errore
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
                Messaggio msg = new Messaggio(username,(char)27 + "[34m",this);
                dOut.writeUTF(msg.echoString(received) + "\u001B[0m");
                dOut.flush();
            } catch (IOException ex) {
                //eccezione
            }
    }
}
