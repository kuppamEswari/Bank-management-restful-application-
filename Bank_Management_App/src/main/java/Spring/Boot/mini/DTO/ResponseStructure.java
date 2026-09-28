package Spring.Boot.mini.DTO;

import java.util.Optional;

import Spring.Boot.mini.Entity.Bank;

public class ResponseStructure<T> {
private int statusCode;
private String message;
private T data;
public int getStatusCode() {
	return statusCode;
}
public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public T getData() {
	return data;
}
public void setData(T data) {
	this.data = data;
}




}
