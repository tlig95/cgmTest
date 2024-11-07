package org.cgmgroup.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.cgmgroup.entity.Patient;
import org.cgmgroup.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PatientService {
    @Inject
    private PatientRepository repository;
    public Optional<Patient> findById(Long id){
        return repository.findByIdOptional(id);
    }
    public List<Patient> find(int page, int size){
        return repository.findPage(page,size);
    }
    @Transactional
    public Patient save(Patient patient){
        patient.setId(null);
        repository.persist(patient);
        return patient;
    }
    public long count(){return repository.count();}

}
