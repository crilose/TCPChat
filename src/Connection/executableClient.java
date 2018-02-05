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
public class executableClient {
public static void main(String[] args) {
        
        ClientConnessioneTCP client = new ClientConnessioneTCP();
        client.startConnection();
        client.setUsername();
        while(true){
            client.communicate();
            client.closeConnection();
        }
      }
    
}
