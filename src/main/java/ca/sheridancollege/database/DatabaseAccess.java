package ca.sheridancollege.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

import ca.sheridancollege.beans.Contact;
import ca.sheridancollege.beans.User;

@Repository
public class DatabaseAccess {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	
	
	public void addContact(Contact contact) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO CONTACTS (name, phone, address, email, role)"
				+ " VALUES (:name, :phone, :address, :email, :role)";
		//parameters.addValue("id", drink.getId());
		parameters.addValue("name", contact.getName());
		parameters.addValue("phone", contact.getPhone());
		parameters.addValue("address", contact.getAddress());
		parameters.addValue("email", contact.getEmail());
		parameters.addValue("role", contact.getRole());
		jdbc.update(query, parameters);
		//Map<key,Value>
	}
	
	public  ArrayList<Contact> getContacts(){
		 ArrayList<Contact> contacts = new ArrayList<Contact>();
		String query = "SELECT * FROM CONTACTS";
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		for(Map<String, Object> row: rows) {
			Contact c = new Contact();
			c.setId((Integer)row.get("id"));
			c.setName((String)row.get("name")); 
			c.setPhone((String)row.get("phone"));
			c.setAddress((String)row.get("address"));
			c.setEmail((String)row.get("email"));
			c.setRole((String)row.get("role"));
			contacts.add(c);
		}
		return contacts;
	}
	
	public  Contact getContactById(int id){
		 ArrayList<Contact> contacts = new ArrayList<Contact>();
		String query = "SELECT * FROM CONTACTS WHERE id=:id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for(Map<String, Object> row: rows) {
			Contact c = new Contact();
			c.setId((Integer)row.get("id"));
			c.setName((String)row.get("name")); 
			c.setPhone((String)row.get("phone"));
			c.setAddress((String)row.get("address"));
			c.setEmail((String)row.get("email"));
			c.setRole((String)row.get("role"));
			contacts.add(c);
		}
		if (contacts.size()>0)
		return contacts.get(0);
		return null;
	}
	
	public void editContact(Contact contact) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "UPDATE CONTACTS SET name=:name, phone=:phone, address=:address, email=:email, role=:role  WHERE id=:id";
		
//		String query = "UPDATE easy_drinks SET drink_name=:name, main=:main, amount1=:a1,"
//				+ " second=:second, amount2=:a2, directions=:dir WHERE id=:id";
		
	    parameters.addValue("id", contact.getId());
		parameters.addValue("name", contact.getName());
		parameters.addValue("phone", contact.getPhone());
		parameters.addValue("address", contact.getAddress());
		parameters.addValue("email", contact.getEmail());
		parameters.addValue("role", contact.getRole());
		jdbc.update(query, parameters);
		//Map<key,Value>
	}
	
	public void deleteContact(int id) {
		//Map<key,Value>
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM CONTACTS WHERE id=:id";
		parameters.addValue("id", id);
		jdbc.update(query, parameters);
	}
	
	public User findUserAccount(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user where userName=:userName";
		parameters.addValue("userName", userName);
		ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size()>0)
			return users.get(0);
		return null;
	}
	
	public List<String> getRolesById(long userId) {
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT user_role.userId, sec_role.roleName "
				+ "FROM user_role, sec_role "
				+ "WHERE user_role.roleId=sec_role.roleId "
				+ "AND userId=:userId";
		parameters.addValue("userId", userId);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			roles.add((String)row.get("roleName"));
		}
		return roles;
		
	}
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	public void createNewUser(String username, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into SEC_User (userName, encryptedPassword, ENABLED) " +
		"values (:name, :pass, 1)";
		parameters.addValue("name", username);
		parameters.addValue("pass", passwordEncoder.encode(password));
		jdbc.update(query, parameters);
				
	}
	
	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into user_role (userId, roleId) values (:userId, :roleId)";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);
	}
	
//	public  ArrayList<Contact> getGuestContact(){
//		ArrayList<Contact> books = new ArrayList<Contact>();
//		String query = "SELECT * FROM CONTACT WHERE role=:role"; 
//		//List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
//		MapSqlParameterSource parameters = new MapSqlParameterSource();
//		parameters.addValue("title", title);
//		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
//		for(Map<String, Object> row: rows) {
//			Book b = new Book();
//			b.setId((Integer)row.get("id"));
//			b.setTitle((String)row.get("title")); 
//			b.setAuthor((String)row.get("author"));
//			b.setPrice(((BigDecimal)(row.get("price"))).doubleValue());
//			b.setInvQuant((Integer)(row.get("invQuant")));
//			b.setCourses((String)row.get("courses"));
//			b.setStore((String)row.get("store"));
//			books.add(b);
//		}
//		return books;
//	}
	public  ArrayList<Contact> getAdminContacts(){
		 ArrayList<Contact> contacts = new ArrayList<Contact>();
		String query = "SELECT * FROM CONTACTS WHERE role='Administrator'";
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		for(Map<String, Object> row: rows) {
			Contact c = new Contact();
			c.setId((Integer)row.get("id"));
			c.setName((String)row.get("name")); 
			c.setPhone((String)row.get("phone"));
			c.setAddress((String)row.get("address"));
			c.setEmail((String)row.get("email"));
			c.setRole((String)row.get("role"));
			contacts.add(c);
		}
		return contacts;
	}

	public  ArrayList<Contact> getGuestContacts(){
		 ArrayList<Contact> contacts = new ArrayList<Contact>();
		String query = "SELECT * FROM CONTACTS WHERE role='Guest'";
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		for(Map<String, Object> row: rows) {
			Contact c = new Contact();
			c.setId((Integer)row.get("id"));
			c.setName((String)row.get("name")); 
			c.setPhone((String)row.get("phone"));
			c.setAddress((String)row.get("address"));
			c.setEmail((String)row.get("email"));
			c.setRole((String)row.get("role"));
			contacts.add(c);
		}
		return contacts;
	}
	
	public  ArrayList<Contact> getMemberContacts(){
		 ArrayList<Contact> contacts = new ArrayList<Contact>();
		String query = "SELECT * FROM CONTACTS WHERE role='Member'";
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		for(Map<String, Object> row: rows) {
			Contact c = new Contact();
			c.setId((Integer)row.get("id"));
			c.setName((String)row.get("name")); 
			c.setPhone((String)row.get("phone"));
			c.setAddress((String)row.get("address"));
			c.setEmail((String)row.get("email"));
			c.setRole((String)row.get("role"));
			contacts.add(c);
		}
		return contacts;
	}
//	public void addContact () {
//		String query = "INSERT INTO CONTACTS VALUES " +
//		"('Yulia', '476 478 6374', '85 Lake Promenade', 'yulia@yahoo.ca')";
//		jdbc.update(query, new HashMap());
//	}
//	
//	public void addContact(Contact contact) {
//		
//		MapSqlParameterSource parameters = new MapSqlParameterSource();
//		String query = "INSERT INTO CONTACTS VALUES (:name, :phone, :address, :email)";
//		parameters.addValue("name", contact.getName());
//		parameters.addValue("phone", contact.getPhone());
//		parameters.addValue("address", contact.getAddress());
//		parameters.addValue("email", contact.getEmail());
//		jdbc.update(query, parameters);
//	}
//	
//	public ArrayList<Contact> getContact(){
//		
//		ArrayList<Contact> contacts = new ArrayList<Contact>();
//		String query = "SELECT * FROM CONTACTS";
//		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
//		for(Map<String, Object> row:rows) {
//			Contact c = new Contact();
//			c.setName((String)row.get("name"));//"name" is the name of the column in sql file
//			c.setPhone((String)row.get("phone"));
//			c.setAddress((String)row.get("address"));
//			c.setEmail((String)row.get("email"));
//			contacts.add(c);
//		}
//		return contacts;
//	}

}
