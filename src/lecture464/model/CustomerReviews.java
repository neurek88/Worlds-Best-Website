package lecture464.model;

import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CustomerReviews {

	private int Id;
	
	public int getUserId() {
		return Id;
	}
	public void setUserId(int Id) {
		this.Id = Id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public CustomerReviews(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	

}
	
