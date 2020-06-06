package Utilisateurs;


import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

/**
 * Se connecte a la bdd avec identifiant et mdp puis initialise toutes les interfaces dao
 */
public class ConnectionViaUser implements User {

	private String url = "jdbc:mysql://localhost:3306/edt?autoReconnect=true&useSSL=false";
	private String user = "root";
	private String passwd = "";
	
	private int ID = 0;
	private UserType userType = UserType.none;
	
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
	
    /****
     * Se connecte a un utilisateur et génère toutes les interfaces d'accès a la bdd
     * @param login
     * @param password
     * @throws UserNotFoundException
     * @throws ClassNotFoundException
     * @throws ConnectionErrorException
     */
	public ConnectionViaUser(String login, String password) throws UserNotFoundException, ClassNotFoundException, ConnectionErrorException
	{
		Class.forName("com.mysql.jdbc.Driver");
		
		System.out.println("Driver O.K.");

		try {
			String s = (String)JOptionPane.showInputDialog(new JFrame(), "Entrez le nom de la bdd", "BDD", JOptionPane.PLAIN_MESSAGE, null,  null, "edt");

			if ((s != null) && (s.length() > 0)) {
			    url = "jdbc:mysql://localhost:3306/"+s+"?autoReconnect=true&useSSL=false";
			}
			
			connection = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connected to the database (user not verified yet)...");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Connection error : " + e.getLocalizedMessage());
			throw new ConnectionErrorException("There was an error trying to connect to the database");
		}
		
		if (!CheckLogin(login, password))
			throw new UserNotFoundException("Login or password incorrect for user " + login);
		
		System.out.println("User verified !");
		
		listeType_cours = new ListeDonneesImpl(connection, "type_cours");
		listeCours = new ListeDonneesImpl(connection, "cours");
		listeSite = new ListeDonneesImpl(connection, "site");
		listePromo = new ListeDonneesImpl(connection, "promotion");
		
		listeSalles = new ListeSallesImpl(connection, listeSite);
		listeGroupes = new ListeGroupesImpl(connection, listePromo);
		listeUtilisateurs = new ListeUtilisateursImpl(connection, listeCours, listeGroupes);
		listeSeances = new ListeSeancesImpl(connection, listeCours, listeType_cours, listeGroupes, listeUtilisateurs, listeSalles);
		
		userType = getUtilisateurConnecte().getType();
	}
	
	@Override
	public Utilisateur getUtilisateurConnecte() { return listeUtilisateurs.getByID(ID); }
	@Override
	public UserType getUserType() { return userType; }
	
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
}
