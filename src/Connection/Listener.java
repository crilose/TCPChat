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
 *
 * @author Cristiano
 */
public class Listener extends Thread{
    
    private DataInputStream input;
    private int state;
    String received;
    ClientConnessioneTCP clientobj;
    ServerConnessioneTCP serverobj;
    
    public Listener(DataInputStream in,ClientConnessioneTCP client)
    {
        this.input = in;
        state = 0;
        clientobj = client;
    }
    
    public Listener(DataInputStream in,ServerConnessioneTCP server)
    {
        this.input = in;
        state = 0;
        serverobj = server;
    }
    
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
    
    
    public void changeState(int stato)
    {
        state = stato;
        //0 per online, 1 per offline
    }
    
    public int getStato()
    {
        return state;
    }
            
            

    
}
