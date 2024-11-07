package org.cgmgroup.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.cgmgroup.dto.PatientDto;
import org.cgmgroup.entity.Patient;
import org.cgmgroup.mapper.PatientMapper;
import org.cgmgroup.service.PatientService;

@Path("/api/v1/patient")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {
    @Inject
    private PatientService patientService;
    @Inject
    private PatientMapper patientMapper;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return patientService.findById(id)
                .map(patient -> Response.ok(patientMapper.toDto(patient)).build())
                .orElse(Response.ok("Patient not found").status(Response.Status.NOT_FOUND).build());
    }
    @GET
    public Response find(@QueryParam("page") int page,@QueryParam("size") int size) {
        return Response.ok(patientMapper.toDtos(patientService.find(page,size))).build();
    }
    @POST
    public Response save(PatientDto patientDto) {
        Patient saved = patientService.save(patientMapper.toModel(patientDto));
        return Response.ok(patientMapper.toDto(saved)).build();
    }
    @GET
    @Path("/count")
    public Response count() {return Response.ok(patientService.count()).build();}
}
