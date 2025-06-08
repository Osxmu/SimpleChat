package ChatDINatale_Client.Logic;


//
// Imports
//
import ChatDINatale_Client.GUI.GUI;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;


//
// Questa classe ci servira per tenere aggiornata
// la lista dei messaggio e la lista degli utenti collegati
//
public class Refresher implements Runnable {

    //
    // Attributi
    //
    GUI gui = null;
    Client client = null;
    InputStreamReader stream = null;
    BufferedReader fromServer = null;


    //
    // Costruttore ( con parametri )
    //
    public Refresher( GUI _gui, Client _client )
    {
        this.gui = _gui;
        this.client = _client;
        return;
    }


    //
    // Funzione che runnera in un thread che refreshera la lista di utenti e i messaggi
    //
    @Override
    public void run() {
        
        /* Controlliamo che le due classi siano puntatori validi */
        if ( this.gui == null || this.client == null )
            return;


        /* Continuiamo a ciclare fin quando non possiamo creare i nostri oggetti */
        while ( true )
        {
            try
            {   
                /* Facciamo dormire il thread */
                Thread.sleep(100);

                /* Inizializziamo le stream */
                this.stream = new InputStreamReader(this.client.connessione.getInputStream());
                this.fromServer = new BufferedReader(this.stream);

                /* Lo manteniamo in vita solo quando siamo connessi */
                while ( this.client.connesso )
                {
                    /* Utenti */
                    String payload = new String();
                    if ( this.fromServer.ready() )
                    {
                        // Read all available lines in the buffer
                        String line;
                        while (this.fromServer.ready() && (line = this.fromServer.readLine()) != null) {
                            payload += line + "\n";
                        }
                    }

                    if ( payload.length() < 3 )
                        continue;

                    if ( payload.substring(0, 4).trim().equals("msg:") )
                        gui.msg_area.setText(payload.substring(3));
                    else
                        gui.users_area.setText(payload.substring(3));


                }
            }
            catch ( NullPointerException e ) { /* facciamo nulla ( per evitare print inutili ) */ }
            catch( IOException e ) { /* facciamo nulla ( per evitare print inutili ) */ }
            catch(InterruptedException e ) { /* facciamo nulla ( per evitare print inutili ) */ }
        }
    }
}
