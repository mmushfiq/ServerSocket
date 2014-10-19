
package az.mm.serversocketservice.Classes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author MM
 */
public class ServerSocketStart implements Runnable {
    
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    static final int port = 2014;
    static boolean running=true;
    
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server Socket aktivleshdi: " + serverSocket);

            while (running) {

                //yeni socket yaranana edene qeder gozleyir
                clientSocket = serverSocket.accept(); //accept metodunun sayesinde yeni obyekt yaradilir
                System.out.println("ClientSocketle baglanti yaradildi: " + clientSocket);

                RequestResponseProcess requestResponseProcess = new RequestResponseProcess(clientSocket);
                requestResponseProcess.start();
            }

        } catch (Exception ex) {
            System.err.println("ServerSocketStart run() method catch--> " + ex);
        } finally {
            try {
                if(serverSocket != null){
                   serverSocket.close();
                }  
            } catch (IOException ex) {
                System.err.println("ServerSocketStart run() method finally catch--> " + ex);
            }
        }
    }    
    
    public void stopListening(){
       running=false;
    }
    
}//end class
