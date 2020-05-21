import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

import Donnees.Donnee;
import Filters.Filter;
import Filters.Filter.Filters;
import Groupes.Groupe;
import Salles.Salle;
import Seances.Seance;
import Utilisateurs.ConnectionViaUser;
import Utilisateurs.User;
import Utilisateurs.Utilisateur;
import Utilitaires.ConnectionErrorException;
import Utilitaires.UserNotFoundException;
import sun.tools.jar.resources.jar;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class EDT {

	
	public static void main(String args[])
	{
        User user = null;
        try {
        	user = new ConnectionViaUser("admin", "pw");

			System.out.println(user.getUtilisateurConnecte() + " connected");
		} catch (UserNotFoundException | ClassNotFoundException | ConnectionErrorException e) {
			e.printStackTrace();
			return;
		}
        
        
        //RemplirSeances(user);
        
        //Tests(user);
       //TestsHistogram();
	}
	
	private static void Tests(User user)
	{
		for (Donnee d : user.ListePromotion().getAll()) {
    		System.out.println(d);
		}
        
        for (Seance s : user.ListeSeances().getAll()) {
			System.out.println(s);
		}
        
        for (Utilisateur u : user.ListeUtilisateurs().getAll()) {
        	System.out.println(u);
        }
        
        try { //Le try catch si le nom du cours n'existe pas dans la BDD
			for (Utilisateur u : user.ListeUtilisateurs().getEnseignantsByCours(user.ListeCours().getByNom("Géométrie"))) {
	        	System.out.println(u);
	        }
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        for (Utilisateur u : user.ListeUtilisateurs().getEnseignantsByCours(user.ListeCours().getByNom("Probabilités et statistiques"))) {
        	System.out.println(u);
        }
        
        for (Groupe g : user.ListeGroupes().getAll()) {
        	System.out.println(g);
        }
        
        for (Salle s : user.ListeSalles().getAll()) {
        	System.out.println(s);
        }
        
        //Ce fonctionnement pour remplir la liste est un peu fastidieux je le changerais surement mais la requete avec les filtres customs fonctionnent en tout cas
        Filter[] filters = null;
        
        try {
        	 Filter[] f = {new Filter(Filters.Nom, new String("Anglais")), new Filter(Filters.Nom, new String("Japonais"))};
        	 filters = f;
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println("Cours trouvés :");
        for (Donnee c : user.ListeCours().getFilteredBy(filters))
        {
        	System.out.println(c+" ");
        	
        	/*
        	c.setValue(c.getValue()+"2");
        	if (user.ListeCours().Update(c))
        		System.out.println("Updated !");
        	*/
        }
   		 	
        
        filters = null;
        
        try {
        	 Filter[] f = {new Filter(Filters.Promotion, user.ListePromotion().GetByID(3)), new Filter(Filters.Promotion, user.ListePromotion().GetByID(2)), new Filter(Filters.Nom, new String("Groupe 01")), new Filter(Filters.Nom, new String("Groupe 04"))};//{new Filter(Filters.Nom, new String("Groupe 01"))};
        	 filters = f;
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println("Groupes trouvés :");
        for (Groupe g : user.ListeGroupes().getFilteredBy(filters))
        {
        	System.out.println(g);
        	
        	/*
        	g.setName(g.getName()+"Z");
        	g.setPromotion(user.ListePromotion().GetByID(1));
        	
        	if (user.ListeGroupes().Update(g))
        		System.out.println(" Updated !");
        		*/
        }
   		 	
        
        System.out.println("\n\ndone");
	}
	
	private static void TestsHistogram()
	{
		double[] vals = {
                0.71477137, 0.55749811, 0.50809619, 0.47027228, 0.25281568,
                0.66633175, 0.50676332, 0.6007552, 0.56892904, 0.49553407,
                0.61093935, 0.65057417, 0.40095626, 0.45969447, 0.51087888,
                0.52894806, 0.49397198, 0.4267163, 0.54091298, 0.34545257,
                0.58548892, 0.3137885, 0.63521146, 0.57541744, 0.59862265,
                0.66261386, 0.56744017, 0.42548488, 0.40841345, 0.47393027,
                0.60882106, 0.45961208, 0.43371424, 0.40876484, 0.64367337,
                0.54092033, 0.34240811, 0.44048106, 0.48874236, 0.68300902,
                0.33563968, 0.58328107, 0.58054283, 0.64710522, 0.37801285,
                0.36748982, 0.44386445, 0.47245989, 0.297599, 0.50295541,
                0.39785732, 0.51370486, 0.46650358, 0.5623638, 0.4446957,
                0.52949791, 0.54611411, 0.41020067, 0.61644868, 0.47493691,
                0.50611458, 0.42518211, 0.45467712, 0.52438467, 0.724529,
                0.59749142, 0.45940223, 0.53099928, 0.65159718, 0.38038268,
                0.51639554, 0.41847437, 0.46022878, 0.57326103, 0.44913632,
                0.61043611, 0.42694949, 0.43997814, 0.58787928, 0.36252603,
                0.50937634, 0.47444256, 0.57992527, 0.29381335, 0.50357977,
                0.42469464, 0.53049697, 0.7163579, 0.39741694, 0.41980533,
                0.68091159, 0.69330702, 0.50518926, 0.55884098, 0.48618324,
                0.48469854, 0.55342267, 0.67159111, 0.62352006, 0.34773486};


        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("key", vals, 50);

        JFreeChart histogram = ChartFactory.createHistogram("Ca fonctione ahah !",
                "y values", "x values", dataset);

        try {
			ChartUtils.saveChartAsPNG(new File("histogram.png"), histogram, 450, 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void RemplirSeances(User user)
	{
		List<Donnee> listeCours = user.ListeCours().getAll();
        List<Donnee> listeTypeCours = user.ListeType_cours().getAll();
        List<Salle> listeSalles = user.ListeSalles().getAll();
        
        Date currentTime = new Date();
        int countp = 0; int countg = 0;
        List<Donnee> promotDonnees = user.ListePromotion().getAll();
        for (Donnee promo : promotDonnees)
        {
        	if (promo.getID() != 1)
        	{
        		List<Groupe> promotionGroupes = user.ListeGroupes().getByPromotion(promo);
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
    							Utilisateur enseignant = user.ListeUtilisateurs().getReferents().get(random.nextInt(user.ListeUtilisateurs().getReferents().size()));
    							if (listeEnseignants.size()>0)
    								 enseignant = listeEnseignants.get(random.nextInt(listeEnseignants.size()));
    							
    							Salle salle;
    							do {
    								salle = listeSalles.get(random.nextInt(listeSalles.size()));
    							} while (!user.ListeSeances().salleLibre(salle, heureDebut, heureFin));
    							
    							Seance toAddSeance = new Seance(21+s, date, heureDebut, heureFin, 2, cours, type_cours, groupe, enseignant, salle);
    							user.ListeSeances().addSeance(toAddSeance);
    							//System.out.println(toAdd);
    							//System.out.println(salle + "(" + heureDebut + ", " + heureFin + ")");
    							//System.out.println(cours + " : " + enseignant);
    							//System.out.println(cours + " | " + type_cours);
    							//System.out.println(d1 + " / " + date + " : " + heureDebut + " - " + heureFin);
    						}//Heure
    						float progress = (countp*promotionGroupes.size()*9*5) + countp*9*5 + s*5 + i;
    						float goal = (promotDonnees.size()*promotionGroupes.size()*9*5);
    						
    						System.out.println(100*progress/goal+"%");
    					}//Jour
    				}//Semaine
            		countg++;
            	}//Groupe
            	
        	}
        	countp++;
		}//Promo
	}
}
