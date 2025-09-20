package br.com.orbitall.hackathon2025.controllers;

import br.com.orbitall.hackathon2025.canonicals.PersonInput;
import br.com.orbitall.hackathon2025.canonicals.PersonOutput;
import br.com.orbitall.hackathon2025.exceptions.ResourceNotFoundException;
import br.com.orbitall.hackathon2025.handlers.GlobalExceptionHandler;
import br.com.orbitall.hackathon2025.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonController.class)
@Import(GlobalExceptionHandler.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PersonService service;

    @Test
    @DisplayName("POST /persons should return 200 with created resource")
    void create_success() throws Exception {
        PersonInput input = new PersonInput("John", 20, "Desc");
        PersonOutput output = sampleOutput(true);
        given(service.create(any(PersonInput.class))).willReturn(output);

        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(output.id().toString())))
                .andExpect(jsonPath("$.fullName", is(output.fullName())))
                .andExpect(jsonPath("$.age", is(output.age())))
                .andExpect(jsonPath("$.description", is(output.description())))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    @DisplayName("POST /persons should validate and return 400 with field errors")
    void create_validationErrors() throws Exception {
        // fullName blank, age 0, description too long
        String tooLong = "x".repeat(256);
        PersonInput input = new PersonInput("", 0, tooLong);

        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fullName", containsString("cannot be null or empty")))
                .andExpect(jsonPath("$.age", containsString("Age must be between 1 and 120")))
                .andExpect(jsonPath("$.description", containsString("between 0 and 255")));
    }

    @Test
    @DisplayName("GET /persons/{id} should return 200 when found")
    void retrieve_success() throws Exception {
        UUID id = UUID.randomUUID();
        PersonOutput output = sampleOutput(true, id);
        given(service.retrieve(id)).willReturn(output);

        mockMvc.perform(get("/persons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())));
    }

    @Test
    @DisplayName("GET /persons/{id} should return 404 when not found")
    void retrieve_notFound() throws Exception {
        UUID id = UUID.randomUUID();
        given(service.retrieve(id)).willThrow(new ResourceNotFoundException("Not found the resource (id: " + id + ")"));

        mockMvc.perform(get("/persons/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString(id.toString())));
    }

    @Test
    @DisplayName("PUT /persons/{id} should delegate to service and return 200")
    void update_success() throws Exception {
        UUID id = UUID.randomUUID();
        PersonInput input = new PersonInput("Jane", 30, "Upd");
        PersonOutput output = sampleOutput(true, id);
        given(service.update(eq(id), any(PersonInput.class))).willReturn(output);

        mockMvc.perform(put("/persons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())));
    }

    @Test
    @DisplayName("DELETE /persons/{id} should return 200 with deactivated resource")
    void delete_success() throws Exception {
        UUID id = UUID.randomUUID();
        PersonOutput output = sampleOutput(false, id);
        given(service.delete(id)).willReturn(output);

        mockMvc.perform(delete("/persons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(false)));
    }

    @Test
    @DisplayName("GET /persons should return list of outputs")
    void findAll_success() throws Exception {
        List<PersonOutput> list = Arrays.asList(sampleOutput(true), sampleOutput(true));
        given(service.findAll()).willReturn(list);

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    private PersonOutput sampleOutput(boolean active) {
        return sampleOutput(active, UUID.randomUUID());
    }

    private PersonOutput sampleOutput(boolean active, UUID id) {
        LocalDateTime now = LocalDateTime.now();
        return new PersonOutput(
                id,
                "John",
                20,
                "Desc",
                now.minusDays(1),
                now,
                active
        );
    }
}
