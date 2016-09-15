/**
 * 
 */
package library;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;

import library.content.purchase.Book;

/**
 * @author adijn
 *
 */
public class BookTest {

	private static final String WEB_SERVICE_URI = "http://localhost:10000/services/library";
	private static Client _client;

	/**
	 * One-time setup method that creates a Web service client.
	 */
	@BeforeClass
	public static void setUpClient() {
		_client = ClientBuilder.newClient();
	}
	
	@Test
	public void firstTest(){
/*		Author author = new Author("Dennis mattheyws", 46, BookGenre.Novel, "Heroic and above most amazing");
		Date date = new GregorianCalendar(1997, Calendar.NOVEMBER, 27).getTime();
		Address address = new Address("27", "McNaughton Street", "Onehunga", "Auckland", "New Zealand", "187154sdaw");
		Publisher publisher = new Publisher(address, "Thompsons publishing services");
		Book book = new Book("Willy wonka", author,date , "Book description", new BigDecimal("50"), PrintType.HardCover, publisher, BookGenre.Novel, "192.168.1.1", "English");
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("bookstorePU");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(book);
		em.close();*/
/*		List<Parolee> parolees = _client
				.target(WEB_SERVICE_URI + "?start=1&size=3").request()
				.accept("application/xml")
				.get(new GenericType<List<Parolee>>() {
				});*/
		
	}
	

}
