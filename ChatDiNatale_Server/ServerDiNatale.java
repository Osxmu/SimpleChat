package ChatDiNatale_Server;


import java.net.InetAddress;
//
// Imports
//
import java.net.ServerSocket;
import ChatDiNatale_Server.Backend.Backend;
import java.io.IOException;


//
// Questa classe conterra l'entry point 
// del nostro server che ci permettera 
// di sfruttare funzionalita dal client
// in modo tale da creare una chat che
// coinvolgera piu utenti.
//
public class ServerDiNatale {


    //
    // Globals
    //
    public static final int port = 1337;
    public static ServerSocket myServer = null;
    public static Globals globals = null;


    //
    // Entry point || Main
    //
    public static void main(String[] args) {

        /* Inizializziamo la classe che conterra le nostre variabili globali */
        globals = new Globals();
        globals.chat_id = ((Integer)((int)(Math.random() * (9999 - 5678)) + 5678)).toString();
        globals.chat_password = "123";
        
        /* Logic */
        try
        {
            /* Creating the server */
            myServer = new ServerSocket(port);


            /* Startup information */
            System.out.println("( + ) Server inizializzato correttamente...");
            System.out.println("( > ) Ip: " + InetAddress.getLocalHost().toString());
            System.out.println("( > ) Port: " + port );

            /* Manteniamo il ciclo attivo per accettare le connessioni */
            while ( globals.keep_server_alive )
            {
                /* Creiamo una nuova instanza del nostro gestore */
                new Thread(
                    new Backend(myServer.accept(), globals)
                ).start();
            }
        }
        catch (IOException e) { e.printStackTrace(); }
    
        return;
    }
}
