package com.uevora.tweb.trabalho2.Repository;

import java.util.List;

import com.uevora.tweb.trabalho2.Entity.Orders;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders, Long> {
	Orders findById(long id);

	List<Orders> findAllById(Long id);

	@Query(value = "SELECT o FROM Orders o WHERE clientID = ?1")
	List<Orders> findAllByUserName(Long id);

}
