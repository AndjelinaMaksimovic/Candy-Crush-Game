package GUIPackage;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CustomJButton extends JButton 
{
	//private static final long serialVersionUID = 1L;
   
	private Color startColor;
    private Color endColor;
    private int sizeOfFont;
    private String fontName;
    
   
    public void setStartColor(Color startColor) {this.startColor = startColor;}

	public void setEndColor(Color endColor) {this.endColor = endColor;}

	public CustomJButton(ImageIcon icon, String text,int sizeOfFont, String fontName, Color startColor, Color endColor) 
	{
		super(icon); // setujemo imageIcon na dugme
        super.setText(text);
        this.sizeOfFont = sizeOfFont;
        this.fontName = fontName;
        this.setFont(new Font(fontName, Font.BOLD, sizeOfFont));
        this.startColor = startColor;
        this.endColor = endColor;
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        Graphics2D g2d = (Graphics2D) g.create();
        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, getHeight()/2, endColor);
        g2d.setPaint(gradientPaint);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // zaobljeno dugme

        super.paintComponent(g);
    }
}

