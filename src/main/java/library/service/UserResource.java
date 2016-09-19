/**
 * 
 */
package library.service;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import library.content.dto.BookDTO;
import library.content.dto.DTOMapper;
import library.content.dto.UserDTO;
import library.content.purchase.Book;
import library.content.purchase.Review;
import library.content.purchase.User;

/**
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
	public UserDTO getUser(@PathParam("id") long id){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User b = m.find(User.class, id);
		UserDTO b1 = DTOMapper.toUserDTO(b);
		m.getTransaction().commit();
		m.close();
		if(b==null){
		    throw new WebApplicationException(
		    	      Response.status(Status.NOT_FOUND)
		    	        .build());
		}
		return b1;
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
		User u = m.createQuery("SELECT u FROM User u WHERE u.email=:email", User.class).setParameter("email", email).getSingleResult();
		UserDTO udto = DTOMapper.toUserDTO(u);
		m.getTransaction().commit();
		m.close();
		if (u == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
		}
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
		} catch (Exception e){

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
	public Response deleteUser(@PathParam("id") long id){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User u = m.find(User.class, id);
		m.remove(u);
		m.getTransaction().commit();
		m.close();
		return Response.ok().build();
	}
	
	/**
	 * add a book to a user
	 */
	@PUT
	@Path("{id}/order/{bid}")
	@Consumes({ "application/xml", "application/json" })
	public Response addBookOrder(@PathParam("id") long id, @PathParam("bid") long bid){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User u = m.find(User.class, id);
		Book b = m.find(Book.class, bid);
		u.addBook(b);
		m.getTransaction().commit();
		m.close();
		return Response.ok().build();
	}
	
	/**
	 * add a book to a user based on isbn
	 */
	@PUT
	@Path("{id}/order/isbn/{isbn}")
	@Consumes({ "application/xml", "application/json" })
	public Response addBookOrderUsingISBN(@PathParam("id") long id, @PathParam("isbn") String isbn){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Book b = m.createQuery("SELECT b FROM Book b WHERE b.isbn=:isbn", Book.class).setParameter("isbn", isbn).getSingleResult();
		User u = m.find(User.class, id);
		u.addBook(b);
		m.getTransaction().commit();
		m.close();
		return Response.status(204).build();
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
	@Path("{id}/review/add")
	@Consumes({ "application/xml", "application/json" })
	public Response updateAddUserReview(@PathParam("id") long id, Review r){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User user = m.find(User.class, id);
		Book b = m.createQuery("SELECT b FROM Book b WHERE b.isbn=:isbn", Book.class).setParameter("isbn", r.getIsbn()).getSingleResult();
		if(b==null){
			return Response.status(400).build();
		}
		r.setBookReviewed(b);
		try{
			user.addReview(r);
			m.getTransaction().commit();
		} catch (PersistenceException e){
			return Response.status(400).build();
		}
		m.close();
		return Response.status(204).build();
	}
	
	@GET
	@Path("{id}/books")
	@Produces({ "application/xml", "application/json" })
	public Response getBooksBoughtByUser(@PathParam("id") long id){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User user = m.find(User.class, id);
		Set<BookDTO> bookSet = new HashSet<BookDTO>();
		for(Book b: user.getUsersBooks()){
			bookSet.add(DTOMapper.toBookDTO(b));
		}
		GenericEntity<Set<BookDTO>> entity = new GenericEntity<Set<BookDTO>>(bookSet){};
		m.getTransaction().commit();
		m.close();
		return Response.ok(entity).build();
	}
	
	
}
