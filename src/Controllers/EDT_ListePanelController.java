package Controllers;

import java.awt.event.ActionEvent;

import Donnees.Donnee;
import Groupes.Groupe;
import Salles.Salle;
import Utilisateurs.Utilisateur;
import Utilisateurs.User.UserType;
import View.EDT_ListePanel;

/**
 * Controller de l'emploi du temps sous forme de liste
 */
public class EDT_ListePanelController extends Controller {
	
	/**
	 * Recharge l'edt en fonction de la selection 
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		EDT_ListePanel pan = (EDT_ListePanel)panel;
		
		pan.removeAll();
		
		switch (pan.getListeSelectedItem().toString()) 
		{
            case "Mon EDT": pan.display_courses(panel.getUser().ListeSeances().getByUtilisateurAtWeek(panel.getUser().getUtilisateurConnecte(), pan.getSemaine()));
                    	break;
			case "Salle": pan.sallesAddToComboBox(); 
						if (pan.getUser().getUserType() == UserType.Etudiant || pan.getUser().getUserType() == UserType.Enseignant)
							pan.display_courses(panel.getUser().ListeSeances().getBySalleAtWeek(pan.getUser().getUtilisateurConnecte(), (Salle) pan.getSelectedItem(), pan.getSemaine()));
						else
							pan.display_courses(panel.getUser().ListeSeances().getBySalleAtWeek((Salle) pan.getSelectedItem(), pan.getSemaine()));
                        break;
			
			case "Enseignant": pan.nomsAddToComboBox();
						if (pan.getUser().getUserType() == UserType.Etudiant || pan.getUser().getUserType() == UserType.Enseignant)
							pan.display_courses(panel.getUser().ListeSeances().getByUtilisateurAtWeek(pan.getUser().getUtilisateurConnecte(), (Utilisateur) pan.getSelectedItem(), pan.getSemaine()));
						else
							pan.display_courses(panel.getUser().ListeSeances().getByUtilisateurAtWeek((Utilisateur) pan.getSelectedItem(), pan.getSemaine()));
			            break;
			
			case "Classe": pan.classesAddToComboBox(); 
						if (pan.getUser().getUserType() == UserType.Etudiant || pan.getUser().getUserType() == UserType.Enseignant)
							pan.display_courses(panel.getUser().ListeSeances().getByGroupeAtWeek(pan.getUser().getUtilisateurConnecte(), (Groupe) pan.getSelectedItem(), pan.getSemaine()));
						else
							pan.display_courses(panel.getUser().ListeSeances().getByGroupeAtWeek((Groupe) pan.getSelectedItem(), pan.getSemaine()));
			            break;
			
			case "Promo": pan.promosAddToComboBox();
						if (pan.getUser().getUserType() == UserType.Etudiant || pan.getUser().getUserType() == UserType.Enseignant)
							pan.display_courses(panel.getUser().ListeSeances().getByPromoAtWeek(pan.getUser().getUtilisateurConnecte(), (Donnee) pan.getSelectedItem(), pan.getSemaine()));
						else
							pan.display_courses(panel.getUser().ListeSeances().getByPromoAtWeek((Donnee) pan.getSelectedItem(), pan.getSemaine()));
			            break;
					
			default: break;
		}
		
		pan.repaintAll();	
	}
}
