package Spring.Boot.mini.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import Spring.Boot.mini.DTO.ResponseStructure;
@ControllerAdvice
public class globalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure> handleIDFE(IdNotFoundException exception){
		ResponseStructure<String> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("failure:id not found in the database");
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NotUniqueValue.class)
	public ResponseEntity<ResponseStructure> handleNUV(NotUniqueValue exception){
		ResponseStructure<String> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("failure:it should not be duplicate value:re-enter the unqiue value");
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RecordNotFound.class)
	public ResponseEntity<ResponseStructure> handleRNF(RecordNotFound exception){
		ResponseStructure<String> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("failure:record not found in the database");
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(LengthNotSupport.class)
	public ResponseEntity<ResponseStructure> handleLNS(LengthNotSupport exception){
		ResponseStructure<String> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("failure:length should be 10");
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
}
