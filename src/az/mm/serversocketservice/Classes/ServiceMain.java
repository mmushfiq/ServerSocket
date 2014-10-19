
package az.mm.serversocketservice.Classes;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

/**
 * @author MM
 */
public class ServiceMain implements WrapperListener{
    
    ServerSocketStart serverSocketStart = null;
    
    public static void main(String[] args) {
        WrapperManager.start(new ServiceMain(), args);
    }

    @Override
    public Integer start(String[] strings) {
        serverSocketStart = new ServerSocketStart();
        Thread thread = new Thread(serverSocketStart);
        thread.start();
        return null;
    }
  
    @Override
    public int stop(int i){
        serverSocketStart.stopListening();
        return 0;
    }

    @Override
    public void controlEvent(int i) {
    }
    
}//end class
