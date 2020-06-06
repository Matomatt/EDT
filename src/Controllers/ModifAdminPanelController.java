package Controllers;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import Controllers.dataModifierWindowsControllers.addDonneeWindowController;
import Controllers.dataModifierWindowsControllers.addGroupeWindowController;
import Controllers.dataModifierWindowsControllers.addSalleWindowController;
import Controllers.dataModifierWindowsControllers.addSeanceWindowController;
import Controllers.dataModifierWindowsControllers.addUtilisateurWindowController;
import Controllers.dataModifierWindowsControllers.modifyDonneeWindowController;
import Controllers.dataModifierWindowsControllers.modifyGroupeWindowController;
import Controllers.dataModifierWindowsControllers.modifySalleWindowController;
import Controllers.dataModifierWindowsControllers.modifySeanceWindowController;
import Controllers.dataModifierWindowsControllers.modifyUtilisateurWindowController;
import Donnees.Donnee;
import Groupes.Groupe;
import Salles.Salle;
import Seances.Seance;
import UI_Elements.Button;
import UI_Elements.JScrollListe;
import Utilisateurs.Utilisateur;
import Utilisateurs.User.UserType;
import View.ModifAdminPanel;
import View.dataModifierWindows.addDonneeWindow;
import View.dataModifierWindows.addGroupeWindow;
import View.dataModifierWindows.addSalleWindow;
import View.dataModifierWindows.addSeanceWindow;
import View.dataModifierWindows.addUtilisateurWindow;

/**
 * Controlleur de la page modification des admins et referants
 */
public class ModifAdminPanelController extends Controller
{
	/**
	 * Gère les boutons ajouter / supprimer / modifier
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().getClass() == Button.class)
		{
			Button bt = (Button) e.getSource();
			Class<? extends Object> listClass = ((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getObjectClass();
			//System.out.println(bt.getName());
			switch (bt.getName()) 
			{
				case "btAjouter":
						if (listClass == Seance.class)
							new addSeanceWindow(Panel().getUser(), new addSeanceWindowController(this));
						else if (listClass == Utilisateur.class)
							new addUtilisateurWindow(Panel().getUser(), new addUtilisateurWindowController(this));
						else if(listClass == Donnee.class)
							new addDonneeWindow(Panel().getUser(), new addDonneeWindowController(this), ((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getNameString());
						else if (listClass == Salle.class)
							new addSalleWindow(Panel().getUser(), new addSalleWindowController(this));
						else if (listClass == Groupe.class)
							new addGroupeWindow(Panel().getUser(), new addGroupeWindowController(this));
						break;
						
				case "btSupprimer": for (Object o : ((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getSelectedValues()) { Supprimer(o); } break;
				
				case "btModifier":
						if (((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getSelectedValues().isEmpty())
							return;
						
						Object o = ((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getSelectedValues().get(0);
						
						if (listClass == Seance.class)
							new addSeanceWindow(Panel().getUser(), new modifySeanceWindowController(this,(Seance)o));
						else if (listClass == Utilisateur.class)
							new addUtilisateurWindow(Panel().getUser(), new modifyUtilisateurWindowController(this,(Utilisateur)o));
						else if(listClass == Donnee.class)
							new addDonneeWindow(Panel().getUser(), new modifyDonneeWindowController(this, (Donnee)o), ((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getNameString());
						else if (listClass == Salle.class)
							new addSalleWindow(Panel().getUser(), new modifySalleWindowController(this,(Salle)o));
						else if (listClass == Groupe.class)
							new addGroupeWindow(Panel().getUser(), new modifyGroupeWindowController(this,(Groupe)o));
						break;

				default: break;
			}
		}
	}
	
	/**
	 * Supprime l'objet indiqué de la liste et de la bbd
	 * @param o
	 */
	private void Supprimer(Object o)
	{
		((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).Delete(o);
		
		if (o.getClass() == Seance.class)
			Panel().getUser().ListeSeances().delete((Seance) o);
		if (o.getClass() == Utilisateur.class)
			Panel().getUser().ListeUtilisateurs().Delete((Utilisateur) o);
		if (o.getClass() == Groupe.class)
			Panel().getUser().ListeGroupes().delete((Groupe) o);
		if (o.getClass() == Salle.class)
			Panel().getUser().ListeSalles().delete((Salle) o);
		if (o.getClass() == Donnee.class)
		{
			if (((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getNameString() == "promotion")
				Panel().getUser().ListePromotion().delete((Donnee) o);
			else
				Panel().getUser().ListeCours().delete((Donnee) o);
		}
			
	}
	
	/**
	 * renvoie le panel dans la bonne class
	 * @return
	 */
	private ModifAdminPanel Panel()
	{
		return (ModifAdminPanel) panel;
	}

	/**
	 * Ajoute la seance dans la liste et la bdd
	 * @param seance
	 */
	public void addSeance(Seance seance) 
	{
		Panel().getUser().ListeSeances().addSeance(seance);
		if (((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).getObjectClass() == Seance.class)
			((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).addObject(seance);
	}

	/**
	 * Ajoute l'utilisateur dans la liste et la bdd
	 * @param utilisateur
	 */
	public void addUtilisateur(Utilisateur utilisateur) 
	{
		if (utilisateur.getType() == UserType.Etudiant)
		{
			utilisateur.setNumeroEtudiant(Panel().getUser().ListeUtilisateurs().getHighestStudentNumber()+1);
			JOptionPane.showMessageDialog(panel, "This student number is : "  + utilisateur.getNumeroEtudiant());
		}
		
		Panel().getUser().ListeUtilisateurs().addUtilisateur(utilisateur);
		for (int i = 0; i < Panel().getTabbedPanes().getTabCount(); i++) 
		{
			JScrollListe liste = (JScrollListe) Panel().getTabbedPanes().getComponentAt(i);
			
			if (liste != null)
			{
				if (liste.getObjectClass() == Utilisateur.class && liste.getUserType() == utilisateur.getType())
					liste.addObject(utilisateur);
			}
		}
	}

	/**
	 * Ajoute la donnée dans la liste et la bdd correspondant au type indiqué
	 * @param donnee
	 * @param type
	 */
	public void addDonnee(Donnee donnee, String type)
	{
		JScrollListe selectedList = (JScrollListe) Panel().getTabbedPanes().getSelectedComponent();
		
		if (selectedList.getObjectClass() == Donnee.class)
		{
			selectedList.addObject(donnee);
			if (type == "promotion")
				panel.getUser().ListePromotion().add(donnee);
			else
				panel.getUser().ListeCours().add(donnee);
		}
	}

	/**
	 * Ajoute la salle dans la liste et la bdd
	 * @param salle
	 */
	public void addSalle(Salle salle) 
	{
		JScrollListe selectedList = (JScrollListe) Panel().getTabbedPanes().getSelectedComponent();
		
		if (selectedList.getObjectClass() == Salle.class)
		{
			selectedList.addObject(salle);
			panel.getUser().ListeSalles().add(salle);
		}
	}
	
	/**
	 * Ajoute le groupe dans la liste et la bdd
	 * @param groupe
	 */
	public void addGroupe(Groupe groupe) 
	{
		JScrollListe selectedList = (JScrollListe) Panel().getTabbedPanes().getSelectedComponent();
		
		if (selectedList.getObjectClass() == Groupe.class)
		{
			selectedList.addObject(groupe);
			panel.getUser().ListeGroupes().add(groupe);
		}
	}

	/**
	 * Update la seance dans la liste et la bdd
	 * @param seance
	 */
	public void updateSeance(Seance seance) {
		panel.getUser().ListeSeances().update(seance);
		((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).validateCustom();
		panel.validate();
	}
	
	/**
	 * Update l'utilisateur dans la liste et la bdd
	 * @param utilisateur
	 */
	public void updateUtilisateur(Utilisateur utilisateur) 
	{
		panel.getUser().ListeUtilisateurs().update(utilisateur);
		
		if (utilisateur.getType() == UserType.Etudiant)
			JOptionPane.showMessageDialog(panel, "This student number is : "  + utilisateur.getNumeroEtudiant());

		((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).validateCustom();
		panel.validate();
	}

	/**
	 * Update la donnee dans la liste et la bdd indiqué par la string type
	 * @param donnee
	 * @param type
	 */
	public void updateDonnee(Donnee donnee, String type) {
		JScrollListe selectedList = (JScrollListe) Panel().getTabbedPanes().getSelectedComponent();
		
		if (selectedList.getObjectClass() == Donnee.class)
		{
			if (selectedList.getNameString() == "promotion")
				panel.getUser().ListePromotion().Update(donnee);
			else
				panel.getUser().ListeCours().Update(donnee);
		}
		
		((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).validateCustom();
		panel.validate();
	}

	/**
	 * Update la salle dans la liste et la bdd
	 * @param salle
	 */
	public void updateSalle(Salle salle) 
	{
		panel.getUser().ListeSalles().update(salle);
			
		((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).validateCustom();
		panel.validate();
	}
	
	/**
	 * Update le groupe dans la liste et la bdd
	 * @param groupe
	 */
	public void updateGroupe(Groupe groupe) 
	{
		panel.getUser().ListeGroupes().Update(groupe);
		
		((JScrollListe) Panel().getTabbedPanes().getSelectedComponent()).validateCustom();
		panel.validate();
	}
}
