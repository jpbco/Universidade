package com.uevora.tweb.trabalho2.Controller;

import java.util.List;

import com.uevora.tweb.trabalho2.Entity.Product;
import com.uevora.tweb.trabalho2.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping("/")
	public String viewHomePage(Model model) {
		List<Product> listProducts = service.findAll();
		model.addAttribute("listProducts", listProducts);

		return "index";
	}

	@GetMapping("/advanced")
	public String viewAdvancedPage() {
		return "advanced_search";
	}

	@GetMapping("/new")
	public String showNewProductPage(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);

		return "new_product";
	}

	@PostMapping("/save")
	public String saveProduct(@ModelAttribute("product") Product product) {
		service.save(product);

		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_product");
		Product product = service.findById(id);
		mav.addObject("product", product);

		return mav;
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		service.deleteById(id);
		return "redirect:/";
	}

	@GetMapping("/show/{id}")
	public ModelAndView showProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("product");
		Product product = service.findById(id);
		mav.addObject("product", product);

		return mav;
	}

	@GetMapping("/search")
	public String viewHomePage(Model model, @RequestParam("search_bar") String keyword) {
		List<Product> listProducts = service.listAll(keyword);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("keyword", keyword);

		return "index";
	}

}