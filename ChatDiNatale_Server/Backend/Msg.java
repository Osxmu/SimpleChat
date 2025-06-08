package ChatDiNatale_Server.Backend;

import java.net.Socket;

public class Msg {
    public String messaggio;
        public String username;
        public Socket user_conn;

        public Msg() { return; }

        public Msg( String _messaggio, String _username, Socket _user_conn )
        {
            this.messaggio = _messaggio;
            this.username = _username;
            this.user_conn = _user_conn;
            return;
        }
}