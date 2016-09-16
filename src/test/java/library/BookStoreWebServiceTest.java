package library;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;

import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.dto.OrdersDTO;
import library.content.dto.UserDTO;
import library.content.purchase.Address;
import library.content.purchase.Author;
import library.content.purchase.Book;
import library.content.purchase.BookGenre;
import library.content.purchase.Orders;
import library.content.purchase.PrintType;
import library.content.purchase.Publisher;
import library.content.purchase.User;


public class BookStoreWebServiceTest {
	private static final String WEB_SERVICE_URI = "http://localhost:10000";
	private static final String OUTPUT_PATH = "target/xml/";
	private static final String XML_FILE_SCHEMA = "simple.xsd";
	private static final String XML_FILE_SIMPLE = "simple.xml";
	
	private static JAXBContext _jaxbCxt = null;
	private static Marshaller _marshaller = null;
	private static Unmarshaller _unmarshaller = null;
	
	private User user;
	private Orders order1;
	private Book book;
	private Book book1;
	private Book book2;
	private Book book3;
	private Date date = new GregorianCalendar(1997, Calendar.NOVEMBER, 27).getTime();
	
	@Before
	public void setUp() throws Exception {

		//4 authors - 1 has done 2 books
		Author author1 = new Author("J.K Rowling", date, BookGenre.Fantasy,"Cool");
		Author author2 = new Author("Stephen King", date, BookGenre.Horror,"Hero");
		Author author3 = new Author("Mark Twain", date, BookGenre.Novel,"Amazing");
		Author author4 = new Author("Ernest Hemingway", date, BookGenre.Fiction,"SY");
		
		//address
		Address addressUser = new Address("21","Ulta Street","Penrose","Auckland","New Zealand", "2502");
		user = new User(addressUser,"Bob",date);
		
		//books
		Address address = new Address("27", "McNaughton Street", "Onehunga", "Auckland", "New Zealand", "187154sdaw");
		Publisher publisher = new Publisher(address, "Thompsons publishing services");
		book = new Book("A",date , "Book description", new BigDecimal("50"), PrintType.HardCover, publisher, BookGenre.Novel, "1231", "English");
		book.addAuthorToSet(author1);
		book1 = new Book("B",date , "Book description", new BigDecimal("52"), PrintType.HardCover, publisher, BookGenre.Novel, "192.1231.1", "er");
		book1.addAuthorToSet(author1);
		book2 = new Book("C",date , "Book description", new BigDecimal("53"), PrintType.HardCover, publisher, BookGenre.Novel, "19223.1", "fd");
		book2.addAuthorToSet(author1);
		book3 = new Book("D",date , "Book description", new BigDecimal("54"), PrintType.HardCover, publisher, BookGenre.Novel, "15168.1.1", "fe");
		book3.addAuthorToSet(author1);
		
		//orders -- for a user
		order1 = new Orders(user);
		order1.addBookToOrder(book);
		order1.addBookToOrder(book2);
		order1.addBookToOrder(book3);
		user.addOrder(order1);
	}
	
	@Test
	public void marshalBook() throws JAXBException {
		BookDTO bookdto = DTOMapper.toBookDTO(book1);
		
		File file = new File("file.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(BookDTO.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(bookdto, file);
		jaxbMarshaller.marshal(bookdto, System.out);
		
		file = new File("file.xml");
		JAXBContext jaxbContext1 = JAXBContext.newInstance(BookDTO.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
		
		BookDTO bookMADE = (BookDTO) jaxbUnmarshaller.unmarshal(file);
		
		Book bookdomain = DTOMapper.toBookDomain(bookMADE);
		
		System.out.println(bookdto.toString());
		System.out.println(bookMADE.toString());
		System.out.println(bookdto.toString().equals(bookMADE.toString()));
		System.out.println(bookdto.equals(bookMADE));
	}
	
	@Test
	public void marshalBook2() throws JAXBException {
		
		UserDTO userdto = DTOMapper.toUserDTO(user);
		
		File file = new File("file2.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(UserDTO.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(userdto, file);
		jaxbMarshaller.marshal(userdto, System.out);
		
		file = new File("file2.xml");
		JAXBContext jaxbContext1 = JAXBContext.newInstance(UserDTO.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
		UserDTO customer = (UserDTO) jaxbUnmarshaller.unmarshal(file);	
		
		System.out.println(customer.toString());
		System.out.println(userdto.toString());
		System.out.println(customer.toString().equals(userdto.toString()));
		System.out.println(customer.equals(userdto));
		
	}
	
	@Test
	public void marshalBook3() throws JAXBException {
		
		OrdersDTO userdto = DTOMapper.toOrdersDTO(order1);
		
		File file = new File("file2.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(OrdersDTO.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(userdto, file);
		jaxbMarshaller.marshal(userdto, System.out);
		
		file = new File("file2.xml");
		JAXBContext jaxbContext1 = JAXBContext.newInstance(OrdersDTO.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
		OrdersDTO customer = (OrdersDTO) jaxbUnmarshaller.unmarshal(file);	
		
		System.out.println(customer.toString());
		System.out.println(userdto.toString());
		System.out.println(customer.toString().equals(userdto.toString()));
		System.out.println(customer.equals(userdto));
	}
}