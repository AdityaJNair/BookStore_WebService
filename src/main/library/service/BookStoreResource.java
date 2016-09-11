package main.library.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.library.content.printcontent.ContentPrintType;
import main.library.content.printcontent.books.EducationalBook;
import main.library.content.purchase.Orders;

public class BookStoreResource {
	private static final Logger _logger = LoggerFactory.getLogger(BookStoreResource.class);

	private Map<Long, Orders> _orderDataBase;
	private Map<Long, ContentPrintType> _printDataBase;
	
	public BookStoreResource(){
		reloadDatabase();
	}
	
	//add order
	//get book
	//get film
	//get order
	//remove order
	//update order

	
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
