package ca.sheridancollege.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Contact implements Serializable{

	private static final long serialVersionUID = 4235490391446502600L;
	
	private int id;
	private String name;
    private String phone;
	private String address;
	private String email;
	private String role;
	private String[] roles = {"Guest", "Memeber", "Administrator"};
	
	
	
//	public Contact() {};
//	
//	public  Contact(String name, String phone, String address, String email) {
//		
//       setName(name);
//       setPhone(phone);
//       setAddress(address);
//       setEmail(email);
////		_name = name;
////		_phone = phone;
////		_address = address;
////		_email = email;
//		}
//	public String getName() {
//		return _name;
//	}
//	public String getPhone() {
//		return _phone;
//	}
//	public String getAddress() {
//		return _address;
//	}
//	public String getEmail() {
//		return _email;
//	}
//	
//	public void setName(String name) {
//		_name = name;
//	}
//	
//	public void setPhone(String phone) {
//		_phone = phone;
//	}
//	
//	public void setAddress(String address) {
//		_address = address;
//	}
//	
//	public void setEmail(String email) {
//		_email = email;
//	}
//	
//	public String toString () {
//		return String.format("Name: %s Phone Number: %s Address: %s Email: %s%n ", getName(), getPhone(), getAddress(), getEmail());
//	}
//	 
}
