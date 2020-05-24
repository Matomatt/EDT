package View;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Utilisateurs.ConnectionViaUser;
import Utilisateurs.User;
import Utilitaires.ConnectionErrorException;
import Utilitaires.UserNotFoundException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class BaseWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 3528066671950303397L;
	
	JPanel mainWindow;
	User user = null;

	JButton  button1, button2, button3, button4, button11 = null;
        
	GridBagConstraints c = new GridBagConstraints();
        
	
	public BaseWindow(){
		this.setLayout(new GridBagLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		mainWindow = new LoginPanel(this);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 1;
		this.add(mainWindow, c);
		
		//addComponentsToPane();
		
		this.setVisible(true);
		this.pack();
	}
	
	public void addComponentsToPane() 
	{
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.gridwidth = 1;
		c.gridy = 0;
        c.ipady=35;
		button1 = new JButton("Emploi du temps");
		c.gridx = 0;
		this.add(button1, c);
        button1.addActionListener(this);

        button11 = new JButton("EDT liste");
		c.gridx = 1;
		this.add(button11, c);
        button11.addActionListener(this);
                
		button2 = new JButton("Récapitulatif des cours");
		c.gridx = 2;
		this.add(button2, c);
        button2.addActionListener(this);

		button3 = new JButton("Modifier");
		c.gridx = 3;
		this.add(button3, c);
        button3.addActionListener(this);
                
                
        button4 = new JButton("Reporting");
		c.gridx = 4;
		this.add(button4, c);
                
    }

	public void SwitchPage(JPanel newPage)
	{
		this.remove(mainWindow);
		
		mainWindow = newPage;
		
        c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 1;
		
		this.add(mainWindow, c);
		
		validate();
	}
	
	public boolean Connect(String login, String password) {
		login = "admin";
		password = "pw";
		
		try {
			System.out.println(login + " " + password);
			user = new ConnectionViaUser(login, password);
		} catch (UserNotFoundException | ClassNotFoundException | ConnectionErrorException e) {
			return false;
		}
                
        SwitchPage(new ModifAdminPanel(user));
        addComponentsToPane();
		System.out.println(user.getUtilisateurConnecte());

		return true;
	}

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource()==button1){
                try {
                    SwitchPage(new EdtGrillePanel(user));
                } catch (ParseException ex) {
                    Logger.getLogger(BaseWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(evt.getSource()==button11){
            	SwitchPage(new EDT_ListePanel(user));
            }
        	
            if(evt.getSource()== button3){
            	SwitchPage(new ModifAdminPanel(user));
            }
        }
        
      
           

}
