package com.uevora.tweb.trabalho2.Controller;

import com.uevora.tweb.trabalho2.Entity.Client;
import com.uevora.tweb.trabalho2.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

	@Autowired
	private ClientRepository repository;

	@PostMapping("/new-client")
	public String newClient(
			@RequestParam(name = "firstName", required = false, defaultValue = "World") String firstName,
			@RequestParam(name = "lastName", required = false, defaultValue = "") String lastName,
			@RequestParam(name = "mail", required = false, defaultValue = "") String mail,
			@RequestParam(name = "username", required = false, defaultValue = "") String username,
			@RequestParam(name = "password", required = false, defaultValue = "") String password, Model model) {

		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		if (repository.findOneByUsername(username) == null)
			repository.save(new Client(firstName, lastName, mail, username, encodedPassword));

		return "redirect:/";

	}
}
