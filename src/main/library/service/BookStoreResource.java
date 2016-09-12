package main.library.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.library.content.printcontent.ContentPrintType;
import main.library.content.printcontent.Orders;

@Path("/users")
public class BookStoreResource {
	private static final Logger _logger = LoggerFactory.getLogger(BookStoreResource.class);

	private Map<Long, Orders> _orderDataBase;
	private Map<Long, ContentPrintType> _printDataBase;
	
	public BookStoreResource(){
		reloadDatabase();
	}
	
	//add order
	
	
	//get book
	/**
	 * 
	 */
	@GET
	@Produces("application/xml")
	public void getBook(){
		
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
	public void removeOrder(){
		
	}
	
	//update order
	/**
	 * 
	 */
	public void updateOrder(){
		
	}
	/**
	 * 
	 */
	//add order
	@POST
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
