package library.service;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.purchase.Author;
import library.content.purchase.Book;

@Path("/book")
public class BookResource {
	private static final Logger _logger = LoggerFactory.getLogger(BookResource.class);

	// get book
	/**
	 * 
	 */
	@GET
	@Path("{id}")
	@Produces({ "application/xml", "application/json" })
	public BookDTO getBook(@PathParam("id") long id) {
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Book b = m.find(Book.class, id);
		BookDTO b1 = DTOMapper.toBookDTO(b);
		m.getTransaction().commit();
		m.close();
		if (b == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
		}
		return b1;
	}

/*	//not working
	@GET
	@Path("/isbn/{isbn}")
	@Produces({ "application/xml", "application/json" })
	public BookDTO getBookByUniqueIdentifier(@PathParam("isbn") String isbn) {
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		TypedQuery<Book> bookQuery = m.createQuery("FROM Book WHERE ", Book.class);
		Book bookdomain = (Book) bookQuery.getSingleResult();
		BookDTO b1 = DTOMapper.toBookDTO(bookdomain);
		m.getTransaction().commit();
		m.close();
		if (bookdomain == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
		}
		return b1;

	}*/

	@POST
	@Consumes({ "application/xml", "application/json" })
	public Response addBook(BookDTO bookdto) {
		Book domainBook = DTOMapper.toBookDomain(bookdto);
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
	}
	
	@DELETE
	@Path("{id}")
	@Consumes({ "application/xml", "application/json" })
	public Response deleteBook(@PathParam("id") long id){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		try{
			Book b = m.find(Book.class, id);
			m.remove(b);
		} catch (IllegalArgumentException e){
			m.getTransaction().commit();
			m.close();
			return Response.status(404).build();
		} catch (TransactionRequiredException e2){
			m.getTransaction().commit();
			m.close();
			return Response.status(404).build();
		}
		m.getTransaction().commit();
		m.close();
		return Response.status(204).build();
	}
	
	

}
