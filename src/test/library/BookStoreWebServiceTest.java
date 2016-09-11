package test.library;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import main.library.content.printcontent.Author;
import main.library.content.printcontent.Publisher;
import main.library.content.printcontent.books.ArtBook;
import main.library.content.printcontent.books.BiographyBook;
import main.library.content.printcontent.books.ComicBook;
import main.library.content.printcontent.books.EducationalBook;
import main.library.content.printcontent.books.Journal;
import main.library.content.printcontent.books.Magazine;
import main.library.content.purchase.Address;
import main.library.content.purchase.Library;
import main.library.content.purchase.Orders;
import main.library.content.purchase.User;
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
	

	@BeforeClass
	public static void setUpJAXB() throws JAXBException, IOException, SAXException {
		_jaxbCxt = JAXBContext.newInstance(User.class, Library.class);
		_marshaller = _jaxbCxt.createMarshaller();
		_unmarshaller = _jaxbCxt.createUnmarshaller();

		// Set Marshaller property so that generated XML is nicely formatted and
		// easy to read by humans. For machine-only processing, formatting isn't
		// required.
		_marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		// Generate the XML schema for classes known to the JAXB context.
		// Begin by creating the output directory if necessary.
		new File(OUTPUT_PATH).mkdirs();
		
		// Generate the schema, storing the resulting .xsd file in the output
		// directory.
		SchemaOutputResolver sor = new DefaultSchemaOutputResolver(
				OUTPUT_PATH, XML_FILE_SCHEMA);
		_jaxbCxt.generateSchema(sor);
		
		// Introduce the XML schema to the Unmarshaller. They Unmarshaller will
		// use the schema to validate XML documents during unmarshalling. Not
		// setting the schema, or passing in a null reference to setSchema()
		// disables validation. Start by creating a SchemaFactory, and use this
		// to create a Schema by loading the previously created .xsd file. 
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory
				.newSchema(new File(OUTPUT_PATH + XML_FILE_SCHEMA));
		_unmarshaller.setSchema(schema);
		
		// Set an error handler on the unmarshaller. The handler will be called
		// by the Unmarshaller if the XML document being unmarshalled contains 
		// errors.
		_unmarshaller.setEventHandler(new DefaultValidationEventHandler());
	}
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//4 authors - 1 has done 2 books
		Author author1 = new Author();
		Author author2 = new Author();
		Author author3 = new Author();
		Author author4 = new Author();
		
		//2 publisher
		Publisher publisher1 = new Publisher();
		Publisher publisher2 = new Publisher();
		
		//single user
		User user1 = new User();
		
		//address
		Address addressUser = new Address();
		Address addressPublisher1 = new Address();
		Address addressPublisher2 = new Address();
		
		//books
		ArtBook artbook1 = new ArtBook("Book of art", null, null, null, null, null, null, null);
		BiographyBook biobook1 = new BiographyBook();
		ComicBook comicbook1 = new ComicBook();
		EducationalBook edubook1 = new EducationalBook();
		Journal jbook1 = new Journal();
		Magazine mbook1 = new Magazine();
		
		//library -- only has all books
		Library library1 = new Library();
		
		//orders -- for a user
		Orders order1 = new Orders();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
