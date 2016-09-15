package library.service;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.purchase.Address;
import library.content.purchase.Author;
import library.content.purchase.Book;
import library.content.purchase.BookGenre;
import library.content.purchase.PrintType;
import library.content.purchase.Publisher;


@Path("/library")
public class BookResource {
	private static final Logger _logger = LoggerFactory.getLogger(BookResource.class);

	
	//get book
	/**
	 * 
	 */
	@GET
	@Path("library/{bookname}")
	@Produces("application/xml")
	public Book getBook(@PathParam("bookname") String bookName){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("bookstorePU");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		try{
			TypedQuery<Book> query = em.createQuery("select b, from Book b"+
						"where b.Book_Title = "+bookName, Book.class);
			Book book = query.getSingleResult();
			return book;
		}catch (NonUniqueResultException e1){
		    throw new WebApplicationException(
		    	      Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
		    	        .entity("fail")
		    	        .build()
		    	    );
		}catch (NoResultException e){
		    throw new WebApplicationException(
		    	      Response.status(HttpURLConnection.HTTP_NOT_FOUND)
		    	        .entity("book not found")
		    	        .build()
		    	    );
		}
	}
	
	@GET
	@Produces("application/xml")
	public List<Book> getBooks(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("bookstorePU");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		//REMOVE LATER
		Author author = new Author("Dennis mattheyws", 46, BookGenre.Novel, "Heroic and above most amazing");
		Date date = new GregorianCalendar(1997, Calendar.NOVEMBER, 27).getTime();
		Address address = new Address("27", "McNaughton Street", "Onehunga", "Auckland", "New Zealand", "187154sdaw");
		Publisher publisher = new Publisher(address, "Thompsons publishing services");
		Book book = new Book("Willy wonka", date , "Book description", new BigDecimal("50"), PrintType.HardCover, publisher, BookGenre.Novel, "192.168.1.1", "English");
		book.addAuthorToSet(author);
		em.persist(book);		
		try{
			TypedQuery<Book> query = em.createQuery("select b, from Book b", Book.class);
			List<Book> bookList = query.getResultList();
			return bookList;
		}catch (NonUniqueResultException e1){
		    throw new WebApplicationException(
		    	      Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
		    	        .entity("fail")
		    	        .build()
		    	    );
		}catch (NoResultException e){
		    throw new WebApplicationException(
		    	      Response.status(HttpURLConnection.HTTP_NOT_FOUND)
		    	        .entity("book not found")
		    	        .build()
		    	    );
		}
		
	}
	
	@POST
	@Consumes("application/xml")
	public void addBook(BookDTO bookdto){
		Book bookDom = DTOMapper.toBookDomain(bookdto);
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("bookstorePU");
		EntityManager em = factory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(bookDom);
		et.commit();
		em.close();
	}
	
	
	
}
