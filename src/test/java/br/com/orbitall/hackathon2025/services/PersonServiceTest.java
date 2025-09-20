package br.com.orbitall.hackathon2025.services;

import br.com.orbitall.hackathon2025.canonicals.PersonInput;
import br.com.orbitall.hackathon2025.canonicals.PersonOutput;
import br.com.orbitall.hackathon2025.exceptions.ResourceNotFoundException;
import br.com.orbitall.hackathon2025.models.Person;
import br.com.orbitall.hackathon2025.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    private PersonInput input;

    @BeforeEach
    void setUp() {
        input = new PersonInput("John Doe", 30, "Test person");
    }

    @Test
    @DisplayName("create() should map input, generate id, set timestamps and active=true, and save")
    void testCreate() {
        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);

        PersonOutput out = service.create(input);

        verify(repository, times(1)).save(personCaptor.capture());
        Person saved = personCaptor.getValue();

        assertNotNull(saved.getId(), "ID should be generated");
        assertEquals(input.fullName(), saved.getFullName());
        assertEquals(input.age(), saved.getAge());
        assertEquals(input.description(), saved.getDescription());
        assertTrue(saved.isActive(), "Person should be active on create");
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
        assertEquals(saved.getCreatedAt(), saved.getUpdatedAt(), "createdAt must equal updatedAt on create");

        // Also validate output mirrors saved state
        assertEquals(saved.getId(), out.id());
        assertEquals(saved.getFullName(), out.fullName());
        assertEquals(saved.getAge(), out.age());
        assertEquals(saved.getDescription(), out.description());
        assertEquals(saved.getCreatedAt(), out.createdAt());
        assertEquals(saved.getUpdatedAt(), out.updatedAt());
        assertEquals(saved.isActive(), out.active());
    }

    @Test
    @DisplayName("retrieve() should return mapped output when active record exists")
    void testRetrieve_success() {
        UUID id = UUID.randomUUID();
        Person p = buildPerson(id, true);
        when(repository.findById(id)).thenReturn(Optional.of(p));

        PersonOutput out = service.retrieve(id);

        assertEquals(id, out.id());
        assertEquals(p.getFullName(), out.fullName());
        assertEquals(p.getAge(), out.age());
        assertEquals(p.getDescription(), out.description());
        assertEquals(p.getCreatedAt(), out.createdAt());
        assertEquals(p.getUpdatedAt(), out.updatedAt());
        assertTrue(out.active());
    }

    @Test
    @DisplayName("retrieve() should throw when not found or inactive")
    void testRetrieve_notFoundOrInactive() {
        UUID id1 = UUID.randomUUID();
        when(repository.findById(id1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.retrieve(id1));

        UUID id2 = UUID.randomUUID();
        Person inactive = buildPerson(id2, false);
        when(repository.findById(id2)).thenReturn(Optional.of(inactive));
        assertThrows(ResourceNotFoundException.class, () -> service.retrieve(id2));
    }

    @Test
    @DisplayName("update() should modify fields and updatedAt and save")
    void testUpdate_success() {
        UUID id = UUID.randomUUID();
        Person existing = buildPerson(id, true);
        LocalDateTime oldUpdatedAt = existing.getUpdatedAt();
        when(repository.findById(id)).thenReturn(Optional.of(existing));

        PersonInput newInput = new PersonInput("Jane Doe", 25, "Updated");
        PersonOutput out = service.update(id, newInput);

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        verify(repository).save(personCaptor.capture());
        Person saved = personCaptor.getValue();

        assertEquals("Jane Doe", saved.getFullName());
        assertEquals(25, saved.getAge());
        assertEquals("Updated", saved.getDescription());
        assertTrue(saved.getUpdatedAt().isAfter(oldUpdatedAt) || saved.getUpdatedAt().isEqual(oldUpdatedAt));
        assertEquals(existing.getCreatedAt(), saved.getCreatedAt(), "createdAt must stay unchanged");
        assertTrue(saved.isActive());

        // Output mirrors entity
        assertEquals(saved.getFullName(), out.fullName());
        assertEquals(saved.getAge(), out.age());
        assertEquals(saved.getDescription(), out.description());
        assertEquals(saved.getUpdatedAt(), out.updatedAt());
        assertEquals(saved.getCreatedAt(), out.createdAt());
        assertEquals(saved.isActive(), out.active());
    }

    @Test
    @DisplayName("update() should throw when not found or inactive")
    void testUpdate_notFoundOrInactive() {
        UUID id1 = UUID.randomUUID();
        when(repository.findById(id1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(id1, input));

        UUID id2 = UUID.randomUUID();
        Person inactive = buildPerson(id2, false);
        when(repository.findById(id2)).thenReturn(Optional.of(inactive));
        assertThrows(ResourceNotFoundException.class, () -> service.update(id2, input));
    }

    @Test
    @DisplayName("delete() should set active=false, update timestamp and save")
    void testDelete_success() {
        UUID id = UUID.randomUUID();
        Person existing = buildPerson(id, true);
        LocalDateTime oldUpdatedAt = existing.getUpdatedAt();
        when(repository.findById(id)).thenReturn(Optional.of(existing));

        PersonOutput out = service.delete(id);

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        verify(repository).save(personCaptor.capture());
        Person saved = personCaptor.getValue();

        assertFalse(saved.isActive());
        assertTrue(!saved.getUpdatedAt().isBefore(oldUpdatedAt));
        assertEquals(saved.isActive(), out.active());
        assertEquals(saved.getUpdatedAt(), out.updatedAt());
    }

    @Test
    @DisplayName("delete() should throw when not found or inactive")
    void testDelete_notFoundOrInactive() {
        UUID id1 = UUID.randomUUID();
        when(repository.findById(id1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.delete(id1));

        UUID id2 = UUID.randomUUID();
        Person inactive = buildPerson(id2, false);
        when(repository.findById(id2)).thenReturn(Optional.of(inactive));
        assertThrows(ResourceNotFoundException.class, () -> service.delete(id2));
    }

    @Test
    @DisplayName("findAll() should return only active records, mapped to outputs")
    void testFindAll_filtersInactive() {
        Person active1 = buildPerson(UUID.randomUUID(), true);
        Person inactive = buildPerson(UUID.randomUUID(), false);
        Person active2 = buildPerson(UUID.randomUUID(), true);
        when(repository.findAll()).thenReturn(Arrays.asList(active1, inactive, active2));

        List<PersonOutput> list = service.findAll();
        assertEquals(2, list.size());
        assertTrue(list.stream().allMatch(PersonOutput::active));
        assertTrue(list.stream().map(PersonOutput::id).toList().containsAll(Arrays.asList(active1.getId(), active2.getId())));
    }

    private Person buildPerson(UUID id, boolean active) {
        Person p = new Person();
        p.setId(id);
        p.setFullName("John Doe");
        p.setAge(30);
        p.setDescription("Test");
        LocalDateTime now = LocalDateTime.now();
        p.setCreatedAt(now.minusDays(1));
        p.setUpdatedAt(now.minusHours(1));
        p.setActive(active);
        return p;
    }
}
