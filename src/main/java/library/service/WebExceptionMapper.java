package library.service;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
/**
 * Mapper class that maps entity not found exceptions to a response of 404
 * @author Aditya
 *
 */
public class WebExceptionMapper implements ExceptionMapper<EntityNotFoundException>{

	@Override
	public Response toResponse(EntityNotFoundException exception) {
		return Response.status(404).build();
	}

}
