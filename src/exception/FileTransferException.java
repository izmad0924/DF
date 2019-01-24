package exception;

public class FileTransferException extends RuntimeException {
	private static final long serialVersionUID = 7035751530756961525L;

	public FileTransferException() {
		super();
	}
	
	public FileTransferException(Exception e) {
		super(e);
	}
	
	public FileTransferException(String str) {
		super(str);
	}
}
