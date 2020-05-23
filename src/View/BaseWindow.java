package View;


import Seances.Seance;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Utilisateurs.ConnectionViaUser;
import Utilisateurs.User;
import Utilitaires.ConnectionErrorException;
import Utilitaires.UserNotFoundException;
import java.awt.Dimension;
import View.EDT_Grille;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;


import java.text.SimpleDateFormat;
import java.util.Date;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Utilisateurs.ConnectionViaUser;
import Utilisateurs.User;
import Utilitaires.ConnectionErrorException;
import Utilitaires.UserNotFoundException;
import java.awt.Dimension;
import View.EDT_Grille;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.text.DateFormat;  
import java.util.Calendar;


public class BaseWindow extends JFrame implements ActionListener{

	JPanel mainWindow;
	User user = null;
	JButton  button1, button2, button3, button4 = null;
        
	GridBagConstraints c = new GridBagConstraints();
        
	
	public BaseWindow() {
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
                
               
		button2 = new JButton("Récapitulatif des cours");
		c.gridx = 1;
		this.add(button2, c);
                
                
	
		button3 = new JButton("Mofifier");
		c.gridx = 2;
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
	}
	
	public boolean Connect(String login, String password) throws ParseException {
		login = "admin";
		password = "pw";
		try {
			System.out.println(login + " " + password);
			user = new ConnectionViaUser(login, password);
			SwitchPage(new ModifAdminPanel(user));
			addComponentsToPane();
                        transformerDate();
			System.out.println(user.getUtilisateurConnecte());
			return true;
		} catch (UserNotFoundException | ClassNotFoundException | ConnectionErrorException e) {
			return false;
		}
		
	}

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            //if(evt.getSource()==button1){
            //SwitchPage(new EdtGrillePanel(user));
            //}
            
            if(evt.getSource()== button3){
            SwitchPage(new ModifAdminPanel(user));
            }
        }
        
      public void transformerDate() throws ParseException{
          String pattern = "dd-MM-yyyy";
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
          Date date = null;
          DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  

                   System.out.println(user.ListeSeances().getByUtilisateur(user.getUtilisateurConnecte()));

          
          
          for(Seance s : user.ListeSeances().getByUtilisateur(user.getUtilisateurConnecte())){
          System.out.println( "zizi");
              s.getDate();
              
             
              String strDate = dateFormat.format(s.getDate()); 
        date = simpleDateFormat.parse(strDate);
        System.out.println( date);
        
            }
      }
           

}
