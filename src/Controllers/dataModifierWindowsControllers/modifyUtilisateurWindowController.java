package Controllers.dataModifierWindowsControllers;

import javax.swing.JFrame;
import Controllers.ModifAdminPanelController;
import Utilisateurs.Utilisateur;
import Utilisateurs.User.UserType;
import View.dataModifierWindows.addUtilisateurWindow;

public class modifyUtilisateurWindowController extends dataModifierController 
{
	addUtilisateurWindow window = null;
	Utilisateur utilisateur = null;
	
	public modifyUtilisateurWindowController(ModifAdminPanelController panelController, Utilisateur utilisateur) 
	{
		super(panelController);
		this.utilisateur = utilisateur;
	}
	
	@Override
	protected void add() { }

	@Override
	protected void modify() {
		Utilisateur utilisateur = null;
		try {
			if (this.utilisateur.getType() == UserType.Etudiant)
				utilisateur = new Utilisateur(window.getEmail(), this.utilisateur.getPassword(), window.getNom(), window.getPrenom(), this.utilisateur.getType().toInt(), window.getGroupe(), this.utilisateur.getNumeroEtudiant());
			else
				utilisateur = new Utilisateur(window.getEmail(), this.utilisateur.getPassword(), window.getNom(), window.getPrenom(), this.utilisateur.getType().toInt(), window.getCours());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		this.utilisateur.copy(utilisateur);
		panelController.updateUtilisateur(this.utilisateur);
		window.dispose();
	}
	
	@Override
	public void setControlledView(JFrame window) {
		this.window = (addUtilisateurWindow) window;
		
		this.window.fillFields(utilisateur);
	}
}
