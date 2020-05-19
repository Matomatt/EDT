package Utilitaires;

public class ConnectionErrorException extends RuntimeException {

	private static final long serialVersionUID = 361110119355211342L;

	public ConnectionErrorException(String errorMessage)
	{
		super(errorMessage);
	}
}
