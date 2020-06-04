package Controllers.dataModifierWindowsControllers;

import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Controllers.ModifAdminPanelController;
import Utilisateurs.Utilisateur;
import Utilisateurs.User.UserType;
import Utilitaires.RandomString;
import View.dataModifierWindows.addUtilisateurWindow;

public class addUtilisateurWindowController extends dataModifierController
{
	addUtilisateurWindow window = null;
	
	public addUtilisateurWindowController(ModifAdminPanelController panelController) {
		super(panelController);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource().getClass() == JComboBox.class) 
		{
			window.ToggleJComboBoxLists();
		}
	}
	
	@Override
	protected void add() {
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

	@Override
	protected void modify() { }

	@Override
	public void setControlledView(JFrame window) {
		this.window = (addUtilisateurWindow) window;
	}
}
