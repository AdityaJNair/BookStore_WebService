/**
 * 
 */
package library.service;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;

import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.dto.UserDTO;
import library.content.purchase.Author;
import library.content.purchase.Book;
import library.content.purchase.User;

/**
 * @author adijn
 *
 */
@Path("/user")
public class UserResource {

	
	@GET
	@Path("{id}")
	@Produces({"application/xml","application/json"})
	public UserDTO getUser(@PathParam("id") long id){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User b = m.find(User.class, id);
		UserDTO b1 = DTOMapper.toUserDTO(b);
		m.getTransaction().commit();
		m.close();
		if(b==null){
		    throw new WebApplicationException(
		    	      Response.status(Status.NOT_FOUND)
		    	        .build());
		}
		return b1;
	}
	
/*	@GET
	@Path("{isbn}")
	@Produces({"application/xml","application/json"})
	public BookDTO getBookByUniqueIdentifier(@PathParam("isbn") String isbn){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Query bookQuery = m.createQuery("SELECT * FROM FROM BOOK b WHERE b.ISBN=:isbn").setParameter("isbn", isbn);
		Book bookdomain = (Book)bookQuery.getSingleResult();
		BookDTO b1 = DTOMapper.toBookDTO(bookdomain);
		m.getTransaction().commit();
		m.close();
		if(bookdomain==null){
		    throw new WebApplicationException(
		    	      Response.status(Status.NOT_FOUND)
		    	        .build());
		}
		return b1;
		
	}*/
	
/*	@POST
	@Consumes({ "application/xml", "application/json" })
	public Response addUser(UserDTO userdto) {
		User domainBook = DTOMapper.toUserDomain(userdto);
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		TypedQuery<Author> authorQuery = m.createQuery("FROM Author", Author.class);
		List<Author> listAuthors = authorQuery.getResultList();
		for (Author a : listAuthors) {
			if (domainBook.get_author().equals(a)) {
				domainBook.set_author(a);
				m.persist(domainBook);
				m.getTransaction().commit();
				m.close();
				return Response.created(URI.create("/book/" + domainBook.get_bookId())).status(201).build();
			}
		}
		m.persist(domainBook);
		m.getTransaction().commit();
		m.close();
		return Response.created(URI.create("/book/" + domainBook.get_bookId())).build();
	}*/
}
