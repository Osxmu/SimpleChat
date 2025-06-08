package ChatDINatale_Client.Logic;



//
// Imports
//
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;


//
// Questa classe ci permettera di interagire
// con il server e di coseguenza anche con gli 
// atri utenti che prenderanno parte alla chat
//
public class Client implements ClientInterface{
    
    //
    // Attributi
    //
    public static final int port = 1337;
    public Socket connessione = null;
    public InputStreamReader stream = null;
    public BufferedReader fromServer = null;
    public PrintWriter fromClient = null;
    public InetAddress target_address = null;
    public boolean autenticato = false;
    public boolean connesso = false;
   

    //
    // Costruttore  ( Vuoto )
    //
    public Client() { return; }


    //
    // Inizializziamo la connessione ( Chiamato dal bottone connetti )
    //
    @Override
    public boolean inizializza_connessione(String target_ip) {

        /* Inizializziamo la connessione con il server */
        try
        {
            /* Prendiamo l'ip del server */
            this.target_address = InetAddress.getByName(target_ip);

            /* Creiamo la nostra connessione con il server */
            this.connessione = new Socket(target_address, port);

            /* Flaggiamo come connesso */
            this.connesso = true;

            /* Connessione riuscita */
            return true;
        }
        catch( IOException e ) {
            this.target_address = null;
            this.connessione = null;
            /* Flaggiamo come NON connesso */
            this.connesso = false;
            e.printStackTrace(); 
            return false;
        }
    }


    //
    // Invia il messaggio
    //
    @Override
    public boolean invia_messaggio(String messaggio) {
        
        /* Controlliamo se siamo connessi ad autenticati */
        if ( !this.connesso || !this.autenticato )
            return false;

        /* Logica per l'invio del messaggio */
        try
        {
            /* Controlliamo che la output stream sia stata inizializzata */
            if ( this.fromClient == null)
                this.fromClient = new PrintWriter(this.connessione.getOutputStream());

            /* Inviamo il messaggio al server */
            this.fromClient.println(messaggio); 
            this.fromClient.flush();         
            
            return true;
        }
        catch( IOException e ) { e.printStackTrace(); return false; }
    }



    //
    // Autentica || Permette all'utente di autenticarsi e di accedere alla chat
    //
    @Override
    public int autentica( String username, String password )
    {
        /* Controlliamo se siamo connessi */
        if ( !this.connesso )
            return 1;

        try
        {
            /* Inizializziamo la output stram */
            this.fromClient = new PrintWriter(this.connessione.getOutputStream());

            /* Inizializziamo la nostra input stream */
            this.stream = new InputStreamReader(this.connessione.getInputStream());
            this.fromServer = new BufferedReader(this.stream);

            /* Inviamo la nostra password e il nostro username */
            fromClient.println(username + "/" + password); 
            fromClient.flush();

            /* Aspettiamo la risposta dal server */
            String res = fromServer.readLine();
            if ( res.trim().equals("1337") )
            {
                /* Autenticazione riuscita */
                this.autenticato = true; 
                return 0;
            }

            if ( res.trim().equals("Err1") ) 
                return -1;
                
            /* Autenticazione fallita */
            this.autenticato = false; 
            return 1;
        }
        catch( IOException e ) { e.printStackTrace(); }

        return 1;
    }


    //
    // Chiudiamo la connessione
    //
    public void chiudi_connessione()
    {
        /* Controlliamo se siamo effettivamente connessi */
        if ( this.connesso ) 
            return; 


        /* Distruggiamo i diversi oggetti */
        try
        {
            this.fromServer.close();
            this.stream.close();
            this.fromClient.close();
            this.connessione.close();
        }catch (IOException e) { e.printStackTrace();}
        

        /* Cambiamo lo stato dei boolean */
        this.autenticato = false;
        this.connesso = false;
        
        return;
    }


}
