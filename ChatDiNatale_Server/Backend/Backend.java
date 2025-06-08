package ChatDiNatale_Server.Backend;


//
// Imports
//
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import ChatDiNatale_Server.Globals;


//
// Questa classe rappresentera il nostro 
// backend che ci permettera di usufruire
// di funzionalita dal client
// 
public class Backend implements Runnable {
    

    //
    // Attributi
    //
    private Socket connessione = null;
    private InputStreamReader stream = null;
    private BufferedReader fromClient = null;
    private PrintWriter fromServer = null;
    private Globals globals = null;
    private boolean autenticato = false;
    private String username = null;
    private Thread refresher_thread = null;

    //
    // Costruttore  ( vuoto )
    //
    public Backend() { return; }


    //
    // Costruttore ( Con parametri )
    // 
    public Backend( Socket _connessione, Globals _globals ) {
        this.connessione = _connessione;
        this.globals = _globals;
        return;
    }


    //
    // Questa funzione verra chiamata quando 
    // creeremo il nostro thread e lo runneremo 
    // ci permettera di gestire piu connessioni
    // in contemporanea.
    // 
    @Override
    public void run() {

        /* Controlliamo se i parametri sono stati inizilizzati correttamente */
        if ( this.connessione == null )
            return;

    
        /* Logic */
        try
        {
            //
            // Inzializziamo le nostre variabili
            //
            this.stream = new InputStreamReader(this.connessione.getInputStream());
            this.fromClient = new BufferedReader(this.stream);
            this.fromServer = new PrintWriter(this.connessione.getOutputStream());


            /* Manteniamo la connessione attiva e aspettiamo gli input del client */
            while ( true )
            {
                /* Prendiamo in input il messaggio mandato dal client */
                String message = fromClient.readLine();

                /* Controlliamo se l'utente si e autenticato */
                if ( !this.autenticato )
                {
                    /* Splittiamo il messaggio per prendere username e password */
                    String[] auth_data = message.split("/");
                    if ( auth_data.length != 2 )
                        continue;

                    /* Controlliamo se la password e corretta */
                    if ( auth_data[1].equals(this.globals.chat_password) )
                    {
                        /* Controlliamo che il nome utente non sia gia stato scelto */
                        if ( this.globals.utenti_connessi.contains(auth_data[0].trim()))
                        {
                            /* Autenticazione fallita */
                            fromServer.println("Err1");
                            fromServer.flush();
                            continue;
                        }

                        /* Loggato correttamente */
                        fromServer.println("1337");
                        fromServer.flush();

                        /* Salviamo lo username */
                        this.username = auth_data[0].trim();

                        /* Aggiungiamo l'utente alla lista di utenti */
                        this.globals.connessioni.add(this.connessione);
                        this.globals.utenti_connessi.add(this.username);

                        /* Notifichiamo che l'utente si e autenticato */
                        this.autenticato = true;

                        /* Iniziamo il processo di refresh */
                        this.refresher_thread = new Thread(
                            new Refresher(this.fromServer, this.globals)
                        );
                        this.refresher_thread.start();

                        /* Skippiamo questo ciclo */
                        continue;
                    }

                    /* Autenticazione fallita */
                    fromServer.println("FAILED");
                    fromServer.flush();
                    continue;
                }

                /* Aggiungia il messaggio alla lista di messaggi */
                this.globals.messaggi.add(
                    new Msg(
                        message, 
                        this.username,
                        this.connessione
                    )
                );

                System.out.println(message);
            }   
        }
        catch ( SocketException e ) { 

            /* Se la connessione viene interrotta unlinkiamo questo utente */
            this.globals.connessioni.remove(this.connessione);
            this.globals.utenti_connessi.remove(this.username);

            /* Fermiamo il thread di refresh */
            refresher_thread.interrupt();

            /* Notifichiamo la disconessione */
            System.out.println("Utente: " + this.username + " disconnesso.");

        }   
        catch( IOException e ) { e.printStackTrace(); }

        return;
    }


}
