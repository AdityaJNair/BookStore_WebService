/**
 * 
 */
package library.service;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import library.content.dto.DTOMapper;
import library.content.dto.OrdersDTO;
import library.content.dto.UserDTO;
import library.content.purchase.Address;
import library.content.purchase.Author;
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
		try{
			User b = m.find(User.class, id);
			m.remove(b);
		} catch (IllegalArgumentException e){
			m.getTransaction().commit();
			m.close();
			return Response.status(404).build();
		} catch (TransactionRequiredException e2){
			m.getTransaction().commit();
			m.close();
			return Response.status(404).build();
		}
		m.getTransaction().commit();
		m.close();
		return Response.status(204).build();
	}
	
	/**
	 * Update address
	 * @param id
	 * @param userAddress
	 * @return
	 */
	@PUT
	@Path("{id}/address")
	@Consumes({ "application/xml", "application/json" })
	public Response updateUserAddress(@PathParam("id") long id, Address userAddress){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User user = m.find(User.class, id);
		user.set_address(userAddress);
		m.getTransaction().commit();
		m.close();
		return Response.status(204).build();
	}
	/**
	 * Add a review for a book
	 * @param id
	 * @param bookId
	 * @return
	 */
	@PUT
	@Path("{id}/review/{bid}")
	@Consumes({ "application/xml", "application/json" })
	public Response updateAddUserReview(@PathParam("id") long id, Review r){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		User user = m.find(User.class, id);
		user.addReview(r);
		m.getTransaction().commit();
		m.close();
		return Response.status(204).build();
	}
	
	/**
	 * Add an order from a specific user
	 * @param userid
	 * @param userAddress
	 * @return
	 */
	@PUT
	@Path("{id}/order")
	@Consumes({ "application/xml", "application/json" })
	public Response updateAddUserOrder(@PathParam("id") long userid, OrdersDTO userAddress){
		
		return null;
	}
	
	/**
	 * Gets a list of all users
	 * @return
	 */
	@GET
	@Produces({"application/xml","application/json"})
	public Set<UserDTO> getUserList(){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		Set<UserDTO> userDTOset = new HashSet<UserDTO>();
		TypedQuery<User> userQuery = m.createQuery("FROM User", User.class);
		List<User> listUser = userQuery.getResultList();
		for(User u: listUser){
			userDTOset.add(DTOMapper.toUserDTO(u));
		}
		m.getTransaction().commit();
		m.close();
		return userDTOset;
	}
	
	
}
