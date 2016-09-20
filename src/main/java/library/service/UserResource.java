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

import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.dto.UserDTO;
import library.content.purchase.Book;
import library.content.purchase.Review;
import library.content.purchase.User;

/**
 * Resource class for Users. Able to create users, delete users, read users and put book orders into a user class
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
		User b = m.find(User.class, id);
		if(b==null){
		    throw new EntityNotFoundException();
		}
		UserDTO b1 = DTOMapper.toUserDTO(b);
		m.getTransaction().commit();
		m.close();
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
			u = m.createQuery("SELECT u FROM User u WHERE u.email=:email", User.class).setParameter("email", email).getSingleResult();

		}catch(PersistenceException e){
			throw new EntityNotFoundException();
		}
		if(u==null){
		    throw new EntityNotFoundException();
		}
		UserDTO udto = DTOMapper.toUserDTO(u);
		m.getTransaction().commit();
		m.close();
		return udto;
	}
	
	
	/**
	 * Add a user
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
			m.persist(domainUser);
			m.getTransaction().commit();
		} catch (PersistenceException e){
			return Response.status(400).build();
		} finally {
			m.close();
		}
		return Response.created(URI.create("/user/" + domainUser.get_userId())).build();
	}
	
	/**
	 * Delete a user
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	@Consumes({ "application/xml", "application/json" })
	public Response deleteUser(@PathParam("id") long id, @CookieParam("username") Cookie name){
		if(name==null){
			return Response.status(401).build();
		}
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
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
			return Response.status(401).build();
		}
	}
	
	/**
	 * add a book to a user
	 */
	@PUT
	@Path("{id}/order/{bid}")
	@Consumes({ "application/xml", "application/json" })
	public Response addBookOrder(@PathParam("id") long id, @PathParam("bid") long bid, @CookieParam("username") Cookie name){
		if(name==null){
			return Response.status(401).build();
		}
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User u = m.find(User.class, id);
		if(u==null){
		    throw new EntityNotFoundException();
		}
		if(u.getUserName().equals(name.getValue())){
			Book b = m.find(Book.class, bid);
			if(b==null){
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
	 * add a book to a user based on isbn
	 */
	@PUT
	@Path("{id}/order/isbn/{isbn}")
	@Consumes({ "application/xml", "application/json" })
	public Response addBookOrderUsingISBN(@PathParam("id") long id, @PathParam("isbn") String isbn, @CookieParam("username") Cookie name){
		if(name==null){
			return Response.status(401).build();
		}
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User u = m.find(User.class, id);
		if(u==null){
		    throw new EntityNotFoundException();
		}
		if(u.getUserName().equals(name.getValue())){
			Book b = m.createQuery("SELECT b FROM Book b WHERE b.isbn=:isbn", Book.class).setParameter("isbn", isbn).getSingleResult();
			if(b==null){
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
	 * Gets a list of all users
	 * @return
	 */
	@GET
	@Produces({"application/xml","application/json"})
	public Response getUserList(){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Set<UserDTO> userDTOset = new HashSet<UserDTO>();
		TypedQuery<User> userQuery = m.createQuery("FROM User", User.class);
		List<User> listUser = userQuery.getResultList();
		if(listUser==null||listUser.isEmpty()){
		    throw new EntityNotFoundException();
		}
		for(User u: listUser){
			userDTOset.add(DTOMapper.toUserDTO(u));
		}
		GenericEntity<Set<UserDTO>> entity = new GenericEntity<Set<UserDTO>>(userDTOset){};
		m.getTransaction().commit();
		m.close();
		return Response.ok(entity).build();
	}
	
	/**
	 * Add a review for a book
	 * @param id
	 * @param bookId
	 * @return
	 */
	@PUT
	@Path("{id}/review")
	@Consumes({ "application/xml", "application/json" })
	public Response updateAddUserReview(@PathParam("id") long id, Review r, @CookieParam("username") Cookie name){
		if(name==null){
			return Response.status(401).build();
		}
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User user = m.find(User.class, id);
		if(user==null){
			return Response.status(404).build();
		}
		_logger.info(name.toString());
		if(user.getUserName().equals(name.getValue())){
			Book b = m.createQuery("SELECT b FROM Book b WHERE b.isbn=:isbn", Book.class).setParameter("isbn", r.getIsbn()).getSingleResult();
			if(b==null){
				return Response.status(404).build();
			}
			r.setBookReviewed(b);
			try{
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
	 * Get the books that have been purchased by this user
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}/book")
	@Produces({ "application/xml", "application/json" })
	public Response getBooksBoughtByUser(@PathParam("id") long id, @CookieParam("username") Cookie name){
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
		for(User b : listUser){
			userDTOset.add(DTOMapper.toUserDTO(b));
		}
		GenericEntity<Set<UserDTO>> entity = new GenericEntity<Set<UserDTO>>(userDTOset){};
		m.getTransaction().commit();
		m.close();
		
 		ResponseBuilder builder = Response.ok(entity);
 		if(next != null) {
 			builder.links(next);
 		}
 		Response response = builder.build();
		return response;
	}
	
	@GET
	@Path("{id}/loginservice")
	public Response userLoginService(@PathParam("id") long id,@QueryParam("name") String name){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User u = m.find(User.class, id);
		if(u==null){
			m.getTransaction().commit();
			m.close();
			return Response.status(404).build();
		}
		if(u.getUserName().equals(name)){
			_logger.info(name);
			NewCookie newCookie = new NewCookie("username", name);
			m.getTransaction().commit();
			m.close();
			return Response.noContent().cookie(newCookie).build();
		} else {
			m.getTransaction().commit();
			m.close();
			return Response.status(401).build();
		}
	}
	


	
}
