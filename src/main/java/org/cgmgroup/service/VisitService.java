package org.cgmgroup.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.cgmgroup.entity.Patient;
import org.cgmgroup.entity.Visit;
import org.cgmgroup.exception.BusinessException;
import org.cgmgroup.repository.VisitRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VisitService {
    @Inject
    private VisitRepository repository;
    @Inject
    private PatientService patientService;

    public Optional<Visit> findById(Long id) {
        return repository.findByIdOptional(id);
    }

    @Transactional
    public Visit save(Visit visit) throws BusinessException {
        visit.setId(null);
        var patient = visit.getPatient();
        if (patient == null || patient.getId() == null)
            throw new BusinessException("patient is missing");
        Optional<Patient> patientFromDb = patientService.findById(patient.getId());
        if (patientFromDb.isEmpty())
            throw new BusinessException("patient is not found");
        visit.setPatient(patientFromDb.get());
        repository.persist(visit);
        return visit;
    }

    public List<Visit> find(int page, int size) {
        return repository.findPage(page, size);
    }

    @Transactional
    public Visit update(Visit visit) {
        return this.findById(visit.getId())
                .map(visitFromDb -> updateDb(visit, visitFromDb))
                .orElseThrow(() -> new BusinessException("visit is not found"));
    }

    @Transactional
    public void delete(Visit visit) {
        repository.delete(visit);
    }

    private Visit updateDb(Visit visit, Visit visitFromDb) {
        visitFromDb.setDateAndTime(visit.getDateAndTime());
        visitFromDb.setVisitReason(visit.getVisitReason());
        visitFromDb.setVisitType(visit.getVisitType());
        visitFromDb.setFamilyHistory(visit.getFamilyHistory());
        this.repository.persist(visitFromDb);
        return visitFromDb;
    }

    public long count() {
        return repository.count();
    }
}
