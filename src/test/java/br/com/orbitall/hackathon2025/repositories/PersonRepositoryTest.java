package br.com.orbitall.hackathon2025.repositories;

import br.com.orbitall.hackathon2025.models.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    @DisplayName("should persist and retrieve Person entities with UUID id")
    void persistAndRetrieve() {
        Person p1 = buildPerson(true);
        Person p2 = buildPerson(false);

        repository.save(p1);
        repository.save(p2);

        Person found1 = repository.findById(p1.getId()).orElseThrow();
        assertEquals(p1.getFullName(), found1.getFullName());
        assertEquals(p1.getAge(), found1.getAge());
        assertEquals(p1.getDescription(), found1.getDescription());
        assertEquals(p1.isActive(), found1.isActive());

        List<Person> all = (List<Person>) repository.findAll();
        assertEquals(2, all.size());
    }

    private Person buildPerson(boolean active) {
        Person p = new Person();
        p.setId(UUID.randomUUID());
        p.setFullName("John");
        p.setAge(20);
        p.setDescription("Desc");
        LocalDateTime now = LocalDateTime.now();
        p.setCreatedAt(now.minusDays(1));
        p.setUpdatedAt(now);
        p.setActive(active);
        return p;
    }
}
