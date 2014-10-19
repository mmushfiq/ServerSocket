
package az.mm.serversocketservice.Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;

/**
 * @author MM
 */
public class RequestResponseProcess extends Thread{

    Socket socket = null;

    public RequestResponseProcess(Socket clientSocket) {
      this.socket = clientSocket;
    }

    @Override
    public void run(){
       try {
            System.out.println("Baglantini qebul eden thread: "+this.getName()+"  &  socket info: "+socket);

            //Client Socketden gelen deyishenin deyerini goturur
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request = in.readLine();

            // MD5 shifreleme alqoritmi     
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(request.getBytes());
            byte byteData[] = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
               sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            String response=sb.toString();

            System.out.println("ClientSocketden ServerSockete gonderilen request: " + request);
            System.out.println("ServerSocketden ClientSockete gonderilen response: " + response);

            //Modifikasiya olunmush deyeri Client Sockete geri gonderir       
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);    
            out.println(response);

        } catch (Exception ex) {
            System.err.println("RequestResponseProcess run() method chatch-->"+this.getName()+"-->"+ex);
        } finally{
            System.out.println("Socket ishini bitirdi ve baglandi.. \n");
            try {
                if(socket != null){
                   socket.close();
                }
            } catch (IOException ex) {
                System.err.println("RequestResponseProcess run() method finally chatch-->-->"+this.getName()+"-->"+ex);
            }
        }
    }//end run()
    
}//endClass
