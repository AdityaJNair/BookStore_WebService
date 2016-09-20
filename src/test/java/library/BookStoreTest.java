/**
 * 
 */
package library;

import static org.junit.Assert.*;

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
/*
 * ||=========================================================================================================================================================||
																			BOOK TESTING
 * ||=========================================================================================================================================================||
 */
	@Test
	public void testPOSTnGETnDELETEforBook(){
		//Initialise Dates to be used 
		Date date = new GregorianCalendar(1950, Calendar.JUNE, 1).getTime();
		// 3 different authors for each book authors
		Author author1 = new Author("William Shakespere", date, BookGenre.Drama, "Classical and one of the greatest...");
		// address for users and publishers
		Address addressPublisher2 = new Address("2", "Two Street", "Twoten", "Auckland", "New Zealand", "1432");
		Publisher publisher2 = new Publisher(addressPublisher2, "Brandons publishing services");
		// books
		Book book1 = new Book("Macbeth", date, author1, "Duncan why u do this", new BigDecimal("3.50"), PrintType.Other, publisher2,
				BookGenre.Novel, "ISBN-13:152-1-56619-505-4", "Russian");

		BookDTO bookDTOBook1 = DTOMapper.toBookDTO(book1);
		
		//POST BOOKS TO THE DATABASE, implicitly creates the AUTHORS USED IN THESE BOOKs -- Cascade.persist
		//Add book 1
		Response responseBook1 = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(bookDTOBook1));
		_logger.info("Response is :" + responseBook1.getStatusInfo());
		String locationBook1 = responseBook1.getLocation().toString();
		_logger.info(locationBook1);
		assertTrue(responseBook1.getStatus() == 201);
		responseBook1.close();
		
		_logger.info("Get book from given location from POST request - "+locationBook1.toString());
		//GET THE BOOKDTO FROM THE ADDRESS GIVEN
		BookDTO dtoBookGetBook3 = _client.target(locationBook1).request().accept("application/xml").get(BookDTO.class);
		assertEquals(dtoBookGetBook3,bookDTOBook1);
		
		//DELETE THE FIRST BOOK -- CHECK IF AUTHOR WAS DELETED FOR BOOK 1
		_logger.info("Deleting book that was created using POST request - "+ locationBook1.toString());
		_logger.info(bookDTOBook1.toString());
		Response deleteBook1 = _client.target(locationBook1).request().accept("application/xml").delete();
		assertTrue(deleteBook1.getStatus() == 200);
		deleteBook1.close();
		
		//READ A BOOK THAT HAS BEEN DELETED
		_logger.info("Raed deleted book - should return 404 not found");
		Response getDeletedBookResponse = _client.target(locationBook1).request().accept("application/xml").get();
		if(getDeletedBookResponse.getStatus() != 404){
			fail();
		}
		getDeletedBookResponse.close();
		
	}
	
	@Test
	public void testFailGetBook(){
		Response dtoBookGetBook3123 = _client.target(WEB_SERVICE_URI + "/book/1231123125").request().accept("application/xml").get();
		_logger.info("Check if the status is 404 not found as book id - 1231123125 doesnt exist");
		if(dtoBookGetBook3123.getStatus() == 404){
			_logger.info("Successful status code of 404");
			dtoBookGetBook3123.close();
		} else {
			fail();
		}
	}
	
	@Test
	public void testGetBookFromQueryParam(){
		//GET BASED ON QUERY PARAMETERS
		BookDTO dtoBookGetBasedOnNameandLanguage = _client.target(WEB_SERVICE_URI + "/book/name" + "?title=Inferno&language=Russian").request().get(BookDTO.class);
		_logger.info("Get book based on query parameters - title=Inferno&language=Russian");
		assertEquals(dtoBookGetBasedOnNameandLanguage.get_title(), "Inferno");
		assertEquals(dtoBookGetBasedOnNameandLanguage.getLanguage(), "Russian");		
	}
	
	
	@Test
	public void testAuthorAndPublisherForBook(){
		//GET THE BOOK USING THE ISBN FOR THAT BOOK -- The Shinning by Stephen King
		BookDTO dtoBookGetISBNBook = _client.target(WEB_SERVICE_URI+"/book/isbn/"+"ISBN-13:234-1-56619-909-4").request().accept("application/xml").get(BookDTO.class);
		_logger.info("dtoBookGetISBNBook has field:" + dtoBookGetISBNBook.toString());
		assertTrue(dtoBookGetISBNBook.getIsbn().equals("ISBN-13:234-1-56619-909-4"));
		
		//GET AUTHOR FOR BOOK1
		AuthorDTO dtoAuthorGetAuthorForBook1 = _client.target(WEB_SERVICE_URI+"/book/"+dtoBookGetISBNBook.get_bookId()+"/author").request().accept("application/xml").get(AuthorDTO.class);
		AuthorDTO realAuthorDTOBook1 = dtoBookGetISBNBook.get_author();
		_logger.info("dtoAuthorGetAuthorForBook1 has field:" + dtoBookGetISBNBook.get_author().toString());
		_logger.info("realAuthorDTOBook1 has field:" + realAuthorDTOBook1.toString());
		assertTrue(dtoAuthorGetAuthorForBook1.equals(dtoBookGetISBNBook.get_author()));
		
		//GET PUBLISHER -- book2 has publisher2
		Publisher bookPublisherFromDatabase = _client.target(WEB_SERVICE_URI+"/book/"+dtoBookGetISBNBook.get_bookId()+"/publisher").request().accept("application/xml").get(Publisher.class);
		_logger.info("book2PublisherFromDatabase has field:" + bookPublisherFromDatabase.toString());
		_logger.info("Publisher2 has field:" + dtoBookGetISBNBook.get_publisher().toString());
		assertTrue(bookPublisherFromDatabase.equals(dtoBookGetISBNBook.get_publisher()));
		
	}
	
	@Test
	public void getSetofBooks(){
		//GET BOOKS FROM A RANGE BASED ON ID
		//LOOKS AT IDS FOR BOOKS STARTING AT 1 TO 1000 == FOR THIS SMALL SET THE ENTIRE BOOK DATABASE
		//DIFFERENCE BETWEEN THIS AND THE NEXT METHOD IS THIS RETURNS A NEXT LINK USING HATEOAS TO THE NEXT INDEX RANGE TO SEE IF THE SET RETURNED IS NOT EMPTY
		Response responseGetBookFromSetQueryParam = _client.target(WEB_SERVICE_URI + "/book/range" + "?start=0&end=1000").request().get();
		Set<BookDTO> dataBaseSetOfBooks = responseGetBookFromSetQueryParam.readEntity(new GenericType<Set<BookDTO>>() {});
		for(BookDTO b: dataBaseSetOfBooks){
			_logger.info(b.toString());
		}
		
		//GET ALL BOOKS IN THE DATABASE
		Set<BookDTO> dataFullBaseSetOfBooks = _client.target(WEB_SERVICE_URI + "/book").request().accept("application/xml").get(new GenericType<Set<BookDTO>>() {});
		for(BookDTO b: dataFullBaseSetOfBooks){
			_logger.info(b.toString());
		}
		assertTrue(dataFullBaseSetOfBooks.size()== dataBaseSetOfBooks.size());
	}

	@Test
	public void addDuplicateBook(){
		//GET HARRY POTTER BOOK ALREADY IN DATABASE
		_logger.info("Get Harry Potter Book already in database");
		BookDTO bookDTOBook2 = _client.target(WEB_SERVICE_URI+"/book/isbn/"+"ISBN-13:673-1-56619-909-4").request().accept("application/xml").get(BookDTO.class);
		
		//DUPLICATE ADD IN BOOK2 WITH SAME AUTHOR -- should return 409 conflict and not add it to database again (single entry)
		Response responseBook2Duplicate = _client.target(WEB_SERVICE_URI + "/book").request().post(Entity.xml(bookDTOBook2));
		_logger.info("Return a 409 conflict response that the book already exists");
		if(responseBook2Duplicate.getStatus()!=409){
			_logger.error("Duplicate entry added with a post message of "+ responseBook2Duplicate.getStatusInfo() + " " +responseBook2Duplicate.getStatus());
			fail();
		} 
		assertTrue(responseBook2Duplicate.getStatus()==409);
		responseBook2Duplicate.close();
	}
	
	public void checkBookRangeIndatabase(){
		//HATEOAS AND QUERYPARAMETERS TESTING
		Response responseGetBookFromSetQueryParam = _client.target(WEB_SERVICE_URI + "/book/range" + "?start=0&end=1000").request().get();
		Set<BookDTO> dataBaseSetOfBooks = responseGetBookFromSetQueryParam.readEntity(new GenericType<Set<BookDTO>>() {});
		for(BookDTO b: dataBaseSetOfBooks){
			_logger.info(b.toString());
		}
		assertTrue(dataBaseSetOfBooks.size() == 2);
		responseGetBookFromSetQueryParam.close();
		
		_logger.info("Get the link for next set of items in database");
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
	}
	
	 /* ||=========================================================================================================================================================||
	 * 																				AUTHOR TESTING
	 * ||=========================================================================================================================================================||
	*/
	@Test
	public void testPOSTnGETforAuthor(){
		//INITIALISE NEW AUTHORS
		Date date4 = new GregorianCalendar(1916, Calendar.JANUARY, 30).getTime();
		Author author4 = new Author("Mark Twain", date4, BookGenre.Fiction, "Amazing...");
		AuthorDTO authorDTOauthor4 = DTOMapper.toAuthorDTO(author4);
	
		//POSTING THE AUTHORS MARK TWAIN	
		Response responseAuthor4 = _client.target(WEB_SERVICE_URI + "/author").request().post(Entity.xml(authorDTOauthor4));
		_logger.info("Response is :" + responseAuthor4.getStatusInfo());
		String locationAuthor4 = responseAuthor4.getLocation().toString();
		_logger.info(locationAuthor4);
		assertTrue(responseAuthor4.getStatus() == 201);
		responseAuthor4.close();
		
		_logger.info("Get author Mark Twain using the response location given from POST");
		Response responseMarkTwain = _client.target(locationAuthor4).request().accept("application/xml").get();
		AuthorDTO dtoAuthorMarkTwainIDGET = responseMarkTwain.readEntity(AuthorDTO.class);
		assertTrue(dtoAuthorMarkTwainIDGET.equals(authorDTOauthor4));
		//LINKS TO GET BOOKS FOR THIS ID AND SET THE DESCRIPTION FOR THIS ID AUTHOR
		Link bookLink  = responseMarkTwain.getLink("book");
		Link descriptionLink = responseMarkTwain.getLink("description");
		responseMarkTwain.close();
		
		//WORKING WITH LINKS HATEOAS
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
	}
	
	@Test
	public void getAuthorByName(){
		//GET AUTHOR FROM NAME -- Author Mark Twain which is author4
		AuthorDTO dtoAuthorDanBrownnNAMEGET = _client.target(WEB_SERVICE_URI+"/author/name/"+ "Dan Brown").request().accept("application/xml").get(AuthorDTO.class);
		//GET A SET OF BOOKS FROM THE AUTHOR - AUTHOR 4 (SHOULD BE 0 AS JUST ADDED) - USING HATEOAS LINKS
		Set<BookDTO> listbook = _client.target(WEB_SERVICE_URI+"/author/"+dtoAuthorDanBrownnNAMEGET.get_authorId()+"/book").request().accept("application/xml").get(new GenericType<Set<BookDTO>>() {});
		_logger.info("length of new author book list is = "+listbook.size());
		for(BookDTO b: listbook){
			_logger.info(b.toString());
		}
		assertTrue(listbook.size()==1);
	}
	
	public void getNonExistingAuthor(){
		//GET A NON EXISTING AUTHOR
		_logger.info("Get a non existing user with id - 651981651618");
		Response getNonExistingAuthor = _client.target(WEB_SERVICE_URI + "/author/651981651618").request().accept("application/xml").get();
		if(getNonExistingAuthor.getStatus() != 404){
			fail();
		}
		getNonExistingAuthor.close();
		
		_logger.info("Get a non existing user with id - 651981651618");
		Response getNonExistingAuthor2 = _client.target(WEB_SERVICE_URI + "/author/name/Tommy FakeAuthor").request().accept("application/xml").get();
		if(getNonExistingAuthor2.getStatus() != 404){
			fail();
		}
		getNonExistingAuthor2.close();
	}
	
	public void failAddDuplicateToDatabaseAuthor(){
		Date date1 = new GregorianCalendar(1425, Calendar.FEBRUARY, 2).getTime();
		Author author = new Author("Ernest Hemingway", date1, BookGenre.Fiction, "I have no response...");
		AuthorDTO authorDTOauthor = DTOMapper.toAuthorDTO(author);
		
		//GET SET OF ALL AUTHORS -- ADDED 5 IN TOTAL (3 FROM BOOK + 2 IN THIS PART TOF TEST)
		Set<AuthorDTO> listAuthor = _client.target(WEB_SERVICE_URI+"/author").request().accept("application/xml").get(new GenericType<Set<AuthorDTO>>() {});
		_logger.info("length of authors in database is = "+listAuthor.size());
		for(AuthorDTO a: listAuthor){
			_logger.info(a.toString());
		}
		
		//POST THIS AUTHOR
		Response responseAuthor = _client.target(WEB_SERVICE_URI + "/author").request().post(Entity.xml(authorDTOauthor));
		_logger.info("Response is :" + responseAuthor.getStatusInfo());
		String locationAuthor = responseAuthor.getLocation().toString();
		_logger.info(locationAuthor);
		assertTrue(responseAuthor.getStatus() == 201);
		responseAuthor.close();
		
		//GET SET OF ALL AUTHORS -- ADDED 5 IN TOTAL (3 FROM BOOK + 2 IN THIS PART TOF TEST)
		Set<AuthorDTO> listAuthor2 = _client.target(WEB_SERVICE_URI+"/author").request().accept("application/xml").get(new GenericType<Set<AuthorDTO>>() {});
		_logger.info("length of authors after adding 1 in database is = "+listAuthor2.size());
		for(AuthorDTO a: listAuthor2){
			_logger.info(a.toString());
		}
		assertTrue(listAuthor.size() == listAuthor2.size()+1);
		
		//POST AUTHOR AGAIN
		Response responseAuthorAgain = _client.target(WEB_SERVICE_URI + "/author").request().post(Entity.xml(authorDTOauthor));
		if(responseAuthorAgain.getStatus() == 409){
			_logger.info("Conflict detected and not persisted");
		} else {
			fail();
		}
		
	}
	
/*	* ||=========================================================================================================================================================||
			 * 																				USER TESTING
	 * ||=========================================================================================================================================================||*/
	
	@Test
	public void testUserPOSTnGETnDELETE(){
		Date dateUser1 = new GregorianCalendar(1960, Calendar.MARCH, 4).getTime();
		Address addressUser = new Address("21", "Green House Street", "Washinton", "Washinton DC", "USA", "2502");
		User user1 = new User(addressUser, "George Lucas", dateUser1,"testingGEORGE@gmail.com");
		UserDTO dtoUserUser1 = DTOMapper.toUserDTO(user1);
		
		//POST USERS
		Response responsePostUser1 = _client.target(WEB_SERVICE_URI + "/user").request().post(Entity.xml(dtoUserUser1));
		_logger.info("Response is :" + responsePostUser1.getStatusInfo());
		
		String locationUser1 = responsePostUser1.getLocation().toString();
		_logger.info(locationUser1);
		assertTrue(responsePostUser1.getStatus() == 201);
		responsePostUser1.close();
		
		//GET THE USER AND CHECK IF THE ARE THE SAME AS THE ONE POSTED
		//HATEOAS LINKS ARE USED TO ACCESS THE API CALLS FOR PUT AND DELETE AS PERSON MUST BE VERIFIED AS A USER OF THE BOOK STORE
		_logger.info("Check that the posted user has been successfuly posted and got from the database");
		Response responseGETUser1 = _client.target(locationUser1).request().get();
		Link toUserBooksForUser1 = responseGETUser1.getLink("book");
		Link toUserLoginForUser1 = responseGETUser1.getLink("login");
		Link toUserReviewForUser1 = responseGETUser1.getLink("review");
		_logger.info(toUserBooksForUser1.toString());
		_logger.info(toUserLoginForUser1.toString());
		_logger.info(toUserReviewForUser1.toString());
		UserDTO dtouser1 = responseGETUser1.readEntity(UserDTO.class);
		assertEquals(dtouser1,dtoUserUser1);
		responseGETUser1.close();
		
		//DELETE THE USER -- WITHOUT A COOKIE THIS IS AN UNAUTHORISED API CALL
		Response deleteUser1 = _client.target(locationUser1).request().delete();
		if(deleteUser1.getStatus() == 401){
			_logger.info("Successfull delete");
		} else {
			fail();
		}
		deleteUser1.close();
		
		//LOGIN USING THE LINKS GIVEN
		//NEED TO LOGIN FOR COOKIE TO GET ACCESS FOR ADDITIONAL UPDATE AND DELETE CALLS -- TO IDENTIFY A USER PART OF THE DATABASE
		//COOKIE FOR USER
		Response cookieLoginUser1 = _client.target(toUserLoginForUser1).request().accept("application/xml").get();
		NewCookie cookieUser1 = cookieLoginUser1.getCookies().get("username");
		cookieLoginUser1.close();
		_logger.info(cookieUser1.toString());
		
		//CAN ONLY DELETE USING THE COOKIE OF A VALID USER
		Response deleteUser1WithCookie = _client.target(locationUser1).request().cookie(cookieUser1).delete();
		if(deleteUser1WithCookie.getStatus() == 200){
			_logger.info("Successfull delete");
		} else {
			fail();
		}
		deleteUser1WithCookie.close();
		
		//CHECK IF DELETED USER IS ACTUALLY DELETED IN DATABASE
		_logger.info("Get the deleted user");
		Response responseGETDELETEDUser1 = _client.target(locationUser1).request().get();
		if(responseGETDELETEDUser1.getStatus() == 404){
			_logger.info("Not found non existing user");
		} else {
			fail();
		}
		responseGETDELETEDUser1.close();
		
	}
	
	@Test
	public void addReviewForBook(){
		Date dateUser1 = new GregorianCalendar(1911, Calendar.MARCH, 1).getTime();
		Address addressUser = new Address("1", "Blue House Street", "Washinton", "Washinton DC", "China", "654984");
		User user1 = new User(addressUser, "Tom Haarbooring", dateUser1,"testingTOM@gmail.com");
		UserDTO dtoUserUser1 = DTOMapper.toUserDTO(user1);
		
		//POST USERS
		Response responsePostUser1 = _client.target(WEB_SERVICE_URI + "/user").request().post(Entity.xml(dtoUserUser1));
		_logger.info("Response is :" + responsePostUser1.getStatusInfo());
		String locationUser1 = responsePostUser1.getLocation().toString();
		_logger.info(locationUser1);
		assertTrue(responsePostUser1.getStatus() == 201);
		responsePostUser1.close();
		
		//GET THE USER AND CHECK IF THE ARE THE SAME AS THE ONE POSTED
		//HATEOAS LINKS ARE USED TO ACCESS THE API CALLS FOR PUT AND DELETE AS PERSON MUST BE VERIFIED AS A USER OF THE BOOK STORE
		_logger.info("Check that the posted user has been successfuly posted and got from the database");
		Response responseGETUser1 = _client.target(locationUser1).request().get();
		Link toUserBooksForUser1 = responseGETUser1.getLink("book");
		Link toUserLoginForUser1 = responseGETUser1.getLink("login");
		Link toUserReviewForUser1 = responseGETUser1.getLink("review");
		_logger.info(toUserBooksForUser1.toString());
		_logger.info(toUserLoginForUser1.toString());
		_logger.info(toUserReviewForUser1.toString());
		UserDTO dtouser1 = responseGETUser1.readEntity(UserDTO.class);
		assertEquals(dtouser1,dtoUserUser1);
		responseGETUser1.close();
		
		//GETTING BOOKS BY ISBN -- ISBN-13:234-1-56619-909-4 -- ISBN-13:673-1-56619-909-4
		BookDTO dtoBookBook2 = _client.target(WEB_SERVICE_URI+"/book/isbn/"+"ISBN-13:234-1-56619-909-4").request().accept("application/xml").get(BookDTO.class);
		
		//CREATE REVIEW FOR USER 1
		_logger.info("Created a review");
		Review review1 = new Review("good", Rating.FOUR_STARS, "ISBN-13:234-1-56619-909-4");
		review1.setBookReviewed(DTOMapper.toBookDomain(dtoBookBook2));
				
		//ADD A REVIEW -- FROM USER 1 WHO HAS NOT LOGGED IN -- (ONLY USERS THAT ARE LOGGED IN CAN ADD REVIEWS)
		_logger.info("Add a review to user 1");
		Response responseAddReview1ToUser1 = _client.target(toUserReviewForUser1).request().accept("application/xml").put(Entity.xml(review1));
		assertTrue(responseAddReview1ToUser1.getStatus() == 401);
		responseAddReview1ToUser1.close();
		
		//LOGIN USING THE LINKS GIVEN
		//NEED TO LOGIN FOR COOKIE TO GET ACCESS FOR ADDITIONAL UPDATE AND DELETE CALLS -- TO IDENTIFY A USER PART OF THE DATABASE
		//COOKIE FOR USER
		Response cookieLoginUser1 = _client.target(toUserLoginForUser1).request().accept("application/xml").get();
		NewCookie cookieUser1 = cookieLoginUser1.getCookies().get("username");
		cookieLoginUser1.close();
		_logger.info(cookieUser1.toString());

		//ADD A REVIEW FOR THE SAME BOOK AGAIN ( THIS TIME LOGGED IN)
		Response responseAddReview2ToAuthor1 = _client.target(toUserReviewForUser1).request().cookie(cookieUser1).accept("application/xml").put(Entity.xml(review1));
		_logger.info("Failed review added");
		assertTrue(responseAddReview2ToAuthor1.getStatus() == 201);
		responseAddReview2ToAuthor1.close();		
				
		//CAN ONLY DELETE USING THE COOKIE OF A VALID USER
		Response deleteUser1WithCookie = _client.target(locationUser1).request().cookie(cookieUser1).delete();
		if(deleteUser1WithCookie.getStatus() == 200){
			_logger.info("Successfull delete");
		} else {
			fail();
		}
		deleteUser1WithCookie.close();
	}
	
	@Test
	public void checkuserDataBase(){
		//HATEOAS AND QUERYPARAMETERS TESTING
		//GET USERS FROM A RANGE BASED ON ID -- KNOWN USER ID AT 7 AND 8
		//THIS GETS A SET OF USERS AT STARTING INDEX 6 TO 7 (WHICH IS ONE ENTRY)
		Response responseGetUserFromSetQueryParam = _client.target(WEB_SERVICE_URI + "/user/range" + "?start=6&end=7").request().get();
		Set<UserDTO> dataBaseSetOfUsers = responseGetUserFromSetQueryParam.readEntity(new GenericType<Set<UserDTO>>() {});
		for(UserDTO b: dataBaseSetOfUsers){
			_logger.info(b.toString());
		}
		assertTrue(dataBaseSetOfUsers.size() == 1);
		responseGetUserFromSetQueryParam.close();
		
		//GET THE NEXT LINK FOR NEXT SET OF USERS AT INCREMENT SAME AS SET INITIALLY (6-7 INCREMENT OF 1) So 8-9
		Link nextUser = responseGetUserFromSetQueryParam.getLink("next");
		_logger.info(nextUser.toString());
	
		//GET THE NEXT SET
		Response responseGetUserNext1 = _client.target(nextUser).request().get();
		Set<UserDTO> dataBaseSetOfUsersNextPart = responseGetUserNext1.readEntity(new GenericType<Set<UserDTO>>() {});
		for(UserDTO b: dataBaseSetOfUsersNextPart){
			_logger.info(b.toString());
		}
		assertTrue(dataBaseSetOfUsersNextPart.size() == 1);
		
		//
		Link nextnextUser = responseGetUserNext1.getLink("next");
		_logger.info(nextnextUser.toString());
		responseGetUserNext1.close();
		
		//SINCE WE KNOW 2 USERS ONLY, RETURN A 404 NOT FOUND AS NO MORE USERSS
		Response responseGetUserNext2 = _client.target(nextnextUser).request().get();
		if(responseGetUserNext2.getStatus()!=404){
			fail();
			_logger.info("Got over the amount of people in db for users");
		}
		responseGetUserNext2.close();
	}
	
	@Test
	public void getNonExistingUser(){
		//GET A NON EXISTING USER
		Response getNonExistingUser = _client.target(WEB_SERVICE_URI + "/user/651981651618").request().accept("application/xml").get();
		if(getNonExistingUser.getStatus() != 404){
			fail();
		}
		getNonExistingUser.close();
		
		//GET A USER BY THEIR EMAIL
		_logger.info("Get a single user by email");
		Response dtoUserGetUser1Email = _client.target(WEB_SERVICE_URI+"/user/email/fakemail@gmail.com").request().accept("application/xml").get();
		if(getNonExistingUser.getStatus() != 404){
			fail();
		}
		dtoUserGetUser1Email.close();
	}
	
	@Test
	public void checkSizeOfUserDatabase(){
		//SINCE NOT ADDING ANY NEW -- DELETING THEM AT END OF TEST ONLY SHOULD BE 2 FROM DEFAULT INITIALISED DATABASE
		//GET A SET OF ALL USERS IN THE DATABASE
		Set<UserDTO> listUsers = _client.target(WEB_SERVICE_URI+"/user").request().accept("application/xml").get(new GenericType<Set<UserDTO>>() {});
		_logger.info("length of user database list is = "+listUsers.size());
		assertTrue(listUsers.size()==2);
		for(UserDTO a : listUsers){
			_logger.info(a.toString());
		}
	}
	
	@Test
	public void addOrderForUser(){
		Date dateUser1 = new GregorianCalendar(1765, Calendar.MARCH, 1).getTime();
		Address addressUser = new Address("1", "Wall Street Street", "New Mexico", "Washinton DC", "Argentina", "654984");
		User user1 = new User(addressUser, "Bobby Sharkings", dateUser1,"testingBOBBY@gmail.com");
		UserDTO dtoUserUser1 = DTOMapper.toUserDTO(user1);
		
		//POST USERS
		Response responsePostUser1 = _client.target(WEB_SERVICE_URI + "/user").request().post(Entity.xml(dtoUserUser1));
		_logger.info("Response is :" + responsePostUser1.getStatusInfo());
		String locationUser1 = responsePostUser1.getLocation().toString();
		_logger.info(locationUser1);
		assertTrue(responsePostUser1.getStatus() == 201);
		responsePostUser1.close();
		
		//GET THE USER AND CHECK IF THE ARE THE SAME AS THE ONE POSTED
		//HATEOAS LINKS ARE USED TO ACCESS THE API CALLS FOR PUT AND DELETE AS PERSON MUST BE VERIFIED AS A USER OF THE BOOK STORE
		_logger.info("Check that the posted user has been successfuly posted and got from the database");
		Response responseGETUser1 = _client.target(locationUser1).request().get();
		Link toUserBooksForUser1 = responseGETUser1.getLink("book");
		Link toUserLoginForUser1 = responseGETUser1.getLink("login");
		Link toUserReviewForUser1 = responseGETUser1.getLink("review");
		_logger.info(toUserBooksForUser1.toString());
		_logger.info(toUserLoginForUser1.toString());
		_logger.info(toUserReviewForUser1.toString());
		UserDTO dtouser1 = responseGETUser1.readEntity(UserDTO.class);
		assertEquals(dtouser1,dtoUserUser1);
		responseGETUser1.close();
		
		//GETTING BOOKS BY ISBN -- ISBN-13:234-1-56619-909-4 -- ISBN-13:673-1-56619-909-4 -- ISBN-13:152-1-56619-909-4
		BookDTO dtoBookBook1 = _client.target(WEB_SERVICE_URI+"/book/isbn/"+"ISBN-13:234-1-56619-909-4").request().accept("application/xml").get(BookDTO.class);
		BookDTO dtoBookBook2 = _client.target(WEB_SERVICE_URI+"/book/isbn/"+"ISBN-13:673-1-56619-909-4").request().accept("application/xml").get(BookDTO.class);
		
		
		//ADD ORDER TO USER -- means they purchased book.
		_logger.info("ADDING BOOKS TO USER USING ISBN BUT NOT VALID AS NO COOKIE FOR PROOF LOGGED IN");
		Response responseAddOrderToUser2 = _client.target(WEB_SERVICE_URI+"/user/"+dtouser1.get_userId()+"/order/isbn/"+dtoBookBook1.getIsbn()).request().accept("application/xml").put(null);
		assertTrue(responseAddOrderToUser2.getStatus() == 401);
		responseAddOrderToUser2.close();
		
		//LOGIN USING THE LINKS GIVEN
		//NEED TO LOGIN FOR COOKIE TO GET ACCESS FOR ADDITIONAL UPDATE AND DELETE CALLS -- TO IDENTIFY A USER PART OF THE DATABASE
		//COOKIE FOR USER
		Response cookieLoginUser1 = _client.target(toUserLoginForUser1).request().accept("application/xml").get();
		NewCookie cookieUser1 = cookieLoginUser1.getCookies().get("username");
		cookieLoginUser1.close();
		_logger.info(cookieUser1.toString());


		Response responseAddOrder1ToUser2 = _client.target(WEB_SERVICE_URI+"/user/"+dtouser1.get_userId()+"/order/isbn/"+dtoBookBook1.getIsbn()).request().cookie(cookieUser1).accept("application/xml").put(null);
		assertTrue(responseAddOrder1ToUser2.getStatus() == 201);
		responseAddOrder1ToUser2.close();
		
	
		Response responseAddOrder2ToUser2 = _client.target(WEB_SERVICE_URI+"/user/"+dtouser1.get_userId()+"/order/isbn/"+dtoBookBook2.getIsbn()).request().cookie(cookieUser1).accept("application/xml").put(null);
		assertTrue(responseAddOrder2ToUser2.getStatus() == 201);
		responseAddOrder2ToUser2.close();
		

		//GET ALL BOOKS FOR USER2
		Set<BookDTO> bookListUser2 = _client.target(toUserBooksForUser1).request().cookie(cookieUser1).accept("application/xml").get(new GenericType<Set<BookDTO>>() {});
		for(BookDTO b: bookListUser2){
			_logger.info(b.toString());
		}
		assertTrue(bookListUser2.size() == 2);
		
		//CAN ONLY DELETE USING THE COOKIE OF A VALID USER
		Response deleteUser1WithCookie = _client.target(locationUser1).request().cookie(cookieUser1).delete();
		if(deleteUser1WithCookie.getStatus() == 200){
			_logger.info("Successfull delete");
		} else {
			fail();
		}
		deleteUser1WithCookie.close();
		
	}


	@Test
	public void jsonTest(){
		
		
		
	}
	
	
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

		
	}
}
