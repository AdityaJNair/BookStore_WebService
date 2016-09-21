/**
 * 
 */
package library.service;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import library.content.domain.Book;
import library.content.domain.Review;
import library.content.domain.User;
import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.dto.ReviewDTO;
import library.content.dto.UserDTO;

/**
 * Resource class for Users. Able to create users, delete users, read users and put book orders into a user class.
 * Also allowed to add a review
 * @author adijn
 *
 */
@Path("/user")
public class UserResource {
	private static final Logger _logger = LoggerFactory.getLogger(UserResource.class);

	/**
	 * Get User from id
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces({"application/xml","application/json"})
	public Response getUser(@PathParam("id") long id, @Context UriInfo uriInfo){
		URI uri = uriInfo.getAbsolutePath();
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("check if the user exists");
		User b = m.find(User.class, id);
		if(b==null){
			m.close();
			_logger.info("Entity not found");
		    throw new EntityNotFoundException();
		}
		UserDTO b1 = DTOMapper.toUserDTO(b);
		m.getTransaction().commit();
		m.close();
		_logger.info("add quick links to next api calls for user");
		Link userLink = Link.fromUri(uri+"/book").rel("book").build();
		Link userLogin = Link.fromUri(uri+"/loginservice"+"?name="+b1.getUserName()).rel("login").build();
		Link userReview = Link.fromUri(uri+"/review").rel("review").build();
		ResponseBuilder builder = Response.ok(b1);
		builder.links(userLink,userLogin,userReview);
		return builder.build();
	}
	
	/**
	 * Get user dto object from email field
	 * @param id
	 * @return AuthorDTO object
	 */
	@GET
	@Path("email/{email}")
	@Produces({"application/xml","application/json"})
	public UserDTO getBook(@PathParam("email") String email){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User u = null;
		try{
			_logger.info("querying for user where the email is = email given in path param");
			u = m.createQuery("SELECT u FROM User u WHERE u.email=:email", User.class).setParameter("email", email).getSingleResult();

		}catch(PersistenceException e){
			m.close();
			_logger.info("Entity not found");
			throw new EntityNotFoundException();
		}
		if(u==null){
			m.close();
			_logger.info("Entity not found");
		    throw new EntityNotFoundException();
		}
		_logger.info("return dto for user");
		UserDTO udto = DTOMapper.toUserDTO(u);
		m.getTransaction().commit();
		m.close();
		return udto;
	}
	
	
	/**
	 * Add a user to the database
	 * @param userdto
	 * @return
	 */
	@POST
	@Consumes({ "application/xml", "application/json" })
	public Response addUser(UserDTO userdto) {
		User domainUser = DTOMapper.toUserDomain(userdto);
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		try{
			_logger.info("persist user if not exist");
			m.persist(domainUser);
			m.getTransaction().commit();
		} catch (PersistenceException e){
			_logger.info("Duplicate user exists in database");
			return Response.status(400).build();
		} finally {
			m.close();
		}
		_logger.info("return location which has id appended for user address uri");
		return Response.created(URI.create("/user/" + domainUser.get_userId())).build();
	}
	
	/**
	 * Delete a user, implicitly deletes the reviews
	 * Needs to have a cookie, so that no random person not in the database can delete a user.
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Consumes({ "application/xml", "application/json" })
	public Response deleteUser(@PathParam("id") long id, @CookieParam("username") Cookie name){
		_logger.info("Check if have a cookie");
		if(name==null){
			return Response.status(401).build();
		}
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("check if user exists");
		User u = m.find(User.class, id);
		if(u==null){
		    return Response.status(404).build();
		}
		_logger.info(name.toString());
		if(u.getUserName().equals(name.getValue())){
			m.remove(u);
			m.getTransaction().commit();
			m.close();
			return Response.ok().build();
		} else {
			m.getTransaction().commit();
			m.close();
			_logger.info("if cookie username is not valid return unauthorised access");
			return Response.status(401).build();
		}
	}
	
	/**
	 * Add a book to a user. This means they purchased the book. Cannot be undone. Requires a cookie
	 */
	@PUT
	@Path("{id}/order/{bid}")
	@Consumes({ "application/xml", "application/json" })
	public Response addBookOrder(@PathParam("id") long id, @PathParam("bid") long bid, @CookieParam("username") Cookie name){
		_logger.info("Check if cookie exists");
		if(name==null){
			return Response.status(401).build();
		}
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("check if user exists based on id");
		User u = m.find(User.class, id);
		if(u==null){
			m.close();
			_logger.info("Entity not found");
		    throw new EntityNotFoundException();
		}
		if(u.getUserName().equals(name.getValue())){
			Book b = m.find(Book.class, bid);
			if(b==null){
				m.close();
				_logger.info("Entity not found");
			    throw new EntityNotFoundException();
			}
			u.addBook(b);
			m.getTransaction().commit();
			m.close();
			return Response.status(201).build();
		} else {
			m.getTransaction().commit();
			m.close();
			return Response.status(401).build();
		}
	}
	
	/**
	 * Add a book to a user based on isbn incase they do not have the id. Requires a cookie
	 */
	@PUT
	@Path("{id}/order/isbn/{isbn}")
	@Consumes({ "application/xml", "application/json" })
	public Response addBookOrderUsingISBN(@PathParam("id") long id, @PathParam("isbn") String isbn, @CookieParam("username") Cookie name){
		_logger.info("checking if cookie is given");
		if(name==null){
			return Response.status(401).build();
		}
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User u = m.find(User.class, id);
		if(u==null){
			m.close();
			_logger.info("Entity not found");
		    throw new EntityNotFoundException();
		}
		if(u.getUserName().equals(name.getValue())){
			_logger.info("get books where the book isbn is equal to the one given");
			Book b = m.createQuery("SELECT b FROM Book b WHERE b.isbn=:isbn", Book.class).setParameter("isbn", isbn).getSingleResult();
			if(b==null){
				m.close();
				_logger.info("Entity not found");
			    throw new EntityNotFoundException();
			}
			u.addBook(b);
			m.getTransaction().commit();
			m.close();
			return Response.status(201).build();
		} else {
			m.getTransaction().commit();
			m.close();
			return Response.status(401).build();
		}
	}
	
	/**
	 * Gets a list of all users in the database
	 * @return
	 */
	@GET
	@Produces({"application/xml","application/json"})
	public Response getUserList(){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("create a set of all users, empty initial");
		Set<UserDTO> userDTOset = new HashSet<UserDTO>();
		TypedQuery<User> userQuery = m.createQuery("FROM User", User.class);
		List<User> listUser = userQuery.getResultList();
		if(listUser==null||listUser.isEmpty()){
			m.close();
			_logger.info("Entity not found");
		    throw new EntityNotFoundException();
		}
		_logger.info("map users to dtousers");
		for(User u: listUser){
			userDTOset.add(DTOMapper.toUserDTO(u));
		}
		_logger.info("add to a generic entity and send across");
		GenericEntity<Set<UserDTO>> entity = new GenericEntity<Set<UserDTO>>(userDTOset){};
		m.getTransaction().commit();
		m.close();
		return Response.ok(entity).build();
	}
	
	/**
	 * Add a review for a book, needs a cookie as form of authenticating the user
	 * @param id
	 * @param bookId
	 * @return
	 */
	@PUT
	@Path("{id}/review")
	@Consumes({ "application/xml", "application/json" })
	public Response updateAddUserReview(@PathParam("id") long id, ReviewDTO rr, @CookieParam("username") Cookie name){
		_logger.info("check if cookie exists");
		if(name==null){
			return Response.status(401).build();
		}
		_logger.info("map the review from dtoreview");
		Review r = DTOMapper.toReviewDomain(rr);
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User user = m.find(User.class, id);
		if(user==null){
			return Response.status(404).build();
		}
		_logger.info(name.toString());
		if(user.getUserName().equals(name.getValue())){
			_logger.info("get a book based on isbn of the book provided");
			Book b = m.createQuery("SELECT b FROM Book b WHERE b.isbn=:isbn", Book.class).setParameter("isbn", r.getIsbn()).getSingleResult();
			if(b==null){
				return Response.status(404).build();
			}
			r.setBookReviewed(b);
			try{
				_logger.info("add review and finish, if conflict with existing review abort");
				user.addReview(r);
				m.getTransaction().commit();
			} catch (PersistenceException e){
				return Response.status(409).build();
			}
			m.close();
			return Response.status(201).build();
		} else {
			m.getTransaction().commit();
			m.close();
			return Response.status(401).build();
		}
	}
	
	/**
	 * Get reviews done by the user specified by the id
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}/review")
	@Produces({ "application/xml", "application/json" })
	public Response getReviewsByUser(@PathParam("id") long id){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("find user for looking for review");
		User user = m.find(User.class, id);
		if(user==null){
			return Response.status(404).build();
		}
		_logger.info("map reviews by user to dtousers");
		Set<ReviewDTO> review = new HashSet<ReviewDTO>();
		for(Review r: user.getReviews()){
			review.add(DTOMapper.toReviewDTO(r));
		}
		_logger.info("wrap to a generic entity for reviewdtos");
		GenericEntity<Set<ReviewDTO>> entity = new GenericEntity<Set<ReviewDTO>>(review){};
		m.getTransaction().commit();
		m.close();
		return Response.ok(entity).build();		
	}
	
	
	/**
	 * Get the books that have been purchased by this user, requires cookie
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}/book")
	@Produces({ "application/xml", "application/json" })
	public Response getBooksBoughtByUser(@PathParam("id") long id, @CookieParam("username") Cookie name){
		_logger.info("check if cookie exist");
		if(name==null){
			return Response.status(401).build();
		}
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User user = m.find(User.class, id);
		if(user==null){
		    throw new EntityNotFoundException();
		}
		if(user.getUserName().equals(name.getValue())){
			Set<BookDTO> bookSet = new HashSet<BookDTO>();
			for(Book b: user.getUsersBooks()){
				bookSet.add(DTOMapper.toBookDTO(b));
			}
			GenericEntity<Set<BookDTO>> entity = new GenericEntity<Set<BookDTO>>(bookSet){};
			m.getTransaction().commit();
			m.close();
			return Response.ok(entity).build();
		} else {
			m.getTransaction().commit();
			m.close();
			return Response.status(401).build();
		}
	}
	
	/**
	 * Get Users where id is start value and between end value.
	 * Using query params and HATEOAS gives the next link to the queries.
	 * @return
	 */
	@GET
	@Path("range")
	@Produces({ "application/xml", "application/json" })
	public Response getUsersFromRange(@DefaultValue("1") @QueryParam("start") long start, 
			@DefaultValue("1") @QueryParam("end")long end, @Context UriInfo uriInfo){
		
		URI uri = uriInfo.getAbsolutePath();
		Link next = null;
		
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Set<UserDTO> userDTOset = new HashSet<UserDTO>();
		_logger.info("look for ids in the range between start-end");
		TypedQuery<User> userQuery = m.createQuery("SELECT b FROM User b WHERE b.userId BETWEEN :start AND :end", User.class).setParameter("start", start).setParameter("end", end);
		List<User> listUser = userQuery.getResultList();
		if(listUser==null || listUser.isEmpty()){
			return Response.status(404).build();
		} else {
			_logger.info("Making NEXT link");
			next = Link.fromUri(uri + "?start={start}&end={end}")
					.rel("next")
					.build(end+1, end+(end-start)+1);
		}
		_logger.info("map users to dtousers");
		for(User b : listUser){
			userDTOset.add(DTOMapper.toUserDTO(b));
		}
		GenericEntity<Set<UserDTO>> entity = new GenericEntity<Set<UserDTO>>(userDTOset){};
		m.getTransaction().commit();
		m.close();
		_logger.info("if link is null as empty, dont add the link");
 		ResponseBuilder builder = Response.ok(entity);
 		if(next != null) {
 			builder.links(next);
 		}
 		Response response = builder.build();
		return response;
	}
	
	/**
	 * A login service that stores username as the key and name as the value.
	 * A user must have this cookie to be able to have access to any PUT DELETE api calls in user resource
	 * @param id
	 * @param name
	 * @return
	 */
	@GET
	@Path("{id}/loginservice")
	public Response userLoginService(@PathParam("id") long id,@QueryParam("name") String name){
		_logger.info("login for a user");
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		_logger.info("check if a user exists");
		User u = m.find(User.class, id);
		if(u==null){
			m.getTransaction().commit();
			m.close();
			return Response.status(404).build();
		}
		_logger.info("check if the name provided, is same as the name given");
		if(u.getUserName().equals(name)){
			_logger.info(name);
			NewCookie newCookie = new NewCookie("username", name);
			m.getTransaction().commit();
			m.close();
			_logger.info("return a cookie");
			return Response.noContent().cookie(newCookie).build();
		} else {
			m.getTransaction().commit();
			m.close();
			return Response.status(401).build();
		}
	}
	


	
}
