package org.cgmgroup.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.cgmgroup.entity.Visit;

import java.util.List;

@ApplicationScoped
public class VisitRepository implements PanacheRepository<Visit> {
    public List<Visit> findPage(int page, int size){
        return findAll().page(Page.of(page, size)).list();
    }
}
