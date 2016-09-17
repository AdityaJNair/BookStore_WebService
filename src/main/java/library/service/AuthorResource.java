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
import library.content.purchase.Book;

/**
 * @author adijn
 *
 */
@Path("/author")
public class AuthorResource {
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
	
	
	@GET
	@Produces({"application/xml","application/json"})
	public Set<BookDTO> getBooks(){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		TypedQuery<Book> bookdto = m.createQuery("SELECT * FROM BOOK ", Book.class);
		List<Book> listBooks = bookdto.getResultList();
		Set<BookDTO> listBooksDTO = new HashSet<BookDTO>();
		for(Book b: listBooks){
			listBooksDTO.add(DTOMapper.toBookDTO(b));
		}
		m.getTransaction().commit();
		m.close();
		if(listBooks==null || listBooks.isEmpty()){
		    throw new WebApplicationException(
		    	      Response.status(Status.BAD_GATEWAY)
		    	        .build());
		}
		return listBooksDTO;
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
	    catch(ConstraintViolationException e){
	    	
			m.getTransaction().commit();
			m.close();
	    }
		m.getTransaction().commit();
		m.close();
		return Response.created(URI.create("/book/" + domainBook.get_bookId())).build();
	}
		

}
