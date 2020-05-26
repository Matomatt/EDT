package View;

import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class popup extends JFrame {

	public popup(String text) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setName("Pop-up");
		this.setLayout(new GridBagLayout());
		this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/3, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2);
	}
}
