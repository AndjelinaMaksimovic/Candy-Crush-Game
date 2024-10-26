package GUIPackage;

import javax.swing.JButton;

public class IndexButton extends JButton 
{
	//private static final long serialVersionUID = 1L;
	private int i;
	private int j;
	
	public IndexButton(int i, int j) {
		super();
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}
	
	
}
