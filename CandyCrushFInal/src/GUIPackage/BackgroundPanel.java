package GUIPackage;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel 
{
	//private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private float opacity;

    public BackgroundPanel(Image backgroundImage, float opacity) 
    {
        this.backgroundImage = backgroundImage;
        this.opacity = opacity;
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
    }
}
