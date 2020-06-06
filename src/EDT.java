import Controllers.BaseWindowController;
import Donnees.Donnee;
import Groupes.Groupe;
import Salles.Salle;
import Seances.Seance;
import Utilisateurs.User;
import Utilisateurs.Utilisateur;
import Utilitaires.ImageManager;
import Utilitaires.path;
import View.BaseWindow;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Classe permettant de lancer le programme
 *
 */
public class EDT {

	/**
	 * Fontion main du programme, crée le controller de la fenetre principale la fenêtre principale
	 * @param args
	 */
	public static void main(String args[])
	{
		try { ImageManager.LoadImage(path.getImagePath("login.png")); } catch (IOException e) 
		
		{ JOptionPane.showMessageDialog(new JFrame(), "Assurez d'avoir le dossier contenant les images dans le meme repertoire que le .jar"); }
		
		BaseWindowController controller = new BaseWindowController();
        new BaseWindow(controller);
	}
	
	/**
	 * Remplit les emplois du temps de tous les groupes aléatoirement (a utiliser ssi la bdd est vide)
	 * @param user
	 */
	@SuppressWarnings("unused")
	private static void RemplirSeances(User user)
	{
		if (true)
			return;
		
		List<Donnee> listeCours = user.ListeCours().getAll();
        List<Donnee> listeTypeCours = user.ListeType_cours().getAll();
        List<Salle> listeSalles = user.ListeSalles().getAll();
        
        Date currentTime = new Date();
        int countp = 0;
        List<Donnee> promotDonnees = user.ListePromotion().getAll();
        for (Donnee promo : promotDonnees)
        {
    		System.out.println("new promo");
    		List<Groupe> promotionGroupes = user.ListeGroupes().getByPromotion(promo);
    		int countg = 0;
        	for(Groupe groupe : promotionGroupes)
        	{
        		for (int s = 0; s < 9; s++) 
	        	{
					for (int i = 0; i < 5; i++) 
		    		{
						java.sql.Date date = new java.sql.Date(currentTime.getTime()-3*24*3600*1000+i*24*3600*1000+s*7*24*3600*1000); //Lundi 18/05/2020 c'est la semaine 21
						
		    			Random random = new Random();
		    			int r = random.nextInt(7);
		    			
						for (int j = 0; j < r; j++) 
						{
							float h; int m;
							h = j*1.75f+8.5f + (j>1?1.75f:0);
							m = (int) ((h-(int)h)*60);
							@SuppressWarnings("deprecation")
							Time heureDebut = new Time((int) h,m,0);
							h += 1.5f;
							m = (int) ((h-(int)h)*60);
							@SuppressWarnings("deprecation")
							Time heureFin = new Time((int) h,m,0);
							
							Donnee cours = listeCours.get(random.nextInt(listeCours.size()));
							Donnee type_cours = listeTypeCours.get(random.nextInt(listeTypeCours.size()));
							List<Utilisateur> listeEnseignants = user.ListeUtilisateurs().getEnseignantsByCours(cours);
							Utilisateur enseignant;
							do {
								if (listeEnseignants.size()>0)
									 enseignant = listeEnseignants.get(random.nextInt(listeEnseignants.size()));
								else
									enseignant = user.ListeUtilisateurs().getReferents().get(random.nextInt(user.ListeUtilisateurs().getReferents().size()));
							} while (user.ListeSeances().utilisateurLibre(enseignant, heureDebut, heureFin, date));
							
							
							Salle salle;
							do {
								salle = listeSalles.get(random.nextInt(listeSalles.size()));
								//System.out.println("keblo");
							} while (!user.ListeSeances().salleLibre(salle, heureDebut, heureFin, date));
							
							Seance toAddSeance = new Seance(21+s, date, heureDebut, heureFin, 2, cours, type_cours, groupe, enseignant, salle);
							user.ListeSeances().addSeance(toAddSeance);
							//System.out.println(toAdd);
							//System.out.println(salle + "(" + heureDebut + ", " + heureFin + ")");
							//System.out.println(cours + " : " + enseignant);
							//System.out.println(cours + " | " + type_cours);
							//System.out.println(d1 + " / " + date + " : " + heureDebut + " - " + heureFin);
						}//Heure
						float progress = (countp*promotionGroupes.size()*9*5) + countg*9*5 + s*5 + i;
						float goal = (promotDonnees.size()*promotionGroupes.size()*9*5);
						
						System.out.println(100*progress/goal+"%");
					}//Jour
				}//Semaine
        		countg++;
        	}//Groupe
        	countp++;
		}//Promo
        
        /*
           for (Utilisateur enseignant : user.ListeUtilisateurs().getEnseignants()) {
			System.out.println(enseignant);
			Seance lastSeance = null;
			for (Seance seance : user.ListeSeances().getByUtilisateur(enseignant)) {
				if (lastSeance==null)
					lastSeance = seance;
				else if (lastSeance.getDate().equals(seance.getDate()) && lastSeance.getDebut().equals(seance.getDebut()) && lastSeance.getFin().equals(lastSeance.getFin()))
					user.ListeSeances().Delete(seance);
				else
					lastSeance = seance;
			}
			
			System.out.println("------------\n");
		}
         */
	}
}
