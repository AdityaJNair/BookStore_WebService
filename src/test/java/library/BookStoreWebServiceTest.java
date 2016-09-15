package library;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.dto.OrdersDTO;
import library.content.dto.UserDTO;
import library.content.purchase.Address;
import library.content.purchase.Author;
import library.content.purchase.Book;
import library.content.purchase.BookGenre;
import library.content.purchase.Ledger;
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
	
	private Ledger library;
	private User user;
	private Orders order1;
	private Book book1;
	

	@BeforeClass
	public static void setUpJAXB() throws JAXBException, IOException, SAXException {
		/*
		_jaxbCxt = JAXBContext.newInstance(User.class, Ledger.class);
		_marshaller = _jaxbCxt.createMarshaller();
		_unmarshaller = _jaxbCxt.createUnmarshaller();
		_marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		new File(OUTPUT_PATH).mkdirs();
		SchemaOutputResolver sor = new DefaultSchemaOutputResolver(
				OUTPUT_PATH, XML_FILE_SCHEMA);
		_jaxbCxt.generateSchema(sor);
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory
				.newSchema(new File(OUTPUT_PATH + XML_FILE_SCHEMA));
		_unmarshaller.setSchema(schema);
		_unmarshaller.setEventHandler(new DefaultValidationEventHandler());
		*/
	}

	@Before
	public void setUp() throws Exception {
		//4 authors - 1 has done 2 books
		Author author1 = new Author("J.K Rowling", 51, BookGenre.Fantasy,"Cool");
		author1.set_authorId((long)1);
		Author author2 = new Author("Stephen King", 68, BookGenre.Horror,"Hero");
		author2.set_authorId((long)2);
		Author author3 = new Author("Mark Twain", 74, BookGenre.Novel,"Amazing");
		author3.set_authorId((long)3);
		Author author4 = new Author("Ernest Hemingway", 61, BookGenre.Fiction,"SY");
		author4.set_authorId((long)4);
		
		//address
		Address addressUser = new Address("21","Ulta Street","Penrose","Auckland","New Zealand", "2502");
				//single user
		user = new User(addressUser,"Bob");
		user.set_userId((long)1);
		
		//books
		Date date = new GregorianCalendar(1997, Calendar.NOVEMBER, 27).getTime();
		Address address = new Address("27", "McNaughton Street", "Onehunga", "Auckland", "New Zealand", "187154sdaw");
		Publisher publisher = new Publisher(address, "Thompsons publishing services");
		Book book = new Book("A",date , "Book description", new BigDecimal("50"), PrintType.HardCover, publisher, BookGenre.Novel, "1231", "English");
		book.addAuthorToSet(author1);
		book.addAuthorToSet(author2);
		book.set_bookId((long)0);
		book1 = new Book("B",date , "Book description", new BigDecimal("52"), PrintType.HardCover, publisher, BookGenre.Novel, "192.1231.1", "er");
		book1.set_bookId((long)1);
		book1.addAuthorToSet(author3);
		book1.addAuthorToSet(author4);
		Book book2 = new Book("C",date , "Book description", new BigDecimal("53"), PrintType.HardCover, publisher, BookGenre.Novel, "19223.1", "fd");
		book2.addAuthorToSet(author3);
		book2.set_bookId((long)2);
		Book book3 = new Book("D",date , "Book description", new BigDecimal("54"), PrintType.HardCover, publisher, BookGenre.Novel, "15168.1.1", "fe");
		book3.addAuthorToSet(author4);
		book3.set_bookId((long)3);
		
		//library -- only has all books
		library = new Ledger();
		library.addPrintMedia(book);
		library.addPrintMedia(book1);
		library.addPrintMedia(book2);
		library.addPrintMedia(book3);
		
		
		//orders -- for a user
		order1 = new Orders(user);
		order1.set_id((long)1);
		order1.addBookToOrder(book);
		order1.addBookToOrder(book2);
		order1.addBookToOrder(book3);
		user.addOrder(order1);
	}
	
	/**
	 * Marshals a Book instance to XML, and directs the XML output to the 
	 * console.
	 * 
	 */
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
		
		System.out.println();
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
		
		User usernorm = DTOMapper.toUserDomain(customer);
		
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
		
		Orders usernorm = DTOMapper.toOrdersDomain(customer);
		
	}
}