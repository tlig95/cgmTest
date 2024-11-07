package org.cgmgroup.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.cgmgroup.entity.Patient;
import org.cgmgroup.entity.Visit;
import org.cgmgroup.entity.enums.VisitReason;
import org.cgmgroup.entity.enums.VisitType;
import org.cgmgroup.exception.BusinessException;
import org.cgmgroup.repository.VisitRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class VisitServiceTest {

    @InjectMock
    private VisitRepository repository;
    @InjectMock
    private PatientService patientService;

    @Inject
    private VisitService service;

    @Test
    void findById() {
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setVisitReason(VisitReason.FIRST);
        doReturn(Optional.of(visit)).when(repository).findByIdOptional(1L);
        Optional<Visit> foundPatient = service.findById(1L);
        assertEquals(Optional.of(visit), foundPatient);
        verify(repository).findByIdOptional(1L);
    }
    @Test
    void find(){
        var visits = List.of(new Visit(),new Visit());
        doReturn(visits).when(repository).findPage(1,30);
        List<Visit> actual = service.find(1,30);
        assertEquals(visits, actual);
        verify(repository).findPage(1,30);
    }
    @Test
    void save(){
        Visit visit = new Visit();
        Patient patient = new Patient();
        patient.setId(1L);
        visit.setPatient(patient);
        doNothing().when(repository).persist(visit);
        doReturn(Optional.of(patient)).when(patientService).findById(1L);
        service.save(visit);
        verify(repository).persist(visit);
    }
    @Test
    void saveNullPatient(){
        Visit visit = new Visit();
        visit.setId(1L);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.save(visit);
        });
        assertEquals("patient is missing",exception.getMessage());
        verify(repository,never()).persist(any(Visit.class));
    }
    @Test
    void saveNullPatientId(){
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setPatient(new Patient());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.save(visit);
        });
        assertEquals("patient is missing",exception.getMessage());
        verify(repository,never()).persist(any(Visit.class));
    }
    @Test
    void savePatientNotFound(){
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setPatient(new Patient());
        Patient patient = new Patient();
        patient.setId(1L);
        visit.setPatient(patient);
        doReturn(Optional.empty()).when(patientService).findById(1L);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.save(visit);
        });
        assertEquals("patient is not found",exception.getMessage());
        verify(repository,never()).persist(any(Visit.class));
    }
    @Test
    void count(){
        doReturn(10L).when(repository).count();
        assertEquals(10L,service.count());
    }
    @Test
    void delete(){
        var visit = new Visit();
        doNothing().when(repository).delete(visit);
        service.delete(visit);
        verify(repository).delete(visit);
    }
    @Test
    void update() {
        Visit visitFromDb = new Visit();
        Visit visit = new Visit();
        visit.setId(1L);
        LocalDateTime dateAndTime = LocalDateTime.of(2024, 01, 01, 10, 15);
        visit.setDateAndTime(dateAndTime);
        visit.setVisitType(VisitType.HOME);
        visit.setVisitReason(VisitReason.RECURRING);
        doReturn(Optional.of(visitFromDb)).when(repository).findByIdOptional(1L);
        doNothing().when(repository).persist(visit);
        var actual = service.update(visit);
        assertEquals(dateAndTime,actual.getDateAndTime());
        assertEquals(VisitType.HOME,actual.getVisitType());
        assertEquals(VisitReason.RECURRING,actual.getVisitReason());
    }
    @Test
    void updateNull(){
        Visit visit = new Visit();
        visit.setId(1L);
        doReturn(Optional.empty()).when(repository).findByIdOptional(1L);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.update(visit);
        });
        assertEquals("visit is not found",exception.getMessage());
        verify(repository,never()).persist(any(Visit.class));
    }

}
