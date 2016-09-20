package library.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import library.content.purchase.Address;
import library.content.purchase.Author;
import library.content.purchase.Book;
import library.content.purchase.Publisher;
import library.content.purchase.User;
import library.content.purchase.enums.BookGenre;
import library.content.purchase.enums.PrintType;

@ApplicationPath("/services")
public class BookStoreApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	public BookStoreApplication() {
		singletons.add(new BookResource());
		singletons.add(new AuthorResource());
		singletons.add(new UserResource());
		singletons.add(new WebExceptionMapper());
		singletons.add(PersistenceManager.instance());
		
		databaseInit();
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	
	static void databaseInit(){
		EntityManager m = PersistenceManager.instance().createEntityManager();
		m.getTransaction().begin();
		
		Date date = new GregorianCalendar(1964, Calendar.JUNE, 22).getTime();
		Date date1 = new GregorianCalendar(1899, Calendar.JULY, 21).getTime();
		Date date2 = new GregorianCalendar(1965, Calendar.JULY, 31).getTime();

		Date date4 = new GregorianCalendar(1947, Calendar.SEPTEMBER, 21).getTime();
		
		// 3 different authors for each book authors
		Author author1 = new Author("Dan Brown", date, BookGenre.Mystery, "Dan has worked extremly...");
		m.persist(author1);
		Author author2 = new Author("J.K Rowling", date2, BookGenre.Fantasy, "One of the greats...");
		m.persist(author2);
		Author author3 = new Author("Stephen King", date4, BookGenre.Horror, "RUN!!....");
		m.persist(author3);
		

		// address for users and publishers
		Address addressPublisher1 = new Address("152", "Dove Street", "Darby", "London", "England", "156-198-19");
		Address addressPublisher2 = new Address("2", "McNaughton Street", "Onehunga", "Auckland", "New Zealand", "1432");
		
		Publisher publisher1 = new Publisher(addressPublisher1, "Thompsons publishing services");
		Publisher publisher2 = new Publisher(addressPublisher2, "Brandons publishing services");
		
		// books
		Book book1 = new Book("Inferno", date, author1, "Book description", new BigDecimal("12.50"), PrintType.Other, publisher1,
				BookGenre.Novel, "ISBN-13:152-1-56619-909-4", "Russian");
		m.persist(book1);

		Book book2 = new Book("Harry Potter", date1, author2, "Book description", new BigDecimal("52.60"), PrintType.HardCover, publisher2,
				BookGenre.Speech, "ISBN-13:673-1-56619-909-4", "Maori");
		m.persist(book2);

		Book book3 = new Book("The Shinning", date2, author3, "Book description", new BigDecimal("23.00"), PrintType.E_Book, publisher1,
				BookGenre.NonFiction, "ISBN-13:234-1-56619-909-4", "Dutch");
		m.persist(book3);
		
		//USER BIRTH DATES
		Date dateUser1 = new GregorianCalendar(2000, Calendar.MARCH, 31).getTime();
		Date dateUser2 = new GregorianCalendar(1900, Calendar.SEPTEMBER, 21).getTime();
		
		//USER ADDRESSES
		Address addressUser = new Address("21", "Parliment House Street", "Wellington", "Auckland", "New Zealand", "2502");
		Address addressUser2 = new Address("23", "Parliment House Street", "Wellington", "Auckland", "New Zealand", "2502");
		
		//USERS
		User user1 = new User(addressUser, "Adi", dateUser1,"testingADI@gmail.com");
		m.persist(user1);
		User user2 = new User(addressUser2, "Tim", dateUser2,"testingTIM@yahoo.com");
		m.persist(user2);

		m.getTransaction().commit();
		m.close();
		
	}
}
