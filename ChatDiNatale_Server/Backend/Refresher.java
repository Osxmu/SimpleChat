package ChatDiNatale_Server.Backend;


//
// Imports
//
import java.io.PrintWriter;
import ChatDiNatale_Server.Globals;


//
// Questa classe servira per creare un thread che ci permettera
// di mandare un refresh ai messaggi che il client riceve.
//
public class Refresher implements Runnable {


    //
    // Attributi
    //
    public PrintWriter fromServer = null;
    public Globals globals = null;
    

    //
    // Costruttore  ( vuoto )
    //
    public Refresher() { return; }


    //
    // Costruttore ( Con parametri )
    //
    public Refresher( PrintWriter out, Globals _globals ) 
    {
        this.fromServer = out;
        this.globals = _globals;
        return;
    }



    //
    // Questa funzione verra runnata quando creeremo
    // il nostro thread e di conseguenza manterra i messaggi
    // visualizzati dal client aggiornati
    //
    @Override
    public void run() {

        while ( true )
        {
            try
            {
                //
                // Ciclo per il refresh dei messaggi
                //
                while ( true )
                {   
                    /* Sleeping ( per non refreshare troppo velocemente ) */
                    Thread.sleep(125);
    
                    /* Creeiamo la strigna con tutti i messaggi */
                    String payload = "msg:";
                    for ( int i = 0; i < globals.messaggi.size(); i++ )
                    {
                        /* Prendiamo le info del messaggio corrente */
                        Msg info = globals.messaggi.get(i);
    
                        /* Formattiamo */
                        payload += "\n[ " + info.username + " ] " + info.messaggio;
                    }
    
                    /* Inviamo il messaggio al client */
                    fromServer.println(payload);
                    fromServer.flush();

                    /* Sleeping ( per non refreshare troppo velocemente ) */
                    Thread.sleep(125);
    
                    /* Creeiamo la stringa con tutti gli utenti */
                    payload = "usr:";
                    for ( int i = 0; i < globals.utenti_connessi.size(); i++ )
                    {
                        payload += "\\" + globals.utenti_connessi.get(i);
                    }   
    
                    /* Inviamo il messaggio al client */
                    fromServer.println(payload);
                    fromServer.flush();
                }
            }
            catch ( InterruptedException e ) { e.printStackTrace(); }
        }
    }

}
