package br.com.orbitall.hackathon2025.repositories;

import org.springframework.data.repository.CrudRepository;
import br.com.orbitall.hackathon2025.models.Person;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID> {

}
