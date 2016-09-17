/**
 * 
 */
package library;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.purchase.Address;
import library.content.purchase.Author;
import library.content.purchase.Book;
import library.content.purchase.BookGenre;
import library.content.purchase.Orders;
import library.content.purchase.PrintType;
import library.content.purchase.Publisher;
import library.content.purchase.User;
import library.service.PersistenceManager;

/**
 * @author adijn
 *
 */
public class BookTest {

	private static final String WEB_SERVICE_URI = "http://localhost:10000/services";
	private static Client _client;

	private User user;
	private Orders order1;
	private Book book;
	private Book book1;
	private Book book2;
	private Book book3;
	private Date date = new GregorianCalendar(1997, Calendar.NOVEMBER, 27).getTime();
	
	/**
	 * One-time setup method that creates a Web service client.
	 */
	@BeforeClass
	public static void setUpClient() {
		_client = ClientBuilder.newClient();
	}

	@Test
	public void firstTest() {

		//4 authors - 1 has done 2 books
		Author author1 = new Author("J.K Rowling", date, BookGenre.Fantasy,"Cool");
		Author author2 = new Author("Stephen King", date, BookGenre.Horror,"Hero");
		Author author3 = new Author("Mark Twain", date, BookGenre.Novel,"Amazing");
		Author author4 = new Author("Ernest Hemingway", date, BookGenre.Fiction,"SY");
		
		//address
		Address addressUser = new Address("21","Ulta Street","Penrose","Auckland","New Zealand", "2502");
		user = new User(addressUser,"Bob",date);
		
		//books
		Address address1 = new Address("27", "McNaughton Street", "Onehunga", "Auckland", "New Zealand", "187154sdaw");
		Publisher publisher = new Publisher(address1, "Thompsons publishing services");
		book = new Book("A",date , "Book description", new BigDecimal("50"), PrintType.HardCover, publisher, BookGenre.Novel, "1231", "English");
		book.addAuthorToSet(author1);
		book1 = new Book("B",date , "Book description", new BigDecimal("52"), PrintType.HardCover, publisher, BookGenre.Novel, "192.1231.1", "er");
		book1.addAuthorToSet(author1);
		book2 = new Book("C",date , "Book description", new BigDecimal("53"), PrintType.HardCover, publisher, BookGenre.Novel, "19223.1", "fd");
		book2.addAuthorToSet(author1);
		book3 = new Book("D",date , "Book description", new BigDecimal("54"), PrintType.HardCover, publisher, BookGenre.Novel, "15168.1.1", "fe");
		book3.addAuthorToSet(author1);
		
	
		Author author = new Author("Dennis mattheyws", date, BookGenre.Novel,"Heroic and above most amazing");
		Date date = new GregorianCalendar(1997, Calendar.NOVEMBER, 27).getTime();
		Address address = new Address("27", "McNaughton Street", "Onehunga","Auckland", "New Zealand", "187154sdaw");
		Publisher publisher1 = new Publisher(address,"Thompsons publishing services");
		Book book4 = new Book("Willy wonka", date, "Book description",new BigDecimal("50"), PrintType.HardCover, publisher1,BookGenre.Novel, "192.168.1.1", "English");
		book4.addAuthorToSet(author);
		
		BookDTO b1 = DTOMapper.toBookDTO(book4);
		BookDTO b2 = DTOMapper.toBookDTO(book);
		BookDTO b3 = DTOMapper.toBookDTO(book1);
		BookDTO b4 = DTOMapper.toBookDTO(book2);
		BookDTO b5 = DTOMapper.toBookDTO(book3);

		Response response = _client.target(WEB_SERVICE_URI+"/book").request().post(Entity.xml(b1));
		System.out.println(response.getStatusInfo());
		String location = response.getLocation().toString();
		response.close();
		
		Response response1 = _client.target(WEB_SERVICE_URI+"/book").request().post(Entity.xml(b2));
		System.out.println(response1.getStatusInfo());
		String location1 = response1.getLocation().toString();
		response1.close();
		
		Response response2 = _client.target(WEB_SERVICE_URI+"/book").request().post(Entity.xml(b3));
		System.out.println(response2.getStatusInfo());
		String location2 = response2.getLocation().toString();
		response2.close();
		
		Response response3 = _client.target(WEB_SERVICE_URI+"/book").request().post(Entity.xml(b4));
		System.out.println(response3.getStatusInfo());
		String location3 = response3.getLocation().toString();
		response3.close();
		
		Response response4 = _client.target(WEB_SERVICE_URI+"/book").request().post(Entity.xml(b5));
		System.out.println(response4.getStatusInfo());
		String location4 = response4.getLocation().toString();
		response4.close();
		
		BookDTO bb1 = _client.target(location).request().accept("application/xml").get(BookDTO.class);
		BookDTO bb2 = _client.target(location1).request().accept("application/xml").get(BookDTO.class);
		BookDTO bb3 = _client.target(location2).request().accept("application/xml").get(BookDTO.class);
		BookDTO bb4 = _client.target(location3).request().accept("application/xml").get(BookDTO.class);
		BookDTO bb5 = _client.target(location4).request().accept("application/xml").get(BookDTO.class);
		
		System.out.println(bb1.toString());
		System.out.println(bb2.toString());
		System.out.println(bb3.toString());
		System.out.println(bb4.toString());
		System.out.println(bb5.toString());
	}
	
	@Test
	public void secondtest(){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		
		Author author1 = new Author("J.K Rowling", date, BookGenre.Fantasy,"Cool");
		m.persist(author1);

		Address addressUser = new Address("21","Ulta Street","Penrose","Auckland","New Zealand", "2502");
		user = new User(addressUser,"Bob",date);
		
		Address address = new Address("27", "McNaughton Street", "Onehunga", "Auckland", "New Zealand", "187154sdaw");
		
		Publisher publisher = new Publisher(address, "Thompsons publishing services");
		
		book = new Book("A",date , "Book description", new BigDecimal("50"), PrintType.HardCover, publisher, BookGenre.Novel, "1231", "English");
		book.addAuthorToSet(author1);
		m.persist(book);
		
		book1 = new Book("B",date , "Book description", new BigDecimal("52"), PrintType.HardCover, publisher, BookGenre.Novel, "192.1231.1", "er");
		book1.addAuthorToSet(author1);
		m.persist(book1);
		
		book2 = new Book("C",date , "Book description", new BigDecimal("53"), PrintType.HardCover, publisher, BookGenre.Novel, "19223.1", "fd");
		book2.addAuthorToSet(author1);
		m.persist(book2);
		
		book3 = new Book("D",date , "Book description", new BigDecimal("54"), PrintType.HardCover, publisher, BookGenre.Novel, "15168.1.1", "fe");
		book3.addAuthorToSet(author1);
		m.persist(book3);
		
		m.getTransaction().commit();
		m.close();
	}

}
