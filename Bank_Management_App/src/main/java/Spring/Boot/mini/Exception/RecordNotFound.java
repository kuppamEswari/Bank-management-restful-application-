package Spring.Boot.mini.Exception;

public class RecordNotFound extends RuntimeException{

	public RecordNotFound(String message) {
		super(message);
	}

}
