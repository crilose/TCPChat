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
    
    public Listener(DataInputStream in)
    {
        this.input = in;
    }
    
    public void run()
    {
        while(true)
        {
            try {
                if(input.available()>0)
                {
                    System.out.println(input.readUTF());
                }
            } catch (IOException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
