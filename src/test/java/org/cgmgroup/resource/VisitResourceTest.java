package org.cgmgroup.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.cgmgroup.dto.VisitDto;
import org.cgmgroup.entity.Visit;
import org.cgmgroup.entity.enums.VisitReason;
import org.cgmgroup.entity.enums.VisitType;
import org.cgmgroup.mapper.VisitMapper;
import org.cgmgroup.service.VisitService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@QuarkusTest
class VisitResourceTest {
    @InjectMock
    private VisitService visitService;
    @InjectMock
    private VisitMapper visitMapper;

    @Test
    void findAll()  {
        List<Visit> visits = List.of(new Visit());
        VisitDto visitDto = new VisitDto();
        visitDto.setId(1L);
        visitDto.setVisitType(VisitType.HOME);
        visitDto.setVisitReason(VisitReason.FIRST);
        visitDto.setDateAndTime(LocalDateTime.of(2024,11,11,12,0));
        List<VisitDto> visitDtos = List.of(visitDto);
        doReturn(visits).when(this.visitService).find(1, 1);
        doReturn(visitDtos).when(this.visitMapper).toDtos(visits);
        given()
                .when().get("/api/v1/visit?page=1&size=1")
                .then()
                .statusCode(200)
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].dateAndTime", Matchers.equalTo("2024-11-11T12:00:00"))
                .body("[0].visitType", Matchers.equalTo(VisitType.HOME.toString()))
                .body("[0].visitReason", Matchers.equalTo(VisitReason.FIRST.toString()))
                .body("[0].familyHistory", Matchers.nullValue());
    }
    @Test
    void count() {
        doReturn(10L).when(this.visitService).count();
        given()
                .when().get("/api/v1/visit/count")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("10"));
    }

    @Test
    void save() throws JsonProcessingException {
        Visit visit = new Visit();
        VisitDto visitSavedDto = new VisitDto();
        visitSavedDto.setVisitReason(VisitReason.FIRST);
        doReturn(visit).when(this.visitMapper).toModel(any());
        doReturn(visit).when(this.visitService).save(visit);
        doReturn(visitSavedDto).when(this.visitMapper).toDto(visit);
        var response = new ObjectMapper().writeValueAsString(visitSavedDto);
        given().contentType(MediaType.APPLICATION_JSON)
                .when().post("/api/v1/visit")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo(response));
    }
    @Test
    void update() throws JsonProcessingException {
        Visit visit = new Visit();
        VisitDto visitSavedDto = new VisitDto();
        visitSavedDto.setVisitReason(VisitReason.FIRST);
        doReturn(visit).when(this.visitMapper).toModel(any());
        doReturn(visit).when(this.visitService).update(visit);
        doReturn(visitSavedDto).when(this.visitMapper).toDto(visit);
        var response = new ObjectMapper().writeValueAsString(visitSavedDto);
        given().contentType(MediaType.APPLICATION_JSON)
                .when().put("/api/v1/visit")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo(response));
    }

}