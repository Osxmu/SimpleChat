package ChatDiNatale_Server;


//
// Imports
//
import java.util.ArrayList;
import java.net.Socket;
import ChatDiNatale_Server.Backend.Msg;


//
// Qusta classe conterra tutte le diverse 
// variabili che ci serviranno per la nostra
// chat: messaggi, utenti, password etc...
//
public class Globals {

    //
    // Attributi
    // 
    public ArrayList<Msg> messaggi = new ArrayList<Msg>();
    public ArrayList<String> utenti_connessi = new ArrayList<String>();
    public ArrayList<Socket> connessioni = new ArrayList<Socket>();
    public String chat_password = null;
    public String chat_id = null;
    public boolean keep_server_alive = true;

}
