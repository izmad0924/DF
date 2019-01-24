package exception;

public class CopyException extends RuntimeException {
	
	private static final long serialVersionUID = -72558044724934643L;

	public CopyException(Exception e) {
		System.out.println(e);
	}

}
