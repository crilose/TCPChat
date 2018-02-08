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
        if(checkCommand(contenuto)==true)
        {
            System.out.println(colore + fromuser + " ha eseguito un comando.");
            return (colore + fromuser + " ha eseguito un comando.");
            
        }
        else
        {
            System.out.println(colore + fromuser + ": " + contenuto);
            return (colore + fromuser + ": " + contenuto);
        }
    }
    
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
    
    public boolean checkCommand(String msg)
    {
        if(msg.contentEquals("/username"))
        {
            if(checkUser()==1)
            {
                clientobj.setUsername();
            }
            else
            {
                serverobj.setUsername();
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}
