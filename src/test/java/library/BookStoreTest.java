/**
 * 
 */
package library;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import library.content.dto.AuthorDTO;
import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.dto.UserDTO;
import library.content.purchase.Address;
import library.content.purchase.Author;
import library.content.purchase.Book;
import library.content.purchase.Publisher;
import library.content.purchase.Review;
import library.content.purchase.User;
import library.content.purchase.enums.BookGenre;
import library.content.purchase.enums.PrintType;
import library.content.purchase.enums.Rating;

/**
 * Test class for the REST api
 * THE DATABASE IS NOT INITIALISED PREVIOUSLY
 * ALL OBJECTS ARE ADDED AND WORKED ON IN THE TEST METHOD. SINCE TESTS CAN OCCUR IN ANY ORDER THAT IS WHY I USED ONE TEST METHOD.
 * IF I DID A PRE-INITIALISED DATABASE I WOULD HAVE SEPARATE METHODS, BUT I FELT THAT THIS SHOWS A TRUER REPRESENTATION OF THE MODEL.
 * TEST METHOD HAS ASSERTS AFTER EVERY TEST IMPLEMENTATION
 * @author adijn
 *
 */
public class BookStoreTest {
	private static final Logger _logger = LoggerFactory.getLogger(BookStoreTest.class);
	private static final String WEB_SERVICE_URI = "http://localhost:10000/services";
	private static Client _client;



	/**
	 * One-time setup method that creates a Web service client.
	 */
	@BeforeClass
	public static void setUpClient() {
		_client = ClientBuilder.newClient();
	}

	/**
	 * Main test case
	 */
	@Test
	public void testREST() {
		
		/*
		 * Initialise domain objects that are going to be used.
		 */
		
		//Initialise Dates to be used 
		Date date = new GregorianCalendar(1964, Calendar.JUNE, 22).getTime();
		Date date1 = new GregorianCalendar(1899, Calendar.JULY, 21).getTime();
		Date date2 = new GregorianCalendar(1965, Calendar.JULY, 31).getTime();
		Date date3 = new GregorianCalendar(1835, Calendar.NOVEMBER, 30).getTime();
		Date date4 = new GregorianCalendar(1947, Calendar.SEPTEMBER, 21).getTime();
		
		// 3 different authors for each book authors
		Author author1 = new Author("Dan Brown", date, BookGenre.Mystery, "Dan has worked extremly...");
		Author author2 = new Author("J.K Rowling", date2, BookGenre.Fantasy, "One of the greats...");
		Author author3 = new Author("Stephen King", date4, BookGenre.Horror, "RUN!!....");
		

		// address for users and publishers
		Address addressPublisher1 = new Address("152", "Dove Street", "Darby", "London", "England", "156-198-19");
		Address addressPublisher2 = new Address("2", "McNaughton Street", "Onehunga", "Auckland", "New Zealand", "1432");
		
		Publisher publisher1 = new Publisher(addressPublisher1, "Thompsons publishing services");
		Publisher publisher2 = new Publisher(addressPublisher2, "Brandons publishing services");
		
		// books
		Book book1 = new Book("Inferno", date, author1, "Book description", new BigDecimal("12.50"), PrintType.Other, publisher1,
				BookGenre.Novel, "ISBN-13:152-1-56619-909-4", "Russian");

		Book book2 = new Book("Harry Potter", date1, author2, "Book description", new BigDecimal("52.60"), PrintType.HardCover, publisher2,
				BookGenre.Speech, "ISBN-13:673-1-56619-909-4", "Maori");

		Book book3 = new Book("The Shinning", date2, author3, "Book description", new BigDecimal("23.00"), PrintType.E_Book, publisher1,
				BookGenre.NonFiction, "ISBN-13:234-1-56619-909-4", "Dutch");


		//Map the domain books to the dto books
		BookDTO bookDTOBook1 = DTOMapper.toBookDTO(book1);
		BookDTO bookDTOBook2 = DTOMapper.toBookDTO(book2);
		BookDTO bookDTOBook3 = DTOMapper.toBookDTO(book3);	

		/*
		 * ||=========================================================================================================================================================||
		 * 																				BOOK TESTING
		 * ||=========================================================================================================================================================||
		 */
		
		//POST BOOKS TO THE DATABASE, implicitly creates the AUTHORS USED IN THESE BOOKs -- Cascade.persist
		//Add book 1
		Response responseBook1 = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(bookDTOBook1));
		_logger.info("Response is :" + responseBook1.getStatusInfo());
		String locationBook1 = responseBook1.getLocation().toString();
		_logger.info(locationBook1);
		assertTrue(responseBook1.getStatus() == 201);
		responseBook1.close();
		
		//Add book 2
		Response responseBook2 = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(bookDTOBook2));
		_logger.info("Response is :" + responseBook2.getStatusInfo());
		String locationBook2 = responseBook2.getLocation().toString();
		_logger.info(locationBook2);
		assertTrue(responseBook2.getStatus() == 201);
		responseBook2.close();

		//Add book 3
		Response responseBook3 = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(bookDTOBook3));
		_logger.info("Response is :" + responseBook3.getStatusInfo());
		String locationBook3 = responseBook3.getLocation().toString();
		_logger.info(locationBook3);
		assertTrue(responseBook3.getStatus() == 201);		
		responseBook3.close();

		//GET THE BOOKDTO FROM THE ADDRESS GIVEN
		BookDTO dtoBookGetBook3 = _client.target(locationBook3).request().accept("application/xml").get(BookDTO.class);
		
		//GET BASED ON QUERY PARAMETERS
		BookDTO dtoBookGetBasedOnNameandLanguage = _client.target(WEB_SERVICE_URI + "/book/name" + "?title=Inferno&language=Russian").request().get(BookDTO.class);
		_logger.info("Get book based on query parameters");
		assertTrue(dtoBookGetBasedOnNameandLanguage.equals(bookDTOBook1));

		//GET A NON EXISTING BOOK
		Response dtoBookGetBook3123 = _client.target(WEB_SERVICE_URI + "/book/12315").request().accept("application/xml").get();
		if(dtoBookGetBook3123.getStatus() != 404){
			fail();
		}
		dtoBookGetBook3123.close();

		
		//GET THE BOOK USING THE ISBN FOR THAT BOOK
		BookDTO dtoBookGetISBNBook3 = _client.target(WEB_SERVICE_URI+"/book/isbn/"+book3.getIsbn()).request().accept("application/xml").get(BookDTO.class);
		_logger.info("dtoBookGetISBNBook3 has field:" + dtoBookGetISBNBook3.toString());
		_logger.info("dtoBookGetBook3 has field:" + dtoBookGetBook3.toString());
		assertTrue(dtoBookGetISBNBook3.equals(dtoBookGetBook3));
		
		//GET AUTHOR FOR BOOK1
		AuthorDTO dtoAuthorGetAuthorForBook1 = _client.target(locationBook1+"/author").request().accept("application/xml").get(AuthorDTO.class);
		AuthorDTO realAuthorDTOBook1 = bookDTOBook1.get_author();
		_logger.info("dtoAuthorGetAuthorForBook1 has field:" + dtoAuthorGetAuthorForBook1.toString());
		_logger.info("realAuthorDTOBook1 has field:" + realAuthorDTOBook1.toString());
		assertTrue(dtoAuthorGetAuthorForBook1.equals(realAuthorDTOBook1));
		
		//GET PUBLISHER -- book2 has publisher2
		Publisher book2PublisherFromDatabase = _client.target(locationBook2+"/publisher").request().accept("application/xml").get(Publisher.class);
		_logger.info("book2PublisherFromDatabase has field:" + book2PublisherFromDatabase.toString());
		_logger.info("Publisher2 has field:" + publisher2.toString());
		assertTrue(book2PublisherFromDatabase.equals(publisher2));
		
		//HATEOAS AND QUERYPARAMETERS TESTING
		//GET BOOKS FROM A RANGE BASED ON ID
		Response responseGetBookFromSetQueryParam = _client.target(WEB_SERVICE_URI + "/book/range" + "?start=0&end=3").request().get();
		Set<BookDTO> dataBaseSetOfBooks = responseGetBookFromSetQueryParam.readEntity(new GenericType<Set<BookDTO>>() {});
		for(BookDTO b: dataBaseSetOfBooks){
			_logger.info(b.toString());
		}
		assertTrue(dataBaseSetOfBooks.size() == 2);
		responseGetBookFromSetQueryParam.close();
		//GET THE NEXT LINK FOR NEXT SET OF BOOKS
		Link next = responseGetBookFromSetQueryParam.getLink("next");
	
		//GET THE NEXT SET
		Response responseGetBookNext1 = _client.target(next).request().get();
		Set<BookDTO> dataBaseSetOfBooksNextPart = responseGetBookNext1.readEntity(new GenericType<Set<BookDTO>>() {});
		for(BookDTO b: dataBaseSetOfBooksNextPart){
			_logger.info(b.toString());
		}
		assertTrue(dataBaseSetOfBooksNextPart.size() == 1);
		Link nextnext = responseGetBookNext1.getLink("next");
		responseGetBookNext1.close();
		
		//SINCE WE KNOW 3 BOOKS ONLY, RETURN A 404 NOT FOUND AS NO MORE BOOKS
		Response responseGetBookNext2 = _client.target(nextnext).request().get();
		if(responseGetBookNext2.getStatus()!=404){
			fail();
			_logger.info("Got over the amount of people in db");
		}
		responseGetBookNext2.close();
		
		//GET ALL BOOKS IN THE DATABASE
		Set<BookDTO> dataFullBaseSetOfBooks = _client.target(WEB_SERVICE_URI + "/book").request().accept("application/xml").get(new GenericType<Set<BookDTO>>() {});
		for(BookDTO b: dataFullBaseSetOfBooks){
			_logger.info(b.toString());
		}
		assertTrue(dataFullBaseSetOfBooks.size()==3);
		
		//DELETE THE FIRST BOOK -- CHECK IF AUTHOR WAS DELETED FOR BOOK 1
		Response deleteBook1 = _client.target(locationBook1).request().accept("application/xml").delete();
		assertTrue(deleteBook1.getStatus() == 200);
		deleteBook1.close();
		
		//READ A BOOK THAT HAS BEEN DELETED
		Response getDeletedBookResponse = _client.target(locationBook1).request().accept("application/xml").get();
		if(getDeletedBookResponse.getStatus() != 404){
			fail();
		}
		getDeletedBookResponse.close();
		
		
		//GET THE ENTIRE BOOK SET OF THE DATABASE WHERE ONE BOOK WAS DELETED
		Set<BookDTO> dataBaseSetOfBookTest2 = _client.target(WEB_SERVICE_URI + "/book"+ "?start=1&size=500").request().accept("application/xml").get(new GenericType<Set<BookDTO>>() {});
		for(BookDTO b: dataBaseSetOfBookTest2){
			_logger.info(b.toString());
		}
		assertTrue(dataBaseSetOfBookTest2.size() == 2);
		
		
		//DUPLICATE ADD IN BOOK2 WITH SAME AUTHOR -- should return 409 conflict
		Response responseBook2Duplicate = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(bookDTOBook2));
		if(responseBook2Duplicate.getStatus()!=409){
			_logger.error("Duplicate entry added with a post message of "+ responseBook2Duplicate.getStatusInfo() + " " +responseBook2Duplicate.getStatus());
			fail();
		} 
		assertTrue(responseBook2Duplicate.getStatus()==409);
		responseBook2Duplicate.close();
		
		/*
		 * ||=========================================================================================================================================================||
		 * 																				AUTHOR TESTING
		 * ||=========================================================================================================================================================||
		 */

		//INITIALISE NEW AUTHORS
		Author author4 = new Author("Mark Twain", date3, BookGenre.Fiction, "Amazing...");
		Author author5 = new Author("Ernest Hemingway", date1, BookGenre.Fiction, "I have no response...");
		
		//AUTHOR DTO NEW ENTRIES
		AuthorDTO authorDTOauthor4 = DTOMapper.toAuthorDTO(author4);
		AuthorDTO authorDTOauthor5 = DTOMapper.toAuthorDTO(author5);
	
		//POSTING THE TWO AUTHORS 		
		Response responseAuthor4 = _client.target(WEB_SERVICE_URI + "/author").request().post(Entity.xml(authorDTOauthor4));
		_logger.info("Response is :" + responseAuthor4.getStatusInfo());
		String locationAuthor4 = responseAuthor4.getLocation().toString();
		_logger.info(locationAuthor4);
		assertTrue(responseAuthor4.getStatus() == 201);
		responseAuthor4.close();
		
		Response responseAuthor5 = _client.target(WEB_SERVICE_URI + "/author").request().post(Entity.xml(authorDTOauthor5));
		_logger.info("Response is :" + responseAuthor5.getStatusInfo());
		String locationAuthor5 = responseAuthor5.getLocation().toString();
		_logger.info(locationAuthor5);
		assertTrue(responseAuthor5.getStatus() == 201);
		responseAuthor5.close();
				
		//GET A NON EXISTING AUTHOR
		Response getNonExistingAuthor = _client.target(WEB_SERVICE_URI + "/author/651981651618").request().accept("application/xml").get();
		if(getNonExistingAuthor.getStatus() != 404){
			fail();
		}
		getNonExistingAuthor.close();
		
		
		//ADDING A DUPLICATE AUTHOR
		Response responseDuplicateAuthor5 = _client.target(WEB_SERVICE_URI + "/author").request().post(Entity.xml(authorDTOauthor5));
		assertTrue(responseDuplicateAuthor5.getStatus() == 409);
		responseDuplicateAuthor5.close();		
		
		//GET AUTHOR FROM NAME -- Author Mark Twain which is author4
		AuthorDTO dtoAuthorMarkTwainNAMEGET = _client.target(WEB_SERVICE_URI+"/author/name/"+ "Mark Twain").request().accept("application/xml").get(AuthorDTO.class);
		Response responseMarkTwain = _client.target(locationAuthor4).request().accept("application/xml").get();
		
		//LINKS TO GET BOOKS FOR THIS ID AND SET THE DESCRIPTION FOR THIS ID AUTHOR
		Link bookLink  = responseMarkTwain.getLink("book");
		Link descriptionLink = responseMarkTwain.getLink("description");

		AuthorDTO dtoAuthorMarkTwainIDGET = responseMarkTwain.readEntity(AuthorDTO.class);
		responseMarkTwain.close();
		assertTrue(dtoAuthorMarkTwainNAMEGET.equals(dtoAuthorMarkTwainIDGET));
		
		//GET SET OF ALL AUTHORS -- ADDED 5 IN TOTAL (3 FROM BOOK + 2 IN THIS PART TOF TEST)
		Set<AuthorDTO> listAuthor = _client.target(WEB_SERVICE_URI+"/author").request().accept("application/xml").get(new GenericType<Set<AuthorDTO>>() {});
		_logger.info("length of authors in database is = "+listAuthor.size());
		for(AuthorDTO a: listAuthor){
			_logger.info(a.toString());
		}
		assertTrue(listAuthor.size()==5);
		
		//UPDATE AUTHOR DESCRIPTION -- USING LINKS (HATEOAS)
		_logger.info("Description for Author4 " +dtoAuthorMarkTwainIDGET.get_description());
		Response responsePutAuthor4Description = _client.target(descriptionLink).request().accept("application/xml").put(Entity.xml(new String("BELIEVE ME PLEASE, I AM A REAL AUTHOR. BUY MY BOOKS!!.")));
		responsePutAuthor4Description.close();
		//GET AUTHOR AND CHECK IF UPDATE IS WORKING
		AuthorDTO dtoUpdatedDescriptionAuthor4 = _client.target(locationAuthor4).request().accept("application/xml").get(AuthorDTO.class);
		_logger.info("updated description for Author4 " +dtoUpdatedDescriptionAuthor4.get_description());
		assertTrue(!dtoAuthorMarkTwainIDGET.get_description().equals(dtoUpdatedDescriptionAuthor4.get_description()));
		
		
		//GET A SET OF BOOKS FROM THE AUTHOR - AUTHOR 4 (SHOULD BE 0 AS JUST ADDED) - USING HATEOAS LINKS
		Set<BookDTO> listbook = _client.target(bookLink).request().accept("application/xml").get(new GenericType<Set<BookDTO>>() {});
		_logger.info("length of new author book list is = "+listbook.size());
		assertTrue(listbook.size()==0);
		/*
		 * ||=========================================================================================================================================================||
		 * 																				USER TESTING
		 * ||=========================================================================================================================================================||
		 */
		
		//USER BIRTH DATES
		Date dateUser1 = new GregorianCalendar(2000, Calendar.MARCH, 31).getTime();
		Date dateUser2 = new GregorianCalendar(1900, Calendar.SEPTEMBER, 21).getTime();
		
		//USER ADDRESSES
		Address addressUser = new Address("21", "Parliment House Street", "Wellington", "Auckland", "New Zealand", "2502");
		Address addressUser2 = new Address("23", "Parliment House Street", "Wellington", "Auckland", "New Zealand", "2502");
		
		//USERS
		User user1 = new User(addressUser, "Adi", dateUser1,"testingADI@gmail.com");
		User user2 = new User(addressUser2, "Tim", dateUser2,"testingTIM@yahoo.com");
		
		//GETTING BOOKS BY ISBN -- ISBN-13:234-1-56619-909-4 -- ISBN-13:673-1-56619-909-4
		BookDTO dtoBookBook2 = _client.target(WEB_SERVICE_URI+"/book/isbn/"+"ISBN-13:234-1-56619-909-4").request().accept("application/xml").get(BookDTO.class);
		
		//CONVETING THE BOOKS TO DTO BOOKS
		Book domainUserTestBook2 = DTOMapper.toBookDomain(dtoBookBook2);
								
		//CONVERTING USER TO DTOS
		UserDTO dtoUserUser1 = DTOMapper.toUserDTO(user1);
		UserDTO dtoUserUser2 = DTOMapper.toUserDTO(user2);
		
		//POST USERS
		Response responsePostUser1 = _client.target(WEB_SERVICE_URI + "/user").request().post(Entity.xml(dtoUserUser1));
		_logger.info("Response is :" + responsePostUser1.getStatusInfo());
		String locationUser1 = responsePostUser1.getLocation().toString();
		_logger.info(locationUser1);
		assertTrue(responsePostUser1.getStatus() == 201);
		responsePostUser1.close();
		
		Response responsePostUser2 = _client.target(WEB_SERVICE_URI + "/user").request().post(Entity.xml(dtoUserUser2));
		_logger.info("Response is :" + responsePostUser2.getStatusInfo());
		String locationUser2 = responsePostUser2.getLocation().toString();
		_logger.info(locationUser2);
		assertTrue(responsePostUser2.getStatus() == 201);
		responsePostUser2.close();	
		
		//GET A SET OF ALL USERS IN THE DATABASE
		Set<UserDTO> listUsers = _client.target(WEB_SERVICE_URI+"/user").request().accept("application/xml").get(new GenericType<Set<UserDTO>>() {});
		_logger.info("length of user database list is = "+listUsers.size());
		assertTrue(listUsers.size()==2);
		for(UserDTO a : listUsers){
			_logger.info(a.toString());
		}
		
		//HATEOAS AND QUERYPARAMETERS TESTING
		//GET USERS FROM A RANGE BASED ON ID
		Response responseGetUserFromSetQueryParam = _client.target(WEB_SERVICE_URI + "/user/range" + "?start=10&end=11").request().get();
		Set<UserDTO> dataBaseSetOfUsers = responseGetUserFromSetQueryParam.readEntity(new GenericType<Set<UserDTO>>() {});
		for(UserDTO b: dataBaseSetOfUsers){
			_logger.info(b.toString());
		}
		assertTrue(dataBaseSetOfUsers.size() == 1);
		responseGetUserFromSetQueryParam.close();
		//GET THE NEXT LINK FOR NEXT SET OF UserS
		Link nextUser = responseGetUserFromSetQueryParam.getLink("next");
	
		//GET THE NEXT SET
		Response responseGetUserNext1 = _client.target(nextUser).request().get();
		Set<UserDTO> dataBaseSetOfUsersNextPart = responseGetUserNext1.readEntity(new GenericType<Set<UserDTO>>() {});
		for(UserDTO b: dataBaseSetOfUsersNextPart){
			_logger.info(b.toString());
		}
		assertTrue(dataBaseSetOfUsersNextPart.size() == 1);
		Link nextnextUser = responseGetUserNext1.getLink("next");
		responseGetUserNext1.close();
		
		//SINCE WE KNOW 2 USERS ONLY, RETURN A 404 NOT FOUND AS NO MORE USERSS
		Response responseGetUserNext2 = _client.target(nextnextUser).request().get();
		if(responseGetUserNext2.getStatus()!=404){
			fail();
			_logger.info("Got over the amount of people in db for users");
		}
		responseGetUserNext2.close();
				
		//GET A SINGLE USER -- THIS LINK WILL BE USED IN THE END TEST FOR GETTING ALL BOOKS
		_logger.info("Get a single user by id for User1");
		Response responseDTOUser1 = _client.target(locationUser1).request().accept("application/xml").get();
		UserDTO dtoUserGetUser1 = responseDTOUser1.readEntity(UserDTO.class);
		assertTrue(dtoUserGetUser1.equals(dtoUserUser1));
		Link toUserBooksForUser1 = responseDTOUser1.getLink("book");
		Link toUserLoginForUser1 = responseDTOUser1.getLink("login");
		Link toUserReviewForUser1 = responseDTOUser1.getLink("review");
		_logger.info(toUserBooksForUser1.toString());
		_logger.info(toUserLoginForUser1.toString());
		_logger.info(toUserReviewForUser1.toString());
		responseDTOUser1.close();
		
		_logger.info("Get a single user by id for User2");
		Response responseDTOUser2 = _client.target(locationUser2).request().accept("application/xml").get();
		UserDTO dtoUserGetUser2 = responseDTOUser2.readEntity(UserDTO.class);
		assertTrue(dtoUserGetUser2.equals(dtoUserUser2));
		Link toUserBooksForUser2 = responseDTOUser2.getLink("book");
		Link toUserLoginForUser2 = responseDTOUser2.getLink("login");
		Link toUserReviewForUser2 = responseDTOUser2.getLink("review");
		_logger.info(toUserBooksForUser2.toString());
		_logger.info(toUserLoginForUser2.toString());
		_logger.info(toUserReviewForUser2.toString());
		responseDTOUser2.close();
				
		//GET A NON EXISTING USER
		Response getNonExistingUser = _client.target(WEB_SERVICE_URI + "/user/651981651618").request().accept("application/xml").get();
		if(getNonExistingUser.getStatus() != 404){
			fail();
		}
		getNonExistingUser.close();
		
		//GET A USER BY THEIR EMAIL
		_logger.info("Get a single user by email");
		UserDTO dtoUserGetUser1Email = _client.target(WEB_SERVICE_URI+"/user/email/"+user1.getEmail()).request().accept("application/xml").get(UserDTO.class);
		assertTrue(dtoUserGetUser1Email.equals(dtoUserUser1));
						
		//NEED TO LOGIN FOR COOKIE TO GET ACCESS FOR ADDITIONAL UPDATE AND DELETE CALLS -- TO IDENTIFY A USER PART OF THE DATABASE
		//COOKIE FOR USER2
		Response cookieLoginUser2 = _client.target(toUserLoginForUser2).request().accept("application/xml").get();
		NewCookie cookieUser2 = cookieLoginUser2.getCookies().get("username");
		cookieLoginUser2.close();
		_logger.info(cookieUser2.toString() + " " + cookieUser2.getName() + " " + cookieUser2.getValue());
		
		//CREATE REVIEW FOR USER 1
		_logger.info("Created a review");
		Review review1 = new Review("good", Rating.FOUR_STARS, "ISBN-13:234-1-56619-909-4");
		review1.setBookReviewed(domainUserTestBook2);
				
		//ADD A REVIEW -- FROM USER 1 WHO HAS NOT LOGGED IN -- (ONLY USERS THAT ARE LOGGED IN CAN ADD REVIEWS)
		_logger.info("Add a review to user 1");
		Response responseAddReview1ToUser1 = _client.target(toUserReviewForUser1).request().accept("application/xml").put(Entity.xml(review1));
		assertTrue(responseAddReview1ToUser1.getStatus() == 401);
		responseAddReview1ToUser1.close();
		
		//USER DELETE A USER -- NOT VALID (ONLY USERS THAT ARE LOGGED IN CAN DELETE USERS)4
		
		Response deleteUser1Fail = _client.target(locationUser1).request().accept("application/xml").delete();
		assertTrue(deleteUser1Fail.getStatus() == 401);
		deleteUser1Fail.close();
		
		//USER 1 LOGGED IN NOW AND HAS A COOKIE
		Response cookieLoginUser1 = _client.target(toUserLoginForUser1).request().accept("application/xml").get();
		NewCookie cookieUser1 = cookieLoginUser1.getCookies().get("username");
		cookieLoginUser1.close();
		_logger.info(cookieUser1.toString() + " " + cookieUser1.getName() + " " + cookieUser1.getValue() + " " + cookieLoginUser1.getCookies().toString());
		
		//ADD A REVIEW FOR THE SAME BOOK AGAIN ( THIS TIME LOGGED IN)
		Response responseAddReview2ToAuthor1 = _client.target(toUserReviewForUser1).request().cookie(cookieUser1).accept("application/xml").put(Entity.xml(review1));
		_logger.info("Failed review added");
		assertTrue(responseAddReview2ToAuthor1.getStatus() == 201);
		responseAddReview2ToAuthor1.close();		
				
		//DELETE A USER --  that has a review
		Response deleteUser1 = _client.target(locationUser1).request().cookie(cookieUser1).accept("application/xml").delete();
		assertTrue(deleteUser1.getStatus() == 200);
		deleteUser1.close();
		
		//GET THE USER THAT HAS BEEN DELETED
		Response getNonExistingUserAfterDelete = _client.target(locationUser1).request().accept("application/xml").get();
		if(getNonExistingUserAfterDelete.getStatus() != 404){
			fail();
		}
		getNonExistingUserAfterDelete.close();
		
		//ADD ORDER TO USER -- means they purchased book.
		_logger.info("ADDING BOOKS TO USER2 USING ISBN");
		Response responseAddOrderToUser2 = _client.target(locationUser2+"/order/isbn/"+book3.getIsbn()).request().cookie(cookieUser2).accept("application/xml").put(null);
		assertTrue(responseAddOrderToUser2.getStatus() == 201);
		responseAddOrderToUser2.close();

		Response responseAddOrder2ToUser2 = _client.target(locationUser2+"/order/isbn/"+book2.getIsbn()).request().cookie(cookieUser2).accept("application/xml").put(null);
		assertTrue(responseAddOrder2ToUser2.getStatus() == 201);
		responseAddOrder2ToUser2.close();
		
		//GET ALL BOOKS FOR USER2
		Set<BookDTO> bookListUser2 = _client.target(toUserBooksForUser2).request().cookie(cookieUser2).accept("application/xml").get(new GenericType<Set<BookDTO>>() {});
		for(BookDTO b: bookListUser2){
			_logger.info(b.toString());
		}
		assertTrue(bookListUser2.size() == 2);
		
		

		
		//END
	}
	
	/*@After
	@Test
	public void asyncTest() throws InterruptedException, ExecutionException{
		Date dateAsync = new GregorianCalendar(2000, Calendar.MARCH, 22).getTime();
		Author author1 = new Author("Matt Damon", dateAsync, BookGenre.Mystery, "Im also an actor");
		Address addressPublisher1 = new Address("2", "Bird Street", "Darby", "Oceania", "England", "156-198-19");
		Publisher publisher1 = new Publisher(addressPublisher1, "Thompsons publishing services");
		Book book1 = new Book("Inferno", dateAsync, author1, "Book Is about Matt Damons acting", new BigDecimal("122.50"), PrintType.HardCover, publisher1,
				BookGenre.Art, "ISBN-22:23923:235", "Korean");
		final BookDTO book1DTO = DTOMapper.toBookDTO(book1);
		
		Client clientReceiver = ClientBuilder.newClient( );
		
		Future<BookDTO> target = clientReceiver.target(WEB_SERVICE_URI + "book/subscribe").request().async().get(new InvocationCallback<BookDTO>() { 
			public void completed(BookDTO book) {  
				org.junit.Assert.assertTrue(book.equals(book1DTO));
				fail();
				_logger.info(book.toString());
			}                  
			public void failed(Throwable t) { 
				fail("Failed to test asynchronous");
			}            
		});
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Client clientSender = ClientBuilder.newClient( );
		Response rcreate = clientSender.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(book1DTO));		
		rcreate.close();
		Response rdelete = clientSender.target(rcreate.getLocation()).request().delete();
		rdelete.close();

		
	}*/
}
