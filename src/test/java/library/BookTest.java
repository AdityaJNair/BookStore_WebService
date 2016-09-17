/**
 * 
 */
package library;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.dto.OrdersDTO;
import library.content.dto.UserDTO;
import library.content.purchase.Address;
import library.content.purchase.Author;
import library.content.purchase.Book;
import library.content.purchase.Orders;
import library.content.purchase.Publisher;
import library.content.purchase.Review;
import library.content.purchase.User;
import library.content.purchase.enums.BookGenre;
import library.content.purchase.enums.PrintType;
import library.content.purchase.enums.Rating;
import library.service.BookResource;
import library.service.PersistenceManager;

/**
 * @author adijn
 *
 */
public class BookTest {
	private static final Logger _logger = LoggerFactory.getLogger(BookTest.class);
	private static final String WEB_SERVICE_URI = "http://localhost:10000/services";
	private static Client _client;



	/**
	 * One-time setup method that creates a Web service client.
	 */
	@BeforeClass
	public static void setUpClient() {
		_client = ClientBuilder.newClient();
	}

	@Test
	public void firstTest() {
		Date date = new GregorianCalendar(1964, Calendar.JUNE, 22).getTime();
		Date date1 = new GregorianCalendar(1899, Calendar.JULY, 21).getTime();
		Date date2 = new GregorianCalendar(1965, Calendar.JULY, 31).getTime();
		Date date3 = new GregorianCalendar(1835, Calendar.NOVEMBER, 30).getTime();
		Date date4 = new GregorianCalendar(1947, Calendar.SEPTEMBER, 21).getTime();
		Date date5 = new GregorianCalendar(1995, Calendar.NOVEMBER, 27).getTime();
		Date date6 = new GregorianCalendar(1890, Calendar.NOVEMBER, 1).getTime();
		
		// 5 authors - 1 has done 2 books
		Author author = new Author("Dan Brown", date, BookGenre.Mystery, "Dan has worked extremly...");
		Author author1 = new Author("J.K Rowling", date2, BookGenre.Fantasy, "One of the greats...");
		Author author2 = new Author("Stephen King", date4, BookGenre.Horror, "RUN!!....");
		Author author3 = new Author("Mark Twain", date3, BookGenre.Fiction, "Amazing...");
		Author author4 = new Author("Ernest Hemingway", date1, BookGenre.Fiction, "I have no response...");

		// address
		Address addressUser = new Address("21", "Parliment House Street", "Wellington", "Auckland", "New Zealand", "2502");
		Address addressUser2 = new Address("23", "Parliment House Street", "Wellington", "Auckland", "New Zealand", "2502");
		Address address1 = new Address("152", "Dove Street", "Darby", "London", "England", "156-198-19");
		Address address = new Address("2", "McNaughton Street", "Onehunga", "Auckland", "New Zealand", "1432");
		
		Publisher publisher = new Publisher(address1, "Thompsons publishing services");
		Publisher publisher1 = new Publisher(address, "Brandons publishing services");
		
		// books
		Book book = new Book("Inferno", date, author1, "Book description", new BigDecimal("12.50"), PrintType.Other, publisher,
				BookGenre.Novel, "ISBN-13: 152-1-56619-909-4", "Russian");

		Book book1 = new Book("Harry Potter", date1, author1, "Book description", new BigDecimal("52.60"), PrintType.HardCover, publisher,
				BookGenre.Speech, "ISBN-13: 673-1-56619-909-4", "Maori");

		Book book2 = new Book("The Shinning", date2, author1, "Book description", new BigDecimal("23.00"), PrintType.E_Book, publisher,
				BookGenre.NonFiction, "ISBN-13: 234-1-56619-909-4", "Dutch");

		Book book3 = new Book("Tom Sawyer", date3, author1, "Book description", new BigDecimal("104.00"), PrintType.Paperback, publisher,
				BookGenre.CookBook, "ISBN-13: 666-1-56619-909-4", "French");

		Book book4 = new Book("Old man and the sea", date4, author1, "Book description", new BigDecimal("265.00"),
				PrintType.HardCover, publisher1, BookGenre.Crime, "ISBN-13: 001-1-56619-909-4", "English");
		
		Book book5 = new Book("Dutchman", date4, author1, "Book description", new BigDecimal("5.00"),
				PrintType.HardCover, publisher1, BookGenre.Article, "ISBN-13: 999-1-56619-909-4", "Japanese");
		
		User user = new User(addressUser, "Adi", date5,"testing@gmail.com");
		
		User user1 = new User(addressUser2, "Tim", date6,"testing@yahoo.com");

		Orders order = new Orders(user);
		order.addBookToOrder(book);
		order.addBookToOrder(book1);
		order.addBookToOrder(book2);
		order.addBookToOrder(book3);
		order.addBookToOrder(book4);
		
		OrdersDTO orderdto = DTOMapper.toOrdersDTO(order);
		
		Review r = new Review("good", Rating.FOUR_STARS, "ISBN-13: 152-1-56619-909-4");
		r.setBookReviewed(book);
		Review r1 = new Review("bad", Rating.ONE_STAR, "ISBN-13: 673-1-56619-909-4");
		r.setBookReviewed(book1);
		Review r2 = new Review("mediocre", Rating.ONE_STAR, "ISBN-13: 234-1-56619-909-4");
		r.setBookReviewed(book2);
		Review r3 = new Review("meh", Rating.TWO_STARS, "ISBN-13: 666-1-56619-909-4");
		r.setBookReviewed(book3);
		Review r4 = new Review("amazing", Rating.FIVE_STARS, "ISBN-13: 001-1-56619-909-4");
		r.setBookReviewed(book4);
		
		user.addReview(r);
		user.addReview(r1);
		user.addReview(r2);
		user.addReview(r3);
		user.addReview(r4);
		
		user1.addReview(r);
		user1.addReview(r1);
		user1.addReview(r2);
		user1.addReview(r3);
		user1.addReview(r4);
				

		BookDTO b1 = DTOMapper.toBookDTO(book);
		BookDTO b2 = DTOMapper.toBookDTO(book1);
		BookDTO b3 = DTOMapper.toBookDTO(book2);
		BookDTO b4 = DTOMapper.toBookDTO(book3);
		BookDTO b5 = DTOMapper.toBookDTO(book4);
		BookDTO b6 = DTOMapper.toBookDTO(book5);
		
		UserDTO u1 = DTOMapper.toUserDTO(user);
		
		Response response = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(b1));
		_logger.info("Response is :" + response.getStatusInfo());
		String location = response.getLocation().toString();
		_logger.info(location);
		response.close();

		Response response1 = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(b2));
		_logger.info("Response is :" + response1.getStatusInfo());
		String location1 = response1.getLocation().toString();
		_logger.info(location1);
		response1.close();

		Response response2 = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(b3));
		_logger.info("Response is :" + response2.getStatusInfo());
		String location2 = response2.getLocation().toString();
		_logger.info(location2);
		response2.close();

		Response response3 = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(b4));
		_logger.info("Response is :" + response3.getStatusInfo());
		String location3 = response3.getLocation().toString();
		_logger.info(location3);
		response3.close();

		Response response4 = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(b5));
		_logger.info("Response is :" + response4.getStatusInfo());
		String location4 = response4.getLocation().toString();
		_logger.info(location4);
		response4.close();
		
		Response response41 = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(b6));
		_logger.info("Response is :" + response41.getStatusInfo());
		String location41 = response41.getLocation().toString();
		_logger.info(location41);
		response41.close();
		

		
		BookDTO bb1 = _client.target(location).request().accept("application/xml").get(BookDTO.class);
		BookDTO bb2 = _client.target(location1).request().accept("application/xml").get(BookDTO.class);
		BookDTO bb3 = _client.target(location2).request().accept("application/xml").get(BookDTO.class);
		BookDTO bb4 = _client.target(location3).request().accept("application/xml").get(BookDTO.class);
		BookDTO bb5 = _client.target(location41).request().accept("application/xml").get(BookDTO.class);
		
		_client.target(location).request().accept("application/xml").delete(BookDTO.class);

		//BookDTO bb7 = _client.target(WEB_SERVICE_URI + "/book/30").request().accept("application/xml").get(BookDTO.class);
		

		_logger.info(bb1.toString());
		_logger.info(bb2.toString());
		_logger.info(bb3.toString());
		_logger.info(bb4.toString());
		_logger.info(bb5.toString());

		//List<BookDTO> bblist = (List<BookDTO>) _client.target(WEB_SERVICE_URI + "/book").request().accept("application/xml").get(BookDTO.class);
		
	}

	/*
	 * @Test public void secondtest(){ EntityManager m =
	 * PersistenceManager.instance().createEntityManager();
	 * m.getTransaction().begin();
	 * 
	 * 
	 * //orders -- for a user order1 = new Orders(user);
	 * order1.addBookToOrder(book); order1.addBookToOrder(book2);
	 * order1.addBookToOrder(book3); user.addOrder(order1);
	 * 
	 * 
	 * 
	 * Author author2 = new Author("Stephen King", date,
	 * BookGenre.Horror,"Hero"); m.persist(author2); Author author3 = new
	 * Author("Mark Twain", date, BookGenre.Novel,"Amazing");
	 * m.persist(author3); Author author4 = new Author("Ernest Hemingway", date,
	 * BookGenre.Fiction,"SY"); m.persist(author4);
	 * 
	 * Author author1 = new Author("J.K Rowling", date,
	 * BookGenre.Fantasy,"Cool"); m.persist(author1);
	 * 
	 * Address addressUser1 = new
	 * Address("21","Ulta Street","Penrose","Auckland","New Zealand", "2502");
	 * user = new User(addressUser1,"Bob",date); m.persist(user);
	 * 
	 * User user1 = new User(addressUser1,"Tom",date); m.persist(user1);
	 * 
	 * _logger.info("comparing user1 with user "+user1.equals(user)); Address
	 * address = new Address("27", "McNaughton Street", "Onehunga", "Auckland",
	 * "New Zealand", "187154sdaw");
	 * 
	 * Publisher publisher = new Publisher(address,
	 * "Thompsons publishing services");
	 * 
	 * book = new Book("A",date , "Book description", new BigDecimal("50"),
	 * PrintType.HardCover, publisher, BookGenre.Novel, "1231", "English");
	 * m.persist(book); book.addAuthorToSet(author1);
	 * book.addAuthorToSet(author2);
	 * 
	 * book1 = new Book("B",date , "Book description", new BigDecimal("52"),
	 * PrintType.HardCover, publisher, BookGenre.Novel, "192.1231.1", "er");
	 * book1.addAuthorToSet(author1); m.persist(book1);
	 * 
	 * book2 = new Book("C",date , "Book description", new BigDecimal("53"),
	 * PrintType.HardCover, publisher, BookGenre.Novel, "19223.1", "fd");
	 * book2.addAuthorToSet(author1); m.persist(book2);
	 * 
	 * book3 = new Book("D",date , "Book description", new BigDecimal("54"),
	 * PrintType.HardCover, publisher, BookGenre.Novel, "15168.1.1", "fe");
	 * book3.addAuthorToSet(author1); m.persist(book3);
	 * 
	 * 
	 * Review r = new Review("BAD",Rating.FIVE_STARS,"1231"); user.addReview(r);
	 * r.setBookReviewed(book);
	 * 
	 * Review r1 = new Review("BAAD",Rating.FIVE_STARS,"192.1231.1");
	 * user.addReview(r1); r1.setBookReviewed(book1);
	 * 
	 * 
	 * m.getTransaction().commit(); m.close(); }
	 */
}
