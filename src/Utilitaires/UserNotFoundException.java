package Utilitaires;

/**
 * Exception quand le mail et le password ne correspondent a aucun utilisateur
 */
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -2628374091582410937L;

	public UserNotFoundException(String errorMessage)
	{
		super(errorMessage);
	}
}
