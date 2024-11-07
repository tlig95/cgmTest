package org.cgmgroup.mapper;

import org.cgmgroup.dto.PatientDto;
import org.cgmgroup.entity.Patient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface PatientMapper {
    PatientDto toDto(Patient patient);
    List<PatientDto> toDtos(List<Patient> visits);
    Patient toModel(PatientDto patientDto);

}
