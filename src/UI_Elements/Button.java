package UI_Elements;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Button extends JButton {

	private static final long serialVersionUID = 669376052961263990L;

	public Button() {
		super();
		Init();
	}
	
	public Button(String text)
	{
		super(text);
		Init();
	}
	
	public Button(ImageIcon icon)
	{
		super(icon);
		Init();
	}
	
	private void Init()
	{
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setVerticalTextPosition(SwingConstants.CENTER);
		this.setFont(new java.awt.Font("Verdana", 0, 30));
		this.setBackground(new java.awt.Color(255, 255, 255));
		this.setBorder(null);
		this.setBorderPainted(true);
		this.setFocusable(true);
		this.setFocusPainted(true);
		this.setContentAreaFilled(true);
	}
}
