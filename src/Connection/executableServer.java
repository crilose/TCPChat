/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

/**
 *
 * @author Cristiano
 */
public class executableServer {
    
    
    
    public static void main(String[] args) {
        
        ServerConnessioneTCP server = new ServerConnessioneTCP();
        server.startConnection();
        server.setUsername();
        while(true){
            server.communicate();
        }
      }
}
