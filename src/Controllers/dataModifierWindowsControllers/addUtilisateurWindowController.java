package Controllers.dataModifierWindowsControllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Controllers.ModifAdminPanelController;
import UI_Elements.Button;
import Utilisateurs.Utilisateur;
import Utilisateurs.User.UserType;
import Utilitaires.RandomString;
import View.dataModifierWindows.addUtilisateurWindow;

public class addUtilisateurWindowController implements ActionListener
{
	addUtilisateurWindow window = null;
	ModifAdminPanelController panelController = null;
	
	public addUtilisateurWindowController(ModifAdminPanelController panelController) {
		this.panelController = panelController;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass() == Button.class)
		{
			Button bt = (Button) e.getSource();
			//System.out.println(bt.getName());
			switch (bt.getName())
			{
				case "btAdd": addUtilisateur(); break;
				
				default: break;
			}
		}
		
	}
	
	private void addUtilisateur() {
		Utilisateur utilisateur = null;
		String password =  new RandomString(10).nextString();
		
		JOptionPane.showMessageDialog(window, "The password is : "  + password);
		
		if (window.getUserType() == UserType.Etudiant)
		{
			utilisateur = new Utilisateur(window.getEmail(), new String("password") , window.getNom(), window.getPrenom(), window.getUserType().toInt(), window.getGroupe(), 0);
		}
		else if (!window.getCours().isEmpty())
		{
			utilisateur = new Utilisateur(window.getEmail(), new String("password") , window.getNom(), window.getPrenom(), window.getUserType().toInt(), window.getCours());
		}
		else
			utilisateur = new Utilisateur(window.getEmail(), new String("password") , window.getNom(), window.getPrenom(), window.getUserType().toInt());
		
		panelController.addUtilisateur(utilisateur);
		window.dispose();
	}

	public void setControlledView(addUtilisateurWindow addUtilisateurWindow) {
		window = addUtilisateurWindow;
	}

}
