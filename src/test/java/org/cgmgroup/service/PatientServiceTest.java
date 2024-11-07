package org.cgmgroup.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.cgmgroup.entity.Patient;
import org.cgmgroup.repository.PatientRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@QuarkusTest
class PatientServiceTest {

    @InjectMock
    private PatientRepository repository;

    @Inject
    private PatientService service;

    @Test
    void findById() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");
        doReturn(Optional.of(patient)).when(repository).findByIdOptional(1L);
        Optional<Patient> foundPatient = service.findById(1L);
        assertEquals(Optional.of(patient), foundPatient);
        verify(repository).findByIdOptional(1L);
    }
    @Test
    void find(){
        var patients = List.of(new Patient(),new Patient());
        doReturn(patients).when(repository).findPage(1,30);
        List<Patient> actual = service.find(1,30);
        assertEquals(patients, actual);
        verify(repository).findPage(1,30);
    }
    @Test
    void count(){
        doReturn(10L).when(repository).count();
        assertEquals(10L,service.count());
    }
    @Test
    void save(){
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");
        doNothing().when(repository).persist(patient);
        service.save(patient);
        verify(repository).persist(patient);
    }
}
