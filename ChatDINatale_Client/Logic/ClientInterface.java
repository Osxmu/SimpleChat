package ChatDINatale_Client.Logic;


//
// In questa interfaccia ci creeremo i metodi
// che andremo ad assegnare ad ogni bottone
// all'interno della nosta GUI in modo tale da avere
// uno standard ( Utilizzando questa interfaccia )
//
public interface ClientInterface {

    //
    // Inizializziamo la connessione ( Chiamato dal bottone connetti )
    //
    public abstract boolean inizializza_connessione( String target_ip );


    //
    // Invia un messaggio ( Chiamato dal bottone invia )
    //
    public abstract boolean invia_messaggio( String messaggio );


    //
    // Autentica || Permette all'utente di autenticarsi e di accedere alla chat
    //
    public abstract int autentica( String username, String password );

}
