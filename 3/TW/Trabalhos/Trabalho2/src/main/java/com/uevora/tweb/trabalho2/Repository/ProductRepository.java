package com.uevora.tweb.trabalho2.Repository;

import java.util.List;
import com.uevora.tweb.trabalho2.Entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String keyword);

}
