package ChatDINatale_Client.GUI;


//
// Imports
//
import javax.swing.*;
import java.awt.Color;
import java.net.InetAddress;
import ChatDINatale_Client.GUI.Componenti.Bordo;
import ChatDINatale_Client.GUI.Componenti.Componenti;


//
// Quest classe conterra tutta la 
// logica che verra applicata per la 
// nostra GUI ( Lato client ).
//
public class GUI implements Runnable {


    //
    // Componenti
    //
    // ( Le lasciamo pubbliche per facilita di accesso )
    //
    public JFrame finestra = null;
    public String font_path = "./fonts/Lato.ttf";

    /* Bottoni */
    public JButton btn_invia = null;
    public JButton btn_modifica = null;
    public JButton btn_connettiti = null;
    public JButton btn_disconetti = null;

    /* Aree di testo */
    public JTextArea msg_area = null;
    public JTextArea users_area = null;
    public JScrollPane scroll_pane = null;
    
    /* Campo di testo */
    public JTextField txb_username = null;
    public JPasswordField txb_password = null;
    public JTextField txb_serverip = null;
    public JTextField txb_inputmsg = null;

    /* Labels */
    public JLabel lbl_username = null;
    public JLabel lbl_password = null;
    public JLabel lbl_serverip = null;
    public JLabel lbl_areautenti = null;

    /* Colori */
    public Color pnl_background = Color.decode("#1e1e1e");
    public Color btn_foreground = Color.decode("#D9D9D9");
    public Color btn_background = Color.decode("#2e2e2e");
    public Color btn_active = Color.decode("#232323");
    public Color color_bordo = Color.decode("#979797");
    public Color textarea_foreground = Color.decode("#656565");
    public Color textarea_background = Color.decode("#B2B2B2");
    public Color textbox_foreground = Color.decode("#656565");
    public Color textbox_background = Color.decode("#B2B2B2");
    public Color textbox_active = Color.decode("#353535");


    //
    // Creiamo il costruttore che inizializzera la nostra finestra
    //
    public GUI() { }   


    //
    // Creiamo il nostro metodo che
    // disegnera la finestra
    //
    public void draw()
    {
        //
        // Creiamo la nostra finestra
        //
        this.finestra = new JFrame("ChatDiNatale x 5F x 2024-2025");
        this.finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.finestra.setSize(775, 625);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(this.pnl_background);


        //
        // Bottone di invio del messaggio
        //
        this.btn_invia = new JButton("Invia");
        this.btn_invia.setBounds(27, 535, 198, 35);
        this.btn_invia.setBackground(this.btn_background);
        this.btn_invia.setForeground(this.btn_foreground);
        this.btn_invia.setFont(Componenti.carica_font(this.font_path, 14));
        this.btn_invia.setBorder(new Bordo(4, this.color_bordo, 1));
        this.btn_invia.setFocusPainted(false);
        Componenti.aggiungi_gestore_btn(this.btn_invia, this.btn_active, this.btn_background);
        panel.add(this.btn_invia);


        //
        // Creiamo il bottone per la modifica del messaggio
        //
        this.btn_modifica = new JButton("Modifica");
        this.btn_modifica.setBounds(237, 534, 218, 36);
        this.btn_modifica.setBackground(this.btn_background);
        this.btn_modifica.setForeground(this.btn_foreground);
        this.btn_modifica.setFont(Componenti.carica_font(this.font_path, 14));
        this.btn_modifica.setBorder(new Bordo(4, this.color_bordo, 1));
        this.btn_modifica.setFocusPainted(false);
        Componenti.aggiungi_gestore_btn(this.btn_modifica, this.btn_active, this.btn_background);
        panel.add(this.btn_modifica);


        //
        // Area di testo nella quale verrano contenuti i messaggi
        //
        int text_area_size = 475;
        this.msg_area = new JTextArea("");
        this.msg_area.setBounds(27, 21, 428, text_area_size);
        this.msg_area.setFont(Componenti.carica_font(this.font_path, 14));
        this.msg_area.setBackground(this.textarea_background);
        this.msg_area.setForeground(this.textarea_foreground);
        this.msg_area.setBorder(new Bordo(2, this.color_bordo, 0));
        Componenti.aggiungi_gestore_txb(this.msg_area, "Messages:", this.textbox_active, this.textbox_foreground);
        this.msg_area.setFocusable(false);

        // Add the JTextArea to a JScrollPane
        this.scroll_pane = new JScrollPane(this.msg_area);
        this.scroll_pane.setBounds(27, 21, 428, text_area_size);
        this.scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(this.scroll_pane);


        //
        // Input del messaggio
        //
        this.txb_inputmsg = new JTextField("");
        this.txb_inputmsg.setBounds(27, 21 + text_area_size + 10, 428, 21);
        this.txb_inputmsg.setFont(Componenti.carica_font(this.font_path, 14));
        this.txb_inputmsg.setBackground(this.textbox_background);
        this.txb_inputmsg.setForeground(this.textbox_foreground);
        this.txb_inputmsg.setBorder(new Bordo(2, this.color_bordo, 0));
        Componenti.aggiungi_gestore_txb(this.txb_inputmsg, "Inserisci qui il tuo messaggio...", this.textbox_active, this.textbox_foreground);
        panel.add(this.txb_inputmsg);


        //
        // Label Server ip
        //
        this.lbl_serverip = new JLabel("Server Ip (Stato: Disconnesso):");
        this.lbl_serverip.setBounds(469, 22, 300, 17);
        this.lbl_serverip.setFont(Componenti.carica_font(this.font_path, 14));
        this.lbl_serverip.setForeground(this.btn_foreground);
        panel.add(this.lbl_serverip);


        //
        // TextBox per il server ip
        //
        this.txb_serverip = new JTextField("");
        this.txb_serverip.setBounds(468, 50, 241, 21);
        this.txb_serverip.setFont(Componenti.carica_font(this.font_path, 14));
        this.txb_serverip.setBackground(this.textbox_background);
        this.txb_serverip.setForeground(this.textbox_foreground);
        this.txb_serverip.setBorder(new Bordo(2, this.color_bordo, 0));
        Componenti.aggiungi_gestore_txb(this.txb_serverip, "Server ip:", this.textbox_active, this.textbox_foreground);
        try { this.txb_serverip.setText(InetAddress.getLocalHost().toString().split("/")[1]); }catch (Exception e ) {  e.printStackTrace();  }
        panel.add(this.txb_serverip);


        //
        // Label username
        //
        this.lbl_username = new JLabel("Username:");
        this.lbl_username .setBounds(469, 83, 106, 17);
        this.lbl_username .setFont(Componenti.carica_font(this.font_path, 14));
        this.lbl_username .setForeground(this.btn_foreground);
        panel.add(this.lbl_username);


        //
        // Textbox per lo useraname
        //
        this.txb_username = new JTextField("");
        this.txb_username.setBounds(469, 111, 242, 21);
        this.txb_username.setFont(Componenti.carica_font(this.font_path, 14));
        this.txb_username.setBackground(this.textbox_background);
        this.txb_username.setForeground(this.textbox_foreground);
        this.txb_username.setBorder(new Bordo(2, this.color_bordo, 0));
        Componenti.aggiungi_gestore_txb(this.txb_username, "Username", this.textbox_active, this.textbox_foreground);
        panel.add(this.txb_username);


        //
        // Label chat password
        //
        this.lbl_password = new JLabel("Chat Password:");
        this.lbl_password.setBounds(469, 139, 106, 17);
        this.lbl_password.setFont(Componenti.carica_font(this.font_path, 14));
        this.lbl_password.setForeground(this.btn_foreground);
        panel.add(this.lbl_password);

        
        //
        // Textbox per la password della chat
        //
        this.txb_password = new JPasswordField("");
        this.txb_password.setBounds(469, 164, 241, 21);
        this.txb_password.setFont(Componenti.carica_font(this.font_path, 14));
        this.txb_password.setBackground(this.textbox_background);
        this.txb_password.setForeground(this.textbox_foreground);
        this.txb_password.setBorder(new Bordo(2, this.color_bordo, 0));
        Componenti.aggiungi_gestore_txb(this.txb_password, "Chat Password", this.textbox_active, this.textbox_foreground);
        panel.add(this.txb_password);


        //
        // Bottone che permettera all'utente di connetersi
        //
        this.btn_connettiti = new JButton("Connettiti");
        this.btn_connettiti.setBounds(469, 199, 239, 29);
        this.btn_connettiti.setBackground(this.btn_background);
        this.btn_connettiti.setForeground(this.btn_foreground);
        this.btn_connettiti.setFont(Componenti.carica_font(this.font_path, 14));
        this.btn_connettiti.setBorder(new Bordo(4, this.color_bordo, 1));
        this.btn_connettiti.setFocusPainted(false);
        Componenti.aggiungi_gestore_btn(this.btn_connettiti, this.btn_active, this.btn_background);
        panel.add(this.btn_connettiti);


        //
        // Bottone per la disconessione
        //
        this.btn_disconetti = new JButton("Disconnettiti");
        this.btn_disconetti.setBounds(469, 237, 239, 30);
        this.btn_disconetti.setBackground(this.btn_background);
        this.btn_disconetti.setForeground(this.btn_foreground);
        this.btn_disconetti.setFont(Componenti.carica_font(this.font_path, 14));
        this.btn_disconetti.setBorder(new Bordo(4, this.color_bordo, 1));
        this.btn_disconetti.setFocusPainted(false);
        Componenti.aggiungi_gestore_btn(this.btn_disconetti, this.btn_active, this.btn_background);
        panel.add(this.btn_disconetti);


        //
        // Label degli utenti collegati
        //
        this.lbl_areautenti = new JLabel("Utenti Collegati:");
        this.lbl_areautenti.setBounds(469, 276, 106, 17);
        this.lbl_areautenti.setFont(Componenti.carica_font(this.font_path, 14));
        this.lbl_areautenti.setForeground(this.btn_foreground);
        panel.add(this.lbl_areautenti);
        

        //
        // Area che mostrera gli utenti collegati
        // 
        this.users_area = new JTextArea("");
        this.users_area.setBounds(471, 302, 233, 267);
        this.users_area.setFont(Componenti.carica_font(this.font_path, 14));
        this.users_area.setBackground(this.textarea_background);
        this.users_area.setForeground(this.textarea_foreground);
        this.users_area.setBorder(new Bordo(2, this.color_bordo, 0));
        Componenti.aggiungi_gestore_txb(this.users_area, "Users:", this.textbox_active, this.textbox_foreground);
        this.users_area.setFocusable(false);
        panel.add(this.users_area);


        //
        // Aggiungiamo il panello alla nostra finestra
        // e la rendiamo visibile.
        //
        this.finestra.add(panel);
        this.finestra.setVisible(true);
    }


    //
    // Questa funzione ci permettera di creare in maniera 
    // semplice una message box
    //
    public static void msg_box( String caption, String content ) {
        JOptionPane.showMessageDialog(
            null, 
            content,
            caption, 
            JOptionPane.INFORMATION_MESSAGE
        );
    }


    @Override
    public void run() {
        this.draw(); 
    }
    
}   
