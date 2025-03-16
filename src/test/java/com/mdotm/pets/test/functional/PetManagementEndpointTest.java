package com.mdotm.pets.test.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdotm.pets.controller.dto.CreatePetInformationRequestDto;
import com.mdotm.pets.controller.dto.UpdatePetInformationRequestDto;
import com.mdotm.pets.test.utils.PostgreSQLTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.springframework.test.context.jdbc.SqlMergeMode.MergeMode.MERGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(PostgreSQLTestExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SqlMergeMode(MERGE)
@Sql(scripts = "/clear-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PetManagementEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreatePet() throws Exception {
        CreatePetInformationRequestDto createPetDto = new CreatePetInformationRequestDto("Buddy", "Dog", 3, "John Doe");

        mockMvc.perform(post("/pets")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(createPetDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.species").value("Dog"));
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetPetById() throws Exception {
        mockMvc.perform(get("/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Clara"))
                .andExpect(jsonPath("$.species").value("Dog"));
    }

    @Test
    void testGetPetByIdNotFound() throws Exception {
        mockMvc.perform(get("/pets/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Pet information with such pet ID is not found"));
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetAllPetsSortingByName() throws Exception {
        mockMvc.perform(get("/pets")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ChanteClair"))
                .andExpect(jsonPath("$[1].name").value("Clara"));
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetAllPetsSortingByAgeDesc() throws Exception {
        mockMvc.perform(get("/pets")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "-age"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Clara"))
                .andExpect(jsonPath("$[1].name").value("ChanteClair"));
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetAllPetsFilterByName() throws Exception {
        mockMvc.perform(get("/pets")
                        .param("page", "0")
                        .param("size", "10")
                        .param("name", "Clara"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Clara"));
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetAllPetsFilterBySpecies() throws Exception {
        mockMvc.perform(get("/pets")
                        .param("page", "0")
                        .param("size", "10")
                        .param("species", "DoG"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].species").value("Dog"));
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetAllPetsFilterByOwnerName() throws Exception {
        mockMvc.perform(get("/pets")
                        .param("page", "0")
                        .param("size", "10")
                        .param("ownerName", "Sofi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ownerName").value("Sofiia"));
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testUpdatePet() throws Exception {
        UpdatePetInformationRequestDto updatePetDto = new UpdatePetInformationRequestDto("Buddy", 4, "Jane Doe");

        mockMvc.perform(put("/pets/1")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updatePetDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(4))
                .andExpect(jsonPath("$.ownerName").value("Jane Doe"));
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testDeletePet() throws Exception {
        mockMvc.perform(delete("/pets/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testPatchPet() throws Exception {
        Map<String, Object> updatedProperties = Map.of("age", 5);

        mockMvc.perform(patch("/pets/1")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updatedProperties)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(5));
    }

    @Test
    @Sql(scripts = "/pets-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetAllPets() throws Exception {
        mockMvc.perform(get("/pets")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(
                        "Clara", "Mastrolindo", "Coccolino", "ChanteClair", "Kimura")));
    }
}
