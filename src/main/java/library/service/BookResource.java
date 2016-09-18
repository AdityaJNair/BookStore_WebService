package library.service;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
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

import library.content.dto.AuthorDTO;
import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.dto.UserDTO;
import library.content.purchase.Author;
import library.content.purchase.Book;
import library.content.purchase.Publisher;
import library.content.purchase.User;

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


	@POST
	@Consumes({ "application/xml", "application/json" })
	public Response addBook(BookDTO bookdto) {
		Book domainBook = DTOMapper.toBookDomain(bookdto);
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		//Need a better query here with select author name is true
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
		Book b = m.find(Book.class, id);
		m.remove(b);
		m.getTransaction().commit();
		m.close();
		return Response.status(204).build();
	}
	
	/**
	 * Get Book from unique isbn
	 * @param isbn
	 * @return
	 */
	@GET
	@Path("/isbn/{isbn}")
	@Produces({ "application/xml", "application/json" })
	public BookDTO getBookByUniqueIdentifier(@PathParam("isbn") String isbn) {
		return null;

	}
	
	/**
	 * Get author object for a book
	 * @param bookid
	 * @param authorId
	 * @return
	 */
	@GET
	@Path("{id}/author/{aid}")
	@Produces({ "application/xml", "application/json" })
	public AuthorDTO getBookAuthor(@PathParam("id") long bookid, @PathParam("aid") long authorId){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Author bookauthor = m.find(Author.class, authorId);
		AuthorDTO authorDTO = DTOMapper.toAuthorDTO(bookauthor);
		m.getTransaction().commit();
		m.close();
		return authorDTO;
	}
	/**
	 * Get all books
	 * @return
	 */
	@GET
	@Produces({ "application/xml", "application/json" })
	public Set<BookDTO> getBooks(){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Set<BookDTO> bookDTOset = new HashSet<BookDTO>();
		TypedQuery<Book> BookQuery = m.createQuery("FROM Book", Book.class);
		List<Book> listBook = BookQuery.getResultList();
		for(Book u: listBook){
			bookDTOset.add(DTOMapper.toBookDTO(u));
		}
		m.getTransaction().commit();
		m.close();
		return bookDTOset;
	}
	
	/**
	 * Get publisher
	 * @param bookid
	 * @return
	 */
	@GET
	@Path("{id}/publisher")
	@Produces({ "application/xml", "application/json" })
	public Publisher getBookPublisher(@PathParam("id") long bookid){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Book b = m.find(Book.class , bookid);
		Publisher publisher = b.get_publisher();
		m.getTransaction().commit();
		m.close();
		return publisher;
	}
		
}
