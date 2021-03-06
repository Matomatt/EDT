package Utilisateurs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Donnees.Donnee;
import Donnees.ListeDonnees;
import Groupes.ListeGroupes;
import Seances.Seance;
import Utilisateurs.User.UserType;

/**
 * Se connecte a la bdd pour gérer les utilisateurs
 */
public class ListeUtilisateursImpl implements ListeUtilisateurs {
	Connection connection = null;
	
	ListeDonnees cours = null;
	ListeGroupes groupes = null;
	
	/**
	 * Constructeur, recupère les interfaces qui accède aux cours et groupes
	 * @param conn
	 * @param _cours
	 * @param _groupes
	 */
	public ListeUtilisateursImpl(Connection conn, ListeDonnees _cours, ListeGroupes _groupes)
	{
		connection = conn;
		cours = _cours;
		groupes = _groupes;
	}
	
	/**
	 * Execute la query indiquée et renvoie la List avec toutes les utilisateurs trouvées
	 * @param query
	 * @return
	 */
	private List<Utilisateur> ExecuteQuery(String query)
	{
		List<Utilisateur> list = new ArrayList<Utilisateur>();
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery(query + " ORDER BY Nom ASC, Prenom ASC");
			
			while(result.next())
			{
				if (result.getInt("Droit") == UserType.Enseignant.toInt() || result.getInt("Droit") == UserType.Referent_pedagogique.toInt()) //Enseignant
				{
					List<Donnee> coursList = new ArrayList<Donnee>();
					ResultSet resultEnseignant = connection.createStatement().executeQuery("Select * From enseignant Where ID_Utilisateur="+result.getInt("ID"));
					while(resultEnseignant.next())
						coursList.add(cours.GetByID(resultEnseignant.getInt("ID_Cours")));

					list.add(new Utilisateur(result.getInt("ID"), result.getString("Email"), result.getString("Password"), result.getString("Nom"), result.getString("Prenom"), result.getInt("Droit"), coursList));
				}
				else if (result.getInt("Droit") == UserType.Etudiant.toInt()) //Etudiant
				{
					ResultSet resultEtudiant = connection.createStatement().executeQuery("Select * From etudiant Where ID_Utilisateur="+result.getInt("ID"));
					if (resultEtudiant.next())
						list.add(new Utilisateur(result.getInt("ID"), result.getString("Email"), result.getString("Password"), result.getString("Nom"), result.getString("Prenom"), result.getInt("Droit"), groupes.getByID(resultEtudiant.getInt("ID_Groupe")), resultEtudiant.getInt("Numero")));
					else
						list.add(new Utilisateur(result.getInt("ID"), result.getString("Email"), result.getString("Password"), result.getString("Nom"), result.getString("Prenom"), result.getInt("Droit")));
				}
				else
					list.add(new Utilisateur(result.getInt("ID"), result.getString("Email"), result.getString("Password"), result.getString("Nom"), result.getString("Prenom"), result.getInt("Droit")));
		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<Utilisateur> getAll()
	{
		return ExecuteQuery("Select * from utilisateur");
	}

	@Override
	public Utilisateur getByID(int ID) 
	{  
		try
		{
			return ExecuteQuery("Select * From utilisateur Where ID="+ID).get(0); 
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Utilisateur> getEnseignants() {
		return ExecuteQuery("Select * From utilisateur Where Droit=3");
	}

	@Override
	public List<Utilisateur> getEnseignantsByCours(Donnee cours) {
		try {
			return ExecuteQuery("Select * from utilisateur Where ID IN (Select ID_Utilisateur from enseignant Where ID_Cours="+cours.getID()+")");
		}
		catch (Exception e) { e.printStackTrace(); return null; }
		
	}
	
	@Override
	public List<Utilisateur> getEnseignantsBySeance(Seance seance) {
		return getEnseignantsBySeanceID(seance.getID());
	}

	@Override
	public List<Utilisateur> getEnseignantsBySeanceID(int ID) {
		return ExecuteQuery("Select * From utilisateur Where ID IN (Select ID_Enseignant From seance_enseignants Where ID_Seance="+ID+")");
	}

	@Override
	public List<Utilisateur> getReferents() {
		return ExecuteQuery("Select * From utilisateur Where Droit=2");
	}
	
	@Override
	public List<Utilisateur> getEtudiants() {
		return ExecuteQuery("Select * From utilisateur Where Droit=4");
	}
	
	@Override
	public int getHighestStudentNumber() 
	{
		int numero = 9999998;
		try {
			ResultSet result = connection.createStatement().executeQuery("Select MAX(Numero) as num From etudiant");
			
			if(result.next())
				numero=result.getInt("num");
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 9999998;
		}
		
		return numero;
	}

	@Override
	public void Delete(Utilisateur utilisateur) {
		try {
			connection.createStatement().executeUpdate("DELETE From utilisateur Where ID="+utilisateur.getID());
								
			if (utilisateur.getType() == UserType.Etudiant)
				connection.createStatement().executeUpdate("DELETE From etudiant Where ID_Utilisateur="+utilisateur.getID());
			else
			{
				connection.createStatement().executeUpdate("DELETE From enseignant Where ID_Utilisateur="+utilisateur.getID());
				connection.createStatement().executeUpdate("DELETE From seance_enseignants Where ID_Enseignant="+utilisateur.getID()+";");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addUtilisateur(Utilisateur utilisateur) {
		try {
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("INSERT INTO `utilisateur` (`ID`, `Email`, `Password`, `Nom`, `Prenom`, `Droit`) "
					+ "VALUES (NULL, '"+utilisateur.getEmail()+"', '"+utilisateur.getPassword()+"', '"+utilisateur.getNom()
					+"', '"+utilisateur.getPrenom()+"', '"+utilisateur.getType().toInt()+"');", Statement.RETURN_GENERATED_KEYS);
			
			ResultSet keysResultSet = statement.getGeneratedKeys();
			if (keysResultSet != null && keysResultSet.next())
				utilisateur.setID((int)keysResultSet.getLong(1));
			
			if (utilisateur.getType() == UserType.Etudiant)
				connection.createStatement().executeUpdate("INSERT INTO `etudiant` (`ID_Utilisateur`, `Numero`, `ID_Groupe`) VALUES ('"+utilisateur.getID()+"', '"+utilisateur.getNumeroEtudiant()+"', '"+utilisateur.getGroupe().getID()+"');");
			else
			{
				for (Donnee coursUtil: utilisateur.getCoursDonnes()) {
					connection.createStatement().executeUpdate("INSERT INTO `enseignant` (`ID_Utilisateur`, `ID_Cours`) VALUES ('"+utilisateur.getID()+"', '"+coursUtil.getID()+"');");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Utilisateur utilisateur) 
	{
		try {
			connection.createStatement().executeUpdate("UPDATE `utilisateur` SET `Email` = '"+utilisateur.getEmail()+"', `Password` = '"+utilisateur.getPassword()+"', "
					+ "`Nom` = '"+utilisateur.getNom()+"', `Prenom` = '"+utilisateur.getPrenom()+"', `Droit` = '"+utilisateur.getType().toInt()+"' WHERE `utilisateur`.`ID` = "+utilisateur.getID()+";");
			
			connection.createStatement().executeUpdate("DELETE From etudiant Where ID_Utilisateur="+utilisateur.getID());
			connection.createStatement().executeUpdate("DELETE From enseignant Where ID_Utilisateur="+utilisateur.getID());
			
			if (utilisateur.getType() == UserType.Etudiant)
				connection.createStatement().executeUpdate("INSERT INTO `etudiant` (`ID_Utilisateur`, `Numero`, `ID_Groupe`) VALUES ('"+utilisateur.getID()+"', '"+utilisateur.getNumeroEtudiant()+"', '"+utilisateur.getGroupe().getID()+"');");
			else
			{
				for (Donnee coursUtil: utilisateur.getCoursDonnes()) {
					connection.createStatement().executeUpdate("INSERT INTO `enseignant` (`ID_Utilisateur`, `ID_Cours`) VALUES ('"+utilisateur.getID()+"', '"+coursUtil.getID()+"');");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
