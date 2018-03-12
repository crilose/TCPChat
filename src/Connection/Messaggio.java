/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.util.Scanner;

/**
 *
 * @author Cristiano
 */
public class Messaggio {
    
    String fromuser;
    String contenuto;
    Scanner input;
    String colore;
    ClientConnessioneTCP clientobj;
    ServerConnessioneTCP serverobj;
    int stato = 2;
    
    
    public Messaggio(String from, String colore, ClientConnessioneTCP client)
    {
        fromuser = from;
        input = new Scanner(System.in);
        this.colore = colore;
        this.clientobj = client;
    }
    
    public Messaggio(String from, String colore, ServerConnessioneTCP server)
    {
        fromuser = from;
        input = new Scanner(System.in);
        this.colore = colore;
        this.serverobj = server;
    }
    
    public String scrivi()
    {
        contenuto = input.nextLine();
        //Se abbiamo eseguito un comando
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
    
    public String scriviSmile()
    {
        System.out.println(colore + fromuser + ": " + ":)"); //Sia sul nostro output
        return (colore + fromuser + ": " + ":)"); //che sullo stream
    }
    
    //Controlla il tipo di utente, se 1 è un client, se 0 è un server
    public int checkUser()
    {
        if(clientobj!=null)
        {
            return 1; //client
        }
        else
        {
            return 0; //server
        }
    }
    
    public String getColore()
    {
        return colore;
    }
    
    public boolean checkCommand(String msg)
    {
        //Se rilevo un comando
        
        switch(msg)
        {
            case "/username":
                stato = 2; //stato default
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
                stato = 2; //stato default
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
                stato = 0; //stato "online"
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
                stato = 1; //stato "offline"
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
                stato = 3; //stato sendfile
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
                stato = 4; //stato smile
                if(checkUser()==1)
                {
                    clientobj.sendSmile();
                }
                else
                {
                    serverobj.sendSmile();
                }
                return true;
                
            default:
                stato = 6;
                return false;
        }
    }
}