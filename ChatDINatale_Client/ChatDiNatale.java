package ChatDINatale_Client;


//
// Imports
//
import ChatDINatale_Client.GUI.GUI;
import ChatDINatale_Client.Logic.Client;
import ChatDINatale_Client.Logic.Refresher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//
// Questa classe conterra l'entry point 
// del nostro programma.
//
public class ChatDiNatale {
    
    //
    // Entry point || Main
    //
    public static void main(String[] args) {

        //
        // Variabili
        //
        GUI myGUI = new GUI();
        Client client = new Client();
        

        /* GUI */
        new Thread(myGUI).run();


        //
        // Gestore per inizializzare la connessione
        //
        myGUI.btn_connettiti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /* Controlliamo se l'ip address e stato inserito correttamente */
                if ( myGUI.txb_serverip.getText().isBlank() )
                {
                    GUI.msg_box("Errore!", "Inserisci un ip address valido!");
                    return;
                }


                /* Controlliamo se il nome utente e stato inserito correttamente */
                if ( myGUI.txb_username.getText().isBlank() )
                {
                    GUI.msg_box("Errore!", "Assicurati di aver inserito un username valido!");
                    return;
                }


                /* Controlliamo se la password inserita e una stringa valida */
                if ( String.valueOf(myGUI.txb_password.getPassword()).isBlank() )
                {
                    GUI.msg_box("Errore!", "Assicurati di aver inserito una password valid!");
                    return;
                }


                /* Ci colleghiamo con il server */
                if ( !client.inizializza_connessione(myGUI.txb_serverip.getText()) )
                {
                    GUI.msg_box("Errore!", "Connessione col server fallita!");
                    return;
                }
            

                /* Registriamo lo username e la password */
                int auth = client.autentica(
                    myGUI.txb_username.getText(),
                    String.valueOf(myGUI.txb_password.getPassword())
                );


                /* Password errata / Fallita */
                if ( auth == 1 )
                {
                    GUI.msg_box("Errore!", "Autenticazione fallita controlla la password!");
                    return;
                }


                /* Nome utente selezionato gia scelto! */
                if ( auth == -1 )
                {
                    GUI.msg_box("Errore!", "NOME UTENTE GIA REGISTRATO!!!");
                    return;
                }


                //
                // Creiamo l'oggetto per il refresh
                //        
                new Thread(
                    new Refresher(myGUI, client)
                ).start();
                
                
                /* Cambiamo lo stato della label */
                myGUI.lbl_serverip.setText("Server Ip (Stato: Connesso):");
            }
        });


        //
        // Gestore per disconnetersi dal server corrente
        //
        myGUI.btn_disconetti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /* Chiudiamo la connessione */
                client.chiudi_connessione();

                /* Garantiamo di distruggere gli oggetti ( Istruzione utile trovato su iternet ) */
                System.gc();    /* Questo dovrebbe teoricamente triggerare il garbage collector */

                /* Cambiamo la label dello stato */
                myGUI.lbl_serverip.setText("Server Ip (Stato: Disconnesso):");
            }
        });


        //
        // Gestore per l'invio dei messaggi
        //
        myGUI.btn_invia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.invia_messaggio(myGUI.txb_inputmsg.getText());
            }
        });


        return;
    }

}
