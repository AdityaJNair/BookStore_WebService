package library.service;

import java.net.URI;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.purchase.Book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/book")
public class BookResource {
	private static final Logger _logger = LoggerFactory.getLogger(BookResource.class);
	
	//get book
	/**
	 * 
	 */
	@GET
	@Path("{id}")
	@Produces({"application/xml","application/json"})
	public BookDTO getBook(@PathParam("id") long id){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Book b = m.find(Book.class, id);
		BookDTO b1 = DTOMapper.toBookDTO(b);
		m.getTransaction().commit();
		m.close();
		if(b==null){
		    throw new WebApplicationException(
		    	      Response.status(Status.BAD_GATEWAY)
		    	        .build());
		}
		return b1;
	}
	
	@POST
	@Consumes({"application/xml","application/json"})
	public Response addBook(BookDTO bookdto){
		Book domainBook = DTOMapper.toBookDomain(bookdto);
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
	   try{
			m.persist(domainBook); 
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
		m.getTransaction().commit();
		m.close();
		return Response.created(URI.create("/book/" + domainBook.get_bookId())).build();
	}
		
}
