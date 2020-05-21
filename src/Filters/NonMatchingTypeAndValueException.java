package Filters;

public class NonMatchingTypeAndValueException extends RuntimeException {

	private static final long serialVersionUID = -6544906105125529134L;

	public NonMatchingTypeAndValueException(String errorMessage)
	{
		super(errorMessage);
	}
}
