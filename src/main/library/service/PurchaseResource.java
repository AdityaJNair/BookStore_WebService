package main.library.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.library.content.printcontent.ContentPrintType;
import main.library.content.printcontent.EducationalBook;
import main.library.content.purchase.Orders;
import main.library.content.visualcontent.ContentFilmType;

public class PurchaseResource {
	private static final Logger _logger = LoggerFactory.getLogger(PurchaseResource.class);

	private Map<Long, Orders> _orderDataBase;
	private Map<Long, ContentPrintType> _printDataBase;
	private Map<Long, ContentFilmType> _visualDataBase;
	
	public PurchaseResource(){
		reloadDatabase();
	}
	
	
	protected void reloadDatabase(){
		_orderDataBase = new ConcurrentHashMap<Long, Orders>();
		_printDataBase = new ConcurrentHashMap<Long, ContentPrintType>();
		_visualDataBase = new ConcurrentHashMap<Long, ContentFilmType>();
		
		//Adding books
		
		//Adding films
		
		//Adding authors
		
		//Adding directors
		
		//Adding orders
		
		
	}
	
}
