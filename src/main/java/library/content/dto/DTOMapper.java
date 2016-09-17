/**
 * 
 */
package library.content.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import library.content.purchase.Author;
import library.content.purchase.Book;
import library.content.purchase.Orders;
import library.content.purchase.Review;
import library.content.purchase.User;

/**
 * @author adijn
 *
 */
public class DTOMapper {

	public static BookDTO toBookDTO(Book domainBook){
		BookDTO bookDTO = new BookDTO(
				domainBook.get_title(),  
				domainBook.getDatePublished(), 
				toAuthorDTO(domainBook.get_author()), 
				domainBook.get_description(), 
				domainBook.get_cost(), 
				domainBook.get_printType(), 
				domainBook.get_publisher(), 
				domainBook.get_genre(), 
				domainBook.getIsbn(), 
				domainBook.getLanguage());
		bookDTO.set_bookId(domainBook.get_bookId());

		return bookDTO;
	}
	public static Book toBookDomain(BookDTO bookDTO){
		Book domainBook = new Book(
				bookDTO.get_title(), 
				bookDTO.getDatePublished(), 
				toAuthorDomain(bookDTO.get_author()),
				bookDTO.get_description(), 
				bookDTO.get_cost(), 
				bookDTO.get_printType(), 
				bookDTO.get_publisher(), 
				bookDTO.get_genre(), 
				bookDTO.getIsbn(), 
				bookDTO.getLanguage());
		return domainBook;
	}
	
	public static OrdersDTO toOrdersDTO(Orders domainOrders){
		OrdersDTO ordersDTO = new OrdersDTO(toUserDTO(domainOrders.get_user()));
		ordersDTO.set_id(domainOrders.get_id());
		
		Set<BookDTO> bookDTOSet = new HashSet<BookDTO>();
		for(Book r : domainOrders.getBooks()){
			bookDTOSet.add(toBookDTO(r));
		}
		ordersDTO.setBooks(bookDTOSet);
		
		return ordersDTO;
	}
	public static Orders toOrdersDomain(OrdersDTO dtoOrders){
		Orders domainOrders = new Orders(toUserDomain(dtoOrders.get_user()));
		
		Set<Book> bookSet = new HashSet<Book>();
		for(BookDTO r : dtoOrders.getBooks()){
			bookSet.add(toBookDomain(r));
		}
		domainOrders.setBooks(bookSet);
		return domainOrders;
	}
	
	
	public static UserDTO toUserDTO(User domainUser){
		UserDTO userDTO = new UserDTO(domainUser.get_address(),domainUser.getUserName(), domainUser.getUserAge(), domainUser.getEmail());
		userDTO.set_userId(domainUser.get_userId());
		return userDTO;
	}
	public static User toUserDomain(UserDTO dtoUser){
		User domainUser = new User(dtoUser.get_address(),dtoUser.getUserName(),dtoUser.getUserBirth(), dtoUser.getEmail());
		return domainUser;
	}
		
	
	public static AuthorDTO toAuthorDTO(Author domainAuthor){
		AuthorDTO authorDTO = new AuthorDTO(domainAuthor.get_name(), domainAuthor.get_age(), domainAuthor.get_mostKnownForGenre(), domainAuthor.get_description());
		authorDTO.set_authorId(domainAuthor.get_authorId());
		return authorDTO;
	}
	
	public static Author toAuthorDomain(AuthorDTO dtoAuthor){
		Author authorDomain = new Author(dtoAuthor.get_name(), dtoAuthor.get_age(), dtoAuthor.get_mostKnownForGenre(), dtoAuthor.get_description());
		return authorDomain;
	}
	

}
