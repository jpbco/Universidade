package com.uevora.tweb.trabalho2.Controller;

import java.util.List;

import com.uevora.tweb.trabalho2.Entity.Client;
import com.uevora.tweb.trabalho2.Entity.Orders;
import com.uevora.tweb.trabalho2.Entity.Product;
import com.uevora.tweb.trabalho2.Repository.ClientRepository;
import com.uevora.tweb.trabalho2.Repository.OrderRepository;
import com.uevora.tweb.trabalho2.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private OrderRepository ordersRepository;

	@Autowired
	private ProductService service;

	@PostMapping("/new-order")
	public String newOrder(@RequestParam(name = "clientID", required = false) long clientID,
			@RequestParam(name = "productID", required = false, defaultValue = "") long productID, Model model) {

		Client client = clientRepository.findById(clientID);
		Product product = service.findById(productID);
		ordersRepository.save(new Orders(client, product));

		return "redirect:/";
	}

	@GetMapping("/new-order/{id}")
	public String newOrderPLS(@PathVariable(name = "id") int id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Client client = clientRepository.findOneByUsername(username);
		Product product = service.findById(id);
		ordersRepository.save(new Orders(client, product));

		return "redirect:/";
	}

	@GetMapping("/list-orders")
	public String listOrders(Model model) {
		List<Orders> ordersList = (List<Orders>) ordersRepository.findAll();
		model.addAttribute("ordersList", ordersList);
		System.out.print(ordersList);
		return "list-orders-view";
	}

	@GetMapping("/list-orders/user")
	public String listOrdersUser(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Client client = clientRepository.findOneByUsername(username);
		Long id = client.getId();
		List<Orders> ordersList = (List<Orders>) ordersRepository.findAllByUserName(id);

		model.addAttribute("ordersList", ordersList);
		System.out.print(ordersList);
		return "list-orders-view";
	}
}
