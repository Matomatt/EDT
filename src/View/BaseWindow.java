package View;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BaseWindow extends JFrame {

	JPanel mainWindow;
	
	public BaseWindow() {
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1280, 720);

		mainWindow = new LoginPanel();
		this.add(mainWindow);		
		
		this.setVisible(true);
		validate();
	}
	
	public void Launch()
	{
		this.remove(mainWindow);
		mainWindow = new LoginPanel();
		this.add(mainWindow);
		validate();
	}
}
