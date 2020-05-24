package Utilisateurs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Donnees.Donnee;
import Donnees.ListeDonnees;
import Groupes.ListeGroupes;
import Seances.Seance;
import Utilisateurs.User.UserType;

public class ListeUtilisateursImpl implements ListeUtilisateurs {
	Connection connection = null;
	
	ListeDonnees cours = null;
	ListeGroupes groupes = null;
	
	public ListeUtilisateursImpl(Connection conn, ListeDonnees _cours, ListeGroupes _groupes)
	{
		connection = conn;
		cours = _cours;
		groupes = _groupes;
	}
	
	private List<Utilisateur> ExecuteQuery(String query)
	{
		List<Utilisateur> list = new ArrayList<Utilisateur>();
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery(query);
			
			while(result.next())
			{
				if (result.getInt("Droit") == 3) //Enseignant
				{
					List<Donnee> coursList = new ArrayList<Donnee>();
					ResultSet resultEnseignant = connection.createStatement().executeQuery("Select * From enseignant Where ID_Utilisateur="+result.getInt("ID"));
					while(resultEnseignant.next())
						coursList.add(cours.GetByID(resultEnseignant.getInt("ID_Cours")));

					list.add(new Utilisateur(result.getInt("ID"), result.getString("Email"), result.getString("Nom"), result.getString("Prenom"), result.getInt("Droit"), coursList));

				}
				else if (result.getInt("Droit") == 4) //Etudiant
				{
					ResultSet resultEtudiant = connection.createStatement().executeQuery("Select * From etudiant Where ID_Utilisateur="+result.getInt("ID"));
					if (resultEtudiant.next())
						list.add(new Utilisateur(result.getInt("ID"), result.getString("Email"), result.getString("Nom"), result.getString("Prenom"), result.getInt("Droit"), groupes.getByID(resultEtudiant.getInt("ID_Groupe")), resultEtudiant.getInt("Numero")));
					else
						list.add(new Utilisateur(result.getInt("ID"), result.getString("Email"), result.getString("Nom"), result.getString("Prenom"), result.getInt("Droit")));
				}
				else
					list.add(new Utilisateur(result.getInt("ID"), result.getString("Email"), result.getString("Nom"), result.getString("Prenom"), result.getInt("Droit")));
		
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
		return ExecuteQuery("Select * From utilisateur Where ID IN (Select ID_Enseignant From seance_enseigants Where ID_Seance="+ID+")");
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
	public void Delete(Utilisateur utilisateur) {
		try {
			connection.createStatement().executeUpdate("DELETE From utilisateur Where ID="+utilisateur.getID());
								
			if (utilisateur.getType() == UserType.Etudiant)
				connection.createStatement().executeUpdate("DELETE From etudiant Where ID_Utilisateur="+utilisateur.getID());
			else
			{
				connection.createStatement().executeUpdate("DELETE From enseignant Where ID_Utilisateur="+utilisateur.getID());
				connection.createStatement().executeUpdate("DELETE From seance_enseigants Where ID_Enseignant="+utilisateur.getID()+";");
			}
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
