package ChatDINatale_Client.GUI.Componenti;


//
// Imports
//
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.text.JTextComponent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


//
// Questa classe conterra alcune componenti
// che riguarderanno la nostra GUI in modo 
// tale da avere un aspetto migliore di quello
// basilare fornito da JFrame 
//
public class Componenti {
    

    //
    // Questo metodo ci permettera di caricare e di utilizzare 
    // un fonta nostra scelta al posto di usare quello predefinito
    //
    public static Font carica_font( String path, float size ) {
        try
        {
            //
            // Creiamo un nuovo font prendendolo 
            // da un file.
            //
            Font font = Font.createFont(
                Font.TRUETYPE_FONT, 
                new File(path)
            );


            /* Ci prendiamo il font della grandezza che desideriamo usare */
            font = font.deriveFont(size);

            /* Ritorniamo il nostro font */
            return font;
        }

        /* In caso fallisca ritorniamo il font basilare */
        catch ( IOException e ) { return new Font("Arial", Font.PLAIN, (int) size); }
        catch ( FontFormatException e ) { return new Font("Arial", Font.PLAIN, (int) size); }
    }

    
    //
    // Questa funzione ci permette di aggiungere un controllo al nostro
    // bottone in modo tale da bindarci una funzione che verra eseguita onClick
    // in questo modo ci assicuriamo che il background cambi quando lo hoveriamo 
    // come con l'hover di CSS
    //
    public static void aggiungi_gestore_btn( JButton button, Color pressedColor, Color originalColor )
    {
        /* Aggiungiamo il nostro gestore */
        button.addMouseListener( 
            new MouseAdapter() {
                

                //
                // Questa funzione verra chiamata quando 
                // il bottone viene premuto
                //
                @Override
                public void mousePressed(MouseEvent e) {
                    button.setContentAreaFilled(false);
                    button.setBackground(pressedColor);
                    button.setOpaque(true);
                    button.repaint();
                }


                //
                // Questo cambiera il colore quando 
                // il mouse verra rilasciato
                //
                @Override
                public void mouseReleased(MouseEvent e) {
                    button.setBackground(originalColor);
                    button.repaint();
                }

            }
        );
    }


    //
    // Questa funzione ci permette di aggiungere alla nostra textbox un gestore
    // che ci permette di capire quando la textbox e cliccata, e quindi cambiare
    // il colore o fare altre operazioni.
    //
    public static void aggiungi_gestore_txb(JTextComponent textComponent, String placeholderText, Color focusGainedColor, Color focusLostColor) {

        /* Impostiamo il nostro placeholder */
        textComponent.setText(placeholderText);

        /* Aggiungiamo un focus listener */
        textComponent.addFocusListener(new FocusAdapter() {

            //
            // Rimuoviamo il placeholder
            //
            @Override
            public void focusGained(FocusEvent e) {
                if (textComponent.getText().equals(placeholderText)) {
                    textComponent.setText("");
                    textComponent.setForeground(focusGainedColor);
                }
            }


            //
            // Aggiungiamo il placeholder in caso non ci sia del testo
            //
            @Override
            public void focusLost(FocusEvent e) {
                if (textComponent.getText().isEmpty()) {
                    textComponent.setText(placeholderText);
                    textComponent.setForeground(focusLostColor);
                }
            }
            
        });
    }

}
