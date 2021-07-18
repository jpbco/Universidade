package com.uevora.tweb.trabalho2.Service;

import java.util.List;

import com.uevora.tweb.trabalho2.Entity.Product;
import com.uevora.tweb.trabalho2.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public List<Product> findAll() {
		return repository.findAll();
	}

	public Product findById(long id) {
		return repository.findById(id).get();
	}

	public List<Product> listAll(String keyword) {
		if (keyword != null) {
			return repository.findByNameContainingIgnoreCase(keyword);
		}
		return repository.findAll();
	}

	public void deleteById(long id) {
		repository.deleteById(id);
	}

	public void save(Product product) {
		repository.save(product);
	}

}
