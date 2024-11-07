package org.cgmgroup.mapper;

import org.cgmgroup.dto.VisitDto;
import org.cgmgroup.entity.Visit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface VisitMapper {
    VisitDto toDto(Visit visit);
    List<VisitDto> toDtos(List<Visit> visits);
    Visit toModel(VisitDto visitDto);

}
