/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  La classe listener per l'ascolto dello stream.
 * 
 * Questa classe è fondamentale per l'ascolto continuo e la stampa a schermo
 * dei messaggi provenienti dall'interlocutore.
 * La classe è un Thread.
 *
 * @author Cristiano Ceccarelli
 */
public class Listener extends Thread{
    
    private DataInputStream input; /** Lo stream di ricezione. */
    private int state; /** Lo stato dell'oggetto istanziante il listener. */
    String received; /** Il messaggio ricevuto dallo stream. */
    /** I due oggetti server e client. */
    ClientConnessioneTCP clientobj;
    ServerConnessioneTCP serverobj;
    /**
     * Costruttore per il client.
     */
    public Listener(DataInputStream in,ClientConnessioneTCP client)
    {
        this.input = in;
        state = 0;
        clientobj = client;
    }
    /**
     * Costruttore per il server.
     */
    public Listener(DataInputStream in,ServerConnessioneTCP server)
    {
        this.input = in;
        state = 0;
        serverobj = server;
    }
    /**
     * Metodo di ascolto.
     * 
     * Il metodo run(), che consiste nelle operazioni che il thread fa
     * mentre è in esecuzione, implementa un ciclo while continuo in grado di stampare
     * a schermo tutte le stringhe ricevute nei messaggi dell'interlocutore.
     */
    public void run()
    {
        while(true)
        {
            try {
                if(input.available()>0 && state == 0)
                {
                    received = input.readUTF();
                    if(received.contentEquals("sendfile"))
                    {
                        if(checkUser()==1)
                        {
                            clientobj.receiveFile();
                        }
                        else
                        {
                            serverobj.receiveFile();
                        }
                    }
                    System.out.println(received);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Ritorna l'ultimo messaggio.
     * 
     * Necessario uno split del messaggio per eliminare il nome utente.
     * @return L'ultimo messaggio ricevuto e memorizzato nella variabile apposita.
     */
    public String getLastMsg()
    {
        String[] msgContent = received.split(":");
        return msgContent[1];
    }
    /**
     * Controlla il tipo di utente.
     * 
     * @return 1 se è un client, 0 se è un server 
     */
    public int checkUser()
    {
        if(clientobj!=null)
        {
            return 1; 
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * Cambia lo stato del listener
     * @param stato E' il numero che indica lo stato da impostare. (0 per online, 1 per offline)
     */
    public void changeState(int stato)
    {
        state = stato;
    }
    /**
     * Ritorna lo stato corrente.
     * @return L'attuale stato del listener. 
     */
    public int getStato()
    {
        return state;
    }
            
            

    
}
