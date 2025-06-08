package ChatDINatale_Client.GUI.Componenti;


//
// Imports
//
import java.awt.*;
import javax.swing.border.Border;


//
// Questa classe ci permettera di 
// creare una finestra con un bordo
// arrotondato in modo tale da dargli  
// un look migliore 
//
public class Bordo implements Border {


    //
    // Attrbuti
    //
    private final int radius;
    private final Color color;
    private final int thickness;


    //
    // Costruttore
    //
    public Bordo(int radius, Color color, int thickness) {
        this.radius = radius;
        this.color = color;
        this.thickness = thickness;
    }


    
    //
    // Controlliamo se il bordo ha deegli insetti
    //
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.thickness, this.thickness, this.thickness, this.thickness);
    }


    //
    // Controlliamo se il bordo e opaco
    //
    @Override
    public boolean isBorderOpaque() {
        return true;
    }


    //
    // Disegnamo il bordo
    //
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Graphics2D g2d = (Graphics2D) g.create();

      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      g2d.setColor(this.color);
      g2d.setStroke(new BasicStroke((float) this.thickness));

      int offset = this.thickness / 2;
      int arc = this.radius;

      int adjustedWidth = width - this.thickness;
      int adjustedHeight = height - this.thickness;

      g2d.drawRoundRect(
          x + offset,
          y + offset,
          adjustedWidth,
          adjustedHeight,
          arc,
          arc
      );

      g2d.dispose();
    }

}