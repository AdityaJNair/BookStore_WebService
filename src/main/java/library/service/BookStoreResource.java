package library.service;

import java.net.HttpURLConnection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import library.content.printcontent.ContentPrintType;
import library.content.printcontent.Orders;

@Path("/service")
public class BookStoreResource {
	private static final Logger _logger = LoggerFactory.getLogger(BookStoreResource.class);

	private Map<Long, Orders> _orderDataBase;
	private Map<Long, ContentPrintType> _printDataBase;
	
	public BookStoreResource(){
		reloadDatabase();
	}

	//get book
	/**
	 * 
	 */
	@GET
	@Path("library/{bookname}")
	@Produces("application/xml")
	public ContentPrintType getBook(@PathParam("bookname") String bookName){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("bookstorePU");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		try{
			TypedQuery<ContentPrintType> query = em.createQuery("select b, from ContentPrintType b"+
						"where b.Book_Title = "+bookName, ContentPrintType.class);
			ContentPrintType book = query.getSingleResult();
			return book;
		}catch (NonUniqueResultException e1){
		    throw new WebApplicationException(
		    	      Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
		    	        .entity("fail")
		    	        .build()
		    	    );
		}catch (NoResultException e){
		    throw new WebApplicationException(
		    	      Response.status(HttpURLConnection.HTTP_NOT_FOUND)
		    	        .entity("book not found")
		    	        .build()
		    	    );
		}
	}
	
	//get order
	/**
	 * 
	 */
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public void getOrder(){
		
	}


	//remove order
	/**
	 * 
	 */
	@DELETE
	@Path("{id}")
	public void removeOrder(){
		
	}
	
	//update order
	/**
	 * 
	 */
	@PUT
	@Path("{id}/orders")
	public void updateOrder(){
		
	}
	/**
	 * 
	 */
	//add order
	@POST
	@Path("{id}/orders")
	@Produces("application/xml")
	public void addOrder(){
		
	}
	
	
	
	
	protected void reloadDatabase(){
		_orderDataBase = new ConcurrentHashMap<Long, Orders>();
		_printDataBase = new ConcurrentHashMap<Long, ContentPrintType>();
		
		//Adding publishers
		
		//Adding authors
		
		//Adding books
		
		//adding bundles
				
		//Adding users
		
		//Adding orders
		
	}
	
}
