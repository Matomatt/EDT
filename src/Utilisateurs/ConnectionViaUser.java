package Utilisateurs;


import java.sql.*;
import Donnees.ListeDonnees;
import Donnees.ListeDonneesImpl;
import Groupes.ListeGroupes;
import Groupes.ListeGroupesImpl;
import Salles.ListeSalles;
import Salles.ListeSallesImpl;
import Seances.ListeSeances;
import Seances.ListeSeancesImpl;
import Utilitaires.ConnectionErrorException;
import Utilitaires.UserNotFoundException;

public class ConnectionViaUser implements User {

	private String url = "jdbc:mysql://localhost:3306/edt?autoReconnect=true&useSSL=false";
	private String user = "root";
	private String passwd = "";
	
	private int ID = 0;
	
	private Connection connection = null;
	
	private ListeSalles listeSalles = null;
	private ListeGroupes listeGroupes = null;
	private ListeUtilisateurs listeUtilisateurs = null;
	private ListeSeances listeSeances = null;
	
	private ListeDonnees listeType_cours = null;
	private ListeDonnees listeCours = null;
	private ListeDonnees listeSite = null;
	private ListeDonnees listePromo = null;
	
	/********
	 * PUBLIC METHODS
	 */
	
	public ConnectionViaUser(String login, String password) throws UserNotFoundException, ClassNotFoundException, ConnectionErrorException
	{
		Class.forName("com.mysql.jdbc.Driver");
		
		System.out.println("Driver O.K.");

		try {
			connection = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ConnectionErrorException("There was an error trying to connect to the database");
		}
		
		System.out.println("Connexion effective !");
        
		if (!CheckLogin(login, password))
			throw new UserNotFoundException("Login or password incorrect for user " + login);
		
		
		listeType_cours = new ListeDonneesImpl(connection, "type_cours");
		listeCours = new ListeDonneesImpl(connection, "cours");
		listeSite = new ListeDonneesImpl(connection, "site");
		listePromo = new ListeDonneesImpl(connection, "promotion");
		
		listeSalles = new ListeSallesImpl(connection, listeSite);
		listeGroupes = new ListeGroupesImpl(connection, listePromo);
		listeUtilisateurs = new ListeUtilisateursImpl(connection, listeCours, listeGroupes);
		listeSeances = new ListeSeancesImpl(connection, listeCours, listeType_cours);
		
	}
	
	@Override
	public Utilisateur getUtilisateurConnecte() { return listeUtilisateurs.getByID(ID); }
	@Override
	public ListeSeances ListeSeances() { return listeSeances; }
	@Override
	public ListeUtilisateurs ListeUtilisateurs() { return listeUtilisateurs; }
	@Override
	public ListeGroupes ListeGroupes() { return listeGroupes; }
	@Override
	public ListeSalles ListeSalles() { return listeSalles; }
	
	@Override
	public ListeDonnees ListeType_cours() { return listeType_cours; }
	@Override
	public ListeDonnees ListeCours() { return listeCours; }
	@Override
	public ListeDonnees ListeSite() { return listeSite; }
	@Override
	public ListeDonnees ListePromotion() { return listePromo; }
	
	
	/********
	 * PRIVATE METHODS
	 */
	
	private boolean CheckLogin(String login, String password) throws ConnectionErrorException
	{
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery("Select ID, Nom, Prenom, Droit from utilisateur where Email='" + login + "' AND Password='" + password +"'");
			
			if (!result.next())
				return false;
			
			ID = result.getInt("ID");
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new ConnectionErrorException("Error when trying to identify user.");
		}
		
		return true;
	}
	
	
	/********
	 * PACKAGE RESTRICTED METHODS
	 */
	
	
}
