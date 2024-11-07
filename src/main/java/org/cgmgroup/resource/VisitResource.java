package org.cgmgroup.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.cgmgroup.dto.VisitDto;
import org.cgmgroup.entity.Visit;
import org.cgmgroup.exception.BusinessException;
import org.cgmgroup.mapper.VisitMapper;
import org.cgmgroup.service.VisitService;

@Path("/api/v1/visit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VisitResource {
    @Inject
    private VisitService visitService;
    @Inject
    private VisitMapper visitMapper;

    @POST
    public Response save(VisitDto visitDto) {
        try {
            Visit saved = visitService.save(visitMapper.toModel(visitDto));
            return Response.ok(visitMapper.toDto(saved)).build();
        } catch (BusinessException e) {
            return Response.ok(e.getMessage()).status(Response.Status.BAD_REQUEST).build();
        }

    }

    @PUT
    public Response update(VisitDto visitDto) {
        try {
            Visit saved = visitService.update(visitMapper.toModel(visitDto));
            return Response.ok(visitMapper.toDto(saved)).build();

        } catch (BusinessException e) {
            return Response.ok(e.getMessage()).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/count")
    public Response count() {
        return Response.ok(visitService.count()).build();
    }

    @GET
    public Response find(@QueryParam("page") int page, @QueryParam("size") int size) {
        return Response.ok(visitMapper.toDtos(visitService.find(page, size))).build();
    }
}
