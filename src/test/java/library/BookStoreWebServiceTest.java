package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import library.content.printcontent.Address;
import library.content.printcontent.Author;
import library.content.printcontent.BookGenre;
import library.content.printcontent.ContentPrintType;
import library.content.printcontent.Ledger;
import library.content.printcontent.Orders;
import library.content.printcontent.PrintType;
import library.content.printcontent.Publisher;
import library.content.printcontent.User;
import library.content.printcontent.books.ArtBook;
import library.content.printcontent.books.BiographyBook;
import library.content.printcontent.books.ComicBook;
import library.content.printcontent.books.EducationalBook;
import library.content.printcontent.books.GenericBook;
import library.content.printcontent.books.Journal;
import library.content.printcontent.books.Magazine;
import main.library.utility.DefaultSchemaOutputResolver;
import main.library.utility.DefaultValidationEventHandler;

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
	private ContentPrintType artbook1;
	

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
		Address addressPublisher1 = new Address("1","Great Dane Street", "Oval", "Ottowa", "Canada", "1234");
		Address addressPublisher2 = new Address("666", "Devils Street", "Hells Kitchen", "Hell", "Australia", "6666");
		
		//2 publisher
		Publisher publisher1 = new Publisher(addressPublisher1, "Thompsons publishing");
		publisher1.set_id((long)1);
		Publisher publisher2 = new Publisher(addressPublisher2, "Nasty publishing");
		publisher2.set_id((long)2);
		//single user
		user = new User(addressUser);
		user.set_userId((long)1);
		
		//books
		artbook1 = new ArtBook("A",author1,2016,"book art", new BigDecimal("25.6"), PrintType.E_Book, publisher1);
		artbook1.set_bookId((long)1);
		BiographyBook biobook1 = new BiographyBook("B",author1,2013,"book bio", new BigDecimal("325.6"), PrintType.E_Book, publisher1);
		biobook1.set_bookId((long)2);
		ComicBook comicbook1 = new ComicBook("C",author1,2316,"book comic", new BigDecimal("225.6"), PrintType.E_Book, publisher1, BookGenre.Comedy);
		comicbook1.set_bookId((long)3);
		Journal jbook1 = new Journal("E",author1,3416,"book journal", new BigDecimal("235.6"), PrintType.E_Book, publisher1);
		jbook1.set_bookId((long)4);
		Magazine mbook1 = new Magazine("F",author3,2,"book mg", new BigDecimal("255.6"), PrintType.E_Book, publisher1,BookGenre.CookBook);
		mbook1.set_bookId((long)5);
		GenericBook gbook1 = new GenericBook("G",author4,123,"book gen", new BigDecimal("125.6"), PrintType.E_Book, publisher2,BookGenre.Comedy);
		gbook1.set_bookId((long)6);
		EducationalBook edubook1 = new EducationalBook("D",author2,2116,"book edu", new BigDecimal("25.6"), PrintType.E_Book, publisher2, BookGenre.Essay);
		edubook1.set_bookId((long)7);
		
		//library -- only has all books
		library = new Ledger();
		library.addPrintMedia(artbook1);
		library.addPrintMedia(biobook1);
		library.addPrintMedia(comicbook1);
		library.addPrintMedia(jbook1);
		library.addPrintMedia(edubook1);
		library.addPrintMedia(mbook1);
		library.addPrintMedia(gbook1);
		
		author1.addAuthoredBook(artbook1);
		author1.addAuthoredBook(biobook1);
		author1.addAuthoredBook(comicbook1);
		author1.addAuthoredBook(jbook1);
		author2.addAuthoredBook(edubook1);
		author3.addAuthoredBook(mbook1);
		author4.addAuthoredBook(gbook1);
		
		//orders -- for a user
		order1 = new Orders(user);
		order1.set_id((long)1);
		order1.addBookToOrder(artbook1);
		order1.addBookToOrder(biobook1);
		user.addOrder(order1);
	}
	
	/**
	 * Marshals a Book instance to XML, and directs the XML output to the 
	 * console.
	 * 
	 */
	@Test
	public void marshalBook() throws JAXBException {
		
		File file = new File("file.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Ledger.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(library, file);
		jaxbMarshaller.marshal(library, System.out);
		
		file = new File("file.xml");
		JAXBContext jaxbContext1 = JAXBContext.newInstance(Ledger.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
		Ledger customer = (Ledger) jaxbUnmarshaller.unmarshal(file);
		System.out.println(customer);
		/*
		for(ContentPrintType a: customer.getBooks()){
			System.out.println(a.get_title());
			System.out.println(a.get_year());
			System.out.println(a.get_description());
			System.out.println(a.get_author());
			System.out.println(a.get_bookId());
			System.out.println(a.get_cost());
			System.out.println(a.get_genre());
			System.out.println(a.get_publisher().get_id());
			System.out.println(a.get_publisher().get_address().get_city());
			System.out.println(a.get_publisher().get_address().get_country());
			System.out.println(a.get_publisher().get_address().get_houseNumber());
			System.out.println(a.get_publisher().get_address().get_street());
			System.out.println(a.get_publisher().get_address().get_suburb());
			System.out.println(a.get_publisher().get_address().get_zip());
			System.out.println(a.get_publisher().get_publisherName());
			System.out.println(a.get_printType());
			System.out.println(a.getClass());
			System.out.println();
		}
		*/
		System.out.println();
	}
	
	@Test
	public void marshalBook2() throws JAXBException {
		
		File file = new File("file2.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(user, file);
		jaxbMarshaller.marshal(user, System.out);
		
		file = new File("file2.xml");
		JAXBContext jaxbContext1 = JAXBContext.newInstance(User.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
		User customer = (User) jaxbUnmarshaller.unmarshal(file);		
		
	}
}
