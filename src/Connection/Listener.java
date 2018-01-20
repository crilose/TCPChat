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
    private String from;
    
    public Listener(DataInputStream in, String type)
    {
        this.input = in;
        from = type;
    }
    
    public void run()
    {
        while(true)
        {
            try {
                if(input.available()>0)
                {
                    System.out.println(from + input.readUTF());
                }
            } catch (IOException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setType(String t)
    {
        if(t.contentEquals("client"))
        {
            from = "Server: ";
        }
        
        if(t.contentEquals("server"))
        {
            from = "Client: ";
        }
    }
    
}
