package library.service;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import library.content.domain.Author;
import library.content.domain.Book;
import library.content.domain.Publisher;
import library.content.domain.Review;
import library.content.domain.User;
import library.content.dto.AuthorDTO;
import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.dto.ReviewDTO;

@Path("/book")
/**
 * Resource class for Books.
 * Only able to GET books, DELETE books and POST books.
 * No Put methods as once a book is added, nothing changes (own constraint)
 * @author Aditya
 *
 */
public class BookResource {
	private static final Logger _logger = LoggerFactory.getLogger(BookResource.class);
	protected List<AsyncResponse> asyncResponses = new ArrayList<AsyncResponse>();

	/**
	 * Get a book based on id
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({ "application/xml", "application/json" })
	public BookDTO getBook(@PathParam("id") long id) {
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("Check if user exists");
		Book b = m.find(Book.class, id);
		if (b == null) {
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		BookDTO b1 = DTOMapper.toBookDTO(b);
		m.getTransaction().commit();
		m.close();
		return b1;
	}
	
	/**
	 * Get a book based on the title and the language
	 * @param title
	 * @param language
	 * @return
	 */
	@GET
	@Path("name")
	@Produces({ "application/xml", "application/json" })
	public BookDTO getBookBasedOnAdditionalInfo(@DefaultValue("") @QueryParam("title") String title, @DefaultValue("") @QueryParam("language") String language){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("Query where looking for title and language is same");
		Book b = m.createQuery("SELECT b FROM Book b WHERE b.title=:title AND b.language=:language", Book.class).setParameter("title", title).setParameter("language",language).getSingleResult();
		if (b == null) {
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		BookDTO b1 = DTOMapper.toBookDTO(b);
		m.getTransaction().commit();
		m.close();

		return b1;
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
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Book b = m.createQuery("SELECT b FROM Book b WHERE b.isbn=:isbn", Book.class).setParameter("isbn", isbn).getSingleResult();
		if (b == null) {
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		BookDTO b1 = DTOMapper.toBookDTO(b);
		m.getTransaction().commit();
		m.close();

		return b1;
	}
	
	/**
	 * post a book to the database
	 * @param bookdto
	 * @return
	 */
	@POST
	@Consumes({ "application/xml", "application/json" })
	public Response addBook(BookDTO bookdto) {
		Book domainBook = DTOMapper.toBookDomain(bookdto);
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("Get all authors");
		TypedQuery<Author> authorQuery = m.createQuery("FROM Author", Author.class);
		List<Author> listAuthors = authorQuery.getResultList();
		_logger.info("Check if the book author given is existing in database");
		for (Author a : listAuthors) {
			if (domainBook.get_author().equals(a)) {
				domainBook.set_author(a);
			}
		}
		try{
			_logger.info("persist book with reference to author");
			m.persist(domainBook);
			m.getTransaction().commit();
			// Notifying subscribers that a new book has been added
			_logger.info("async test");
			for (AsyncResponse response : asyncResponses) {
				response.resume(bookdto);
			}
			asyncResponses.clear();
		} catch (PersistenceException e){
			_logger.error("Conflict found");
			return Response.status(409).build();
		}finally{
			m.close();
		}
		return Response.created(URI.create("/book/" + domainBook.get_bookId())).build();
	}
		
	/**
	 * Delete book from database based on id value
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Consumes({ "application/xml", "application/json" })
	public Response deleteBook(@PathParam("id") long id){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("Find the book based on id");
		Book b = m.find(Book.class, id);
		if (b == null) {
			m.close();
			_logger.error("Entity not found");
			return Response.status(404).build();
		}
		_logger.info("remove the book, not author");
		m.remove(b);
		m.getTransaction().commit();
		m.close();
		return Response.ok().build();
	}
	

	
	/**
	 * Get author object for a book
	 * @param bookid
	 * @param authorId
	 * @return
	 */
	@GET
	@Path("{id}/author")
	@Produces({ "application/xml", "application/json" })
	public AuthorDTO getBookAuthor(@PathParam("id") long bookid){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("Get the book specified by id");
		Book bookauthor = m.find(Book.class, bookid);
		if (bookauthor == null) {
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		AuthorDTO authorDTO = DTOMapper.toAuthorDTO(bookauthor.get_author());
		m.getTransaction().commit();
		m.close();
		return authorDTO;
	}
	
	/**
	 * Get books where id is start value and between end value.
	 * Using query params and HATEOAS gives the next link to the queries.
	 * @return
	 */
	@GET
	@Path("range")
	@Produces({ "application/xml", "application/json" })
	public Response getBooksFromRange(@DefaultValue("1") @QueryParam("start") long start, 
			@DefaultValue("1") @QueryParam("end")long end, @Context UriInfo uriInfo){
		
		URI uri = uriInfo.getAbsolutePath();
		Link next = null;
		
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Set<BookDTO> bookDTOset = new HashSet<BookDTO>();
		_logger.info("query book table where ids are between start - end");
		TypedQuery<Book> BookQuery = m.createQuery("SELECT b FROM Book b WHERE b.bookId BETWEEN :start AND :end", Book.class).setParameter("start", start).setParameter("end", end);
		List<Book> listBook = BookQuery.getResultList();
		if(listBook==null || listBook.isEmpty()){
			return Response.status(404).build();
		} else {
			_logger.info("Making NEXT link");
			next = Link.fromUri(uri + "?start={start}&end={end}")
					.rel("next")
					.build(end+1, end+(end-start)+1);
		}
		for(Book b : listBook){
			bookDTOset.add(DTOMapper.toBookDTO(b));
		}
		_logger.info("Wrap bookdtoset into a generic entity to pass through a response");
		GenericEntity<Set<BookDTO>> entity = new GenericEntity<Set<BookDTO>>(bookDTOset){};
		m.getTransaction().commit();
		m.close();
		_logger.info("add links only if there we have seen something and added to the set");
 		ResponseBuilder builder = Response.ok(entity);
 		if(next != null) {
 			builder.links(next);
 		}
 		Response response = builder.build();
		return response;
	}
	
	
	/**
	 * Get all books from the database
	 * @return
	 */
	@GET
	@Produces({ "application/xml", "application/json" })
	public Response getBooks(){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Set<BookDTO> bookDTOset = new HashSet<BookDTO>();
		_logger.info("Get all books in the database");
		TypedQuery<Book> BookQuery = m.createQuery("FROM Book", Book.class);
		List<Book> listBook = BookQuery.getResultList();
		if (listBook == null||listBook.isEmpty()) {
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		for(Book u : listBook){
			bookDTOset.add(DTOMapper.toBookDTO(u));
		}
		_logger.info("Wrap the converted dtobooks into a generic entity");
		GenericEntity<Set<BookDTO>> entity = new GenericEntity<Set<BookDTO>>(bookDTOset){};
		m.getTransaction().commit();
		m.close();
		return Response.ok(entity).build();
	}
	
	
	/**
	 * Get publisher for a book
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
		if (b == null) {
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		_logger.info("get publisher for book");
		Publisher publisher = b.get_publisher();
		m.getTransaction().commit();
		m.close();
		return publisher;
	}
	
	/**
	 * Subscribe method for async responses
	 */
	@GET
	@Path("/sub")
	@Produces({"application/xml","application/json"})
	public void subscribeToPage(@Suspended AsyncResponse response) {
		//add the response to the list of async responses
		_logger.info("adding the response to the local async response");
		asyncResponses.add(response);
	}
	
	/**
	 * Get the reviews for a particular book with id
	 * @param bookid
	 * @return
	 */
	@GET
	@Path("{id}/review")
	@Produces({"application/xml","application/json"})
	public Response getReview(@PathParam("id") long bookid){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("query nativesql with the table user_reviews, and get the user ids");
		Query q = m.createNativeQuery("SELECT USER_USERID FROM USER_REVIEWS WHERE BOOKREVIEWED_BOOKID=:id").setParameter("id", bookid);
		List<BigInteger> books = q.getResultList();
		_logger.info("Book size for review"+books.size());
		Set<ReviewDTO> rdtolist = new HashSet<ReviewDTO>();
		Book b = m.find(Book.class, bookid);
		if (b == null) {
			m.close();
			_logger.error("Entity not found");
			throw new EntityNotFoundException();
		}
		for(BigInteger u: books){
			User user = m.find(User.class, u.longValue());
			if (user == null) {
				m.close();
				_logger.error("Entity not found");
				throw new EntityNotFoundException();
			}
			for(Review r: user.getReviews()){
				if(r.getIsbn().equals(b.getIsbn())){
					rdtolist.add(DTOMapper.toReviewDTO(r));
				}
			}
		}
		_logger.info("Wrap the entity reviewdto to a genericentity");
		GenericEntity<Set<ReviewDTO>> entity = new GenericEntity<Set<ReviewDTO>>(rdtolist){};
		m.getTransaction().commit();
		m.close();
		return Response.ok(entity).build();
	}
}
