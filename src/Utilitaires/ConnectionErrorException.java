package Utilitaires;

/**
 * Exception quand il y a eu un probleme de connexion a la bdd
 */
public class ConnectionErrorException extends Exception {

	private static final long serialVersionUID = 361110119355211342L;

	public ConnectionErrorException(String errorMessage)
	{
		super(errorMessage);
	}
}
