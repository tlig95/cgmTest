package org.cgmgroup.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.cgmgroup.dto.PatientDto;
import org.cgmgroup.entity.Patient;
import org.cgmgroup.mapper.PatientMapper;
import org.cgmgroup.service.PatientService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@QuarkusTest
class PatientResourceTest {
    @InjectMock
    private PatientService patientService;
    @InjectMock
    private PatientMapper patientMapper;
    @Test
    void count() {
        doReturn(10L).when(this.patientService).count();
        given()
                .when().get("/api/v1/patient/count")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("10"));
    }

    @Test
    void findAll() {
        List<Patient> patients = List.of(new Patient());
        List<PatientDto> patientDto = List.of(new PatientDto());

        doReturn(patients).when(this.patientService).find(1,1);
        doReturn(patientDto).when(this.patientMapper).toDtos(patients);
        given()
          .when().get("/api/v1/patient?page=1&size=1")
          .then()
             .statusCode(200)
                .body(Matchers.equalTo("[{\"id\":null,\"name\":null,\"surname\":null,\"dateOfBirth\":null,\"socialSecurityNumber\":null}]"));
    }
    @Test
    void findById() throws JsonProcessingException {
        Patient patient = new Patient();
        var patientDto = new PatientDto();
        doReturn(Optional.of(patient)).when(this.patientService).findById(1L);
        doReturn(patientDto).when(this.patientMapper).toDto(patient);
        String expected= new ObjectMapper().writeValueAsString(patientDto);
        given()
                .when().get("/api/v1/patient/1")
                .then()
                .statusCode(200)
                .body(Matchers.equalToObject(expected));
    }
    @Test
    void findByIdNotFound() {
        doReturn(Optional.empty()).when(this.patientService).findById(1L);
        given()
                .when().get("/api/v1/patient/1")
                .then()
                .statusCode(404)
                .body(Matchers.equalToObject("Patient not found"));
    }
    @Test
    void save() throws JsonProcessingException {
        Patient patient = new Patient();
        PatientDto patientSavedDto = new PatientDto();
        patientSavedDto.setName("name");
        doReturn(patient).when(this.patientMapper).toModel(any());
        doReturn(patient).when(this.patientService).save(patient);
        doReturn(patientSavedDto).when(this.patientMapper).toDto(patient);
        var body = """
                {
                    "id":1,
                    "name": "name",
                    "surname": "surname",
                    "dateOfBirth": "1995-02-20",
                    "socialSecurityNumber": "9123123123012",
                    "visits": []
                }
                """;
        var response = new ObjectMapper().writeValueAsString(patientSavedDto);
        given().body(body).contentType(MediaType.APPLICATION_JSON)
                .when().post("/api/v1/patient")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo(response));
    }

}