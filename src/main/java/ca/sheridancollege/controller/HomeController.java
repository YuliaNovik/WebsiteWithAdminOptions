package ca.sheridancollege.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Contact;
import ca.sheridancollege.database.DatabaseAccess;

@Controller
public class HomeController {
	
	@Autowired 
	@Lazy
	private DatabaseAccess da;
	
//	@GetMapping("/")
//	public String goAddContact(Model model) {
//		model.addAttribute("contact", new Contact());
//	return "newContact.html";
//	}
	
	@GetMapping("/")
	public String goHome() {
	return "index.html";		
	}
	@GetMapping("/admin")
	public String goAdminHome() {
		return "registration.html";
	//return "/admin/index.html";		
	}
	@GetMapping("/login")
	public String goLoginPage() {
	return "login.html";		
	}
	@GetMapping("/guest")
	public String goManagerHome() {
	return "/guest/index.html";		
	}
	@GetMapping("/member")
	public String goEmployeeHome() {
	return "/member/index.html";		
	}
	@GetMapping("/access-denied")
	public String accessDenied() {
	return "/error/access-denied.html";		
	}
	
	@GetMapping("/register")
	public String goRegistration() {
	return "registration.html";		
	}
	
	@PostMapping("/register")
	public String processRegistration(@RequestParam String name, 	@RequestParam String password, 
			@RequestParam(required=false, defaultValue = "false") boolean admin, 
			@RequestParam(required=false, defaultValue = "false") boolean guest,
			@RequestParam(required=false, defaultValue = "false") boolean member) {
		
		da.createNewUser(name, password);
		long userId = da.findUserAccount(name).getUserId();
		
		if(admin==true) {
			da.addRole(userId, 1);//admin
		}
		
		if(guest==true) {
			da.addRole(userId, 2);//guest
		}
		
		if(member==true) {
			da.addRole(userId, 3);//member
		}
		System.out.println(admin);
		System.out.println(guest);
		System.out.println(member);
//		da.addRole(userId, admin);
//		da.addRole(userId, member);
//		da.addRole(userId, guest);
	return "redirect:/";		
	}
	
	@GetMapping("/newContact")
	public String doAddContact(Model model, @ModelAttribute Contact contact) {		
		da.addContact(contact);
		model.addAttribute("contacts", da.getContacts());
		return "newContact.html";
	}
	@GetMapping("/view")
	public String goViewContacts(Model model) {
		model.addAttribute("contacts", da.getContacts());
		//System.out.println(da.toString());
		
		return "view.html";
	}
	
	@GetMapping("/edit/{id}")
	public String editContact(@PathVariable int id, Model model) {
		Contact contact = da.getContactById(id);
		model.addAttribute("contact", contact);
		return "modify.html";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteContact(@PathVariable int id, Model model) {
		da.deleteContact(id);
		return "redirect:/view";
	}
	
	@GetMapping("/modify")
	public String modifyContact(Model model, @ModelAttribute Contact contact) {
		da.editContact(contact);
		return "redirect:/view";
	}
	
	@GetMapping("/viewRoles")
	public String goViewContactsRoles(Model model,  Authentication authentication) {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		ArrayList<String> roles = new ArrayList<String>();
        for (GrantedAuthority ga: authentication.getAuthorities()){
            roles.add(ga.getAuthority());
            if (roles.contains("ROLE_ADMIN")) {
            	contacts.addAll(da.getAdminContacts());
            	model.addAttribute("contacts", contacts);
    		}
           // if (roles.get(0).equalsIgnoreCase("ROLE_GUEST")) {
            	 if (roles.contains("ROLE_GUEST")) {
            	contacts.addAll(da.getGuestContacts());
            	model.addAttribute("contacts", contacts);
    		}
            if (roles.contains("ROLE_MEMBER")) {
            	contacts.addAll(da.getMemberContacts());
            //	model.addAttribute("contacts", da.getMemberContacts());
            	model.addAttribute("contacts", contacts);
    		}
        }
		
		return "viewRoles.html";
	}
	
	
	
//	@GetMapping("/sQ")
//	public String searchBookByQ(@RequestParam double invQuant, Model model) {
//		ArrayList<Book> book = da.searchBookByQ(invQuant);
//		model.addAttribute("book", book);
//		return "search.html";
//	}

//	@GetMapping("/")
//	public String goHome() {
//		Contact c = new Contact("Galina", "904 489 9483", "77 Yar", "galina@yahoo.ca");
//		da.addContact(c);
//		return "newContact.html";
//	}
//	
//
//	@GetMapping("/newContact")
//	public String newContact() {
//		for(Contact c: da.getContact()) {
//		System.out.println(c.toString());
//		}
//		return "newContact.html";
//	}
	
	
//	public static ArrayList<Contact> _contacts = new ArrayList<Contact>();
//
//	@GetMapping("/")  //localhost:8080
//	public String goNewContact(Model model) { 
//		model.addAttribute("contact", new Contact());
//		return "newContact.html";
//	}
//
//	@GetMapping("/newContact")
//	//	public String addContact(@RequestParam String name, @RequestParam String phone, 
//	//			@RequestParam String address, @RequestParam String email) { 
//	public String addContact(Model model, @ModelAttribute Contact contact) {
//		ContactList.contactList.add(contact);
//
//		//		Contact cont = new Contact(name, phone, address, email); 
//		//
//		//				cont.setName(name);
//		//				cont.setPhone(phone);
//		//				cont.setAddress(address);
//		//				cont.setEmail(email);
//		//			    _contacts.add(cont);
//		//_contacts.add(new Contact(name, phone, address, email));
//
//		//System.out.println(name + " " + " " + phone + " " + address+ " " + email);
//
//		//						 for(Contact i : _contacts) {
//		//								System.out.print(i.toString());
//		//							}
//
//		System.out.println(contact.toString());
//		model.addAttribute("contact", new Contact());
//		return "newContact.html";
//	}
//
//	@GetMapping("/view")	
//	public String viewContact(Model model) {
//		model.addAttribute("list", ContactList.contactList);
//
//		//		for(Contact i : _contacts) {
//		//			System.out.print(i.toString());
//		//		}
//
//		return "view.html";
//	}
}
