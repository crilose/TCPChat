/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.util.Scanner;

/**
 *
 * @author Cristiano Ceccarelli
 */
public class Messaggio {
    
    String fromuser; /** Nome utente proprietario del messaggio. */
    String contenuto; /** Contenuto del messaggio. */
    Scanner input; /** Scanner per l'input. */
    String colore; /** Colore del messaggio. */
    ClientConnessioneTCP clientobj; /** Oggetto Client. */
    ServerConnessioneTCP serverobj; /** Oggetto Server. */
    int stato = 2; /** Intero che rappresenta lo stato del messaggio. */
    
    /**
     * Costruttore per un messaggio del client.
     */
    public Messaggio(String from, String colore, ClientConnessioneTCP client)
    {
        fromuser = from;
        input = new Scanner(System.in);
        this.colore = colore;
        this.clientobj = client;
    }
    /**
     * Costruttore per un messaggio del server.
     */
    public Messaggio(String from, String colore, ServerConnessioneTCP server)
    {
        fromuser = from;
        input = new Scanner(System.in);
        this.colore = colore;
        this.serverobj = server;
    }
    /**
     * Scrivere il testo del messaggio.
     * 
     * Il metodo consente di scrivere il testo del messaggio da inviare
     * e di comunicare a sè stessi e all'interlocutore l'eventuale invio di un comando,
     * l'eventuale proprio stato di disponibilità.
     * @return Il contenuto del messaggio inviato. Se nessun messaggio o comando non riconosciuto
     * è una stringa vuota.
     */
    public String scrivi()
    {
        contenuto = input.nextLine();
        /**Se abbiamo eseguito un comando*/
        if(checkCommand(contenuto)==true)
        {
            switch (stato)
            {
                case 2:
                System.out.println(colore + fromuser + " ha eseguito un comando.");
                return (colore + fromuser + " ha eseguito un comando.");
                
                case 0: 
                    System.out.println(colore + fromuser + " è andato online.");
                    return (colore + fromuser + " è andato online.");
                    
                case 1:
                    System.out.println(colore + fromuser + " è andato offline.");
                    return (colore + fromuser + " è andato offline.");
            }
        }
        else
        {
            System.out.println(colore + fromuser + ": " + contenuto); //Sia sul nostro output
            return (colore + fromuser + ": " + contenuto); //che sullo stream
        }
        return "";
    }
    /**
     * Scrivere uno smile.
     * @return Il messaggio inviato.
     */
    public String scriviSmile()
    {
        System.out.println(colore + fromuser + ": " + ":)"); /**Sia sul nostro output*/
        return (colore + fromuser + ": " + ":)"); /**che sullo stream*/
    }
    /**
     * Scrivere la stringa ricevuta
     * @param StringToRepeat E' la stringa che si vuole ripetere.
     * @return Il messaggio inviato.
     */
    public String echoString(String StringToRepeat)
    {
        System.out.println(colore + fromuser + ": " + StringToRepeat); /**Sia sul nostro output*/
        return (colore + fromuser + ": " + StringToRepeat); /*che sullo stream*/
    }
    
   /**
    * Controlla il tipo di utente.
    * 
    * @return 1 se si tratta di un client, 0 se si tratta di un server
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
     * Ritorna il colore del messaggio.
     * @return Il colore del messaggio.
     */
    public String getColore()
    {
        return colore;
    }
    /**
     * Controlla per eventuali comandi e sceglie le azioni da fare.
     * 
     * Il metodo è in grado di distinguere il comando impartito e di utilizzare
     * i metodi di server e client corrispondenti.
     * @param msg E' il messaggio scritto dall'utente.
     * @return Vero se si tratta di un comando, Falso se è un messaggio normale.
     */
    public boolean checkCommand(String msg)
    {
        //Se rilevo un comando
        
        switch(msg)
        {
            case "/username":
                stato = 2; /** 2 E' lo stato di default */
                if(checkUser()==1)
                {
                    clientobj.setUsername();
                }
                else
                {
                    serverobj.setUsername();
                }
                return true;
                
            case "/end":
                stato = 2; /** 2 E' lo stato di default */
                if(checkUser()==1)
                {
                    clientobj.closeConnection();
                }
                else
                {
                    serverobj.closeConnection();
                }
                return true;
            
            case "/online":
                stato = 0; /** 0 E' lo stato di online */
                if(checkUser()==1)
                {
                    clientobj.online();
                }
                else
                {
                    serverobj.online();
                }
                return true;
                
            case "/offline":
                stato = 1; /** 1 E' lo stato di offline */
                if(checkUser()==1)
                {
                    clientobj.offline();
                }
                else
                {
                    serverobj.offline();
                }
                return true;
                
            case "/sendfile":
                stato = 3; /** 3 E' lo stato di invio di un file */
                if(checkUser()==1)
                {
                    clientobj.sendFile();
                }
                else
                {
                    serverobj.sendFile();
                }
                return true;
                
            case "/smile":
                stato = 4; /** 4 E' lo stato di invio di uno smile */
                if(checkUser()==1)
                {
                    clientobj.sendSmile();
                }
                else
                {
                    serverobj.sendSmile();
                }
                return true;
                
            case "/echo":
                stato = 5; /** 5 E' lo stato di invio del messaggio echo */
                if(checkUser()==1)
                {
                    clientobj.echo();
                }
                else
                {
                    serverobj.echo();
                }
                return true;
                
            default:
                stato = 6;
                return false;
        }
    }
}