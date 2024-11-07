package org.cgmgroup.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.cgmgroup.entity.Patient;

import java.util.List;

@ApplicationScoped
public class PatientRepository implements PanacheRepository<Patient> {
    public List<Patient> findPage(int page, int size){
        return findAll().page(Page.of(page, size)).list();
    }
}
