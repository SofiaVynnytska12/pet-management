package com.mdotm.pets.test.unit;

import com.mdotm.pets.domain.Pet;
import com.mdotm.pets.jpa.repository.JpaPetRepositoryImpl;
import com.mdotm.pets.test.utils.PostgreSQLTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.SqlMergeMode.MergeMode.MERGE;

@SpringBootTest
@ExtendWith(PostgreSQLTestExtension.class)
@ActiveProfiles("test")
@SqlMergeMode(MERGE)
@Sql(scripts = "/clear-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class JpaPetRepositoryTest {

    @Autowired
    private JpaPetRepositoryImpl jpaPetRepositoryImpl;

    @Test
    public void testSavePet() {
        Pet pet = new Pet(null, "Test_Save_dog", "Dog", 5, "John");
        Pet savedPet = jpaPetRepositoryImpl.save(pet);
        assertNotNull(savedPet.getPetId());
        assertEquals("Test_Save_dog", savedPet.getName());
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testFindById() {
        Optional<Pet> retrievedPet = jpaPetRepositoryImpl.findById(2L);
        assertTrue(retrievedPet.isPresent());
        assertEquals("Mastrolindo", retrievedPet.get().getName());
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdatePet() {
        String id = String.valueOf(2L);
        Pet pet = new Pet(id, "Charlie", "Bird", 2, "Emma");
        Pet savedPet = jpaPetRepositoryImpl.update(pet);
        savedPet.setAge(4);
        savedPet.setName("Updated Charlie");
        Pet updatedPet = jpaPetRepositoryImpl.update(savedPet);
        assertEquals(id, updatedPet.getPetId());
        assertEquals(4, updatedPet.getAge());
        assertEquals("Updated Charlie", updatedPet.getName());
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testFindAllPets() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Pet> petPage = jpaPetRepositoryImpl.findAll(null, null, null, null, pageable);
        assertFalse(petPage.getContent().isEmpty());
        assertEquals(5, petPage.getContent().size());
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeletePet() {
        Long id = 5L;
        jpaPetRepositoryImpl.deleteById(id);
        assertFalse(jpaPetRepositoryImpl.findById(id).isPresent());
    }
}
