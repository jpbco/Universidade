package com.uevora.tweb.trabalho2.Repository;

import java.util.List;

import com.uevora.tweb.trabalho2.Entity.Client;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

	List<Client> findByFirstName(String firstName);

	Client findOneByUsername(String username);

	Client findById(long id);
}
