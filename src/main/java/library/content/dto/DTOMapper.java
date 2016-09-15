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
				domainBook.get_description(), 
				domainBook.get_cost(), 
				domainBook.get_printType(), 
				domainBook.get_publisher(), 
				domainBook.get_genre(), 
				domainBook.getIsbn(), 
				domainBook.getLanguage());
		bookDTO.set_bookId(domainBook.get_bookId());
		
		Set<AuthorDTO> authorDTOSet = new HashSet<AuthorDTO>();
		for(Author r : domainBook.get_author()){
			authorDTOSet.add(toAuthorDTO(r));
		}
		bookDTO.set_author(authorDTOSet);
		
		Set<ReviewDTO> reviewDTOSet = new HashSet<ReviewDTO>();
		for(Review r : domainBook.getBookReviews()){
			reviewDTOSet.add(toReviewDTO(r));
		}
		bookDTO.setBookReviews(reviewDTOSet);
		return bookDTO;
	}
	public static Book toBookDomain(BookDTO bookDTO){
		Book domainBook = new Book(
				bookDTO.get_title(), 
				bookDTO.getDatePublished(), 
				bookDTO.get_description(), 
				bookDTO.get_cost(), 
				bookDTO.get_printType(), 
				bookDTO.get_publisher(), 
				bookDTO.get_genre(), 
				bookDTO.getIsbn(), 
				bookDTO.getLanguage());
		Set<Review> reviewSet = new HashSet<Review>();
		for(ReviewDTO r : bookDTO.getBookReviews()){
			reviewSet.add(toReviewDomain(r));
		}
		domainBook.setBookReviews(reviewSet);

		
		Set<Author> authorSet = new HashSet<Author>();
		for(AuthorDTO r : bookDTO.get_author()){
			authorSet.add(toAuthorDomain(r));
		}
		domainBook.set_author(authorSet);
		return domainBook;
	}
	
	public static OrdersDTO toOrdersDTO(Orders domainOrders){
		OrdersDTO ordersDTO = new OrdersDTO(toUserDTO(domainOrders.get_user()));
		ordersDTO.set_id(domainOrders.get_id());
		
		Collection<BookDTO> bookDTOSet = new ArrayList<BookDTO>();
		for(Book r : domainOrders.getBooks()){
			bookDTOSet.add(toBookDTO(r));
		}
		ordersDTO.setBooks(bookDTOSet);
		
		return ordersDTO;
	}
	public static Orders toOrdersDomain(OrdersDTO dtoOrders){
		Orders domainOrders = new Orders(toUserDomain(dtoOrders.get_user()));
		
		Collection<Book> bookSet = new ArrayList<Book>();
		for(BookDTO r : dtoOrders.getBooks()){
			bookSet.add(toBookDomain(r));
		}
		domainOrders.setBooks(bookSet);
		return domainOrders;
	}
	
	public static ReviewDTO toReviewDTO(Review domainReview){
		ReviewDTO reviewDTO = new ReviewDTO(toUserDTO(domainReview.getReviewFromUser()), domainReview.getReviewComment(), domainReview.getReviewRating());
		reviewDTO.setUser_iD(domainReview.getUser_iD());
		return reviewDTO;
	}
	public static Review toReviewDomain(ReviewDTO dtoReview){
		Review domainReview = new Review(toUserDomain(dtoReview.getReviewFromUser()), dtoReview.getReviewComment(), dtoReview.getReviewRating());
		return domainReview;
	}
	
	public static UserDTO toUserDTO(User domainUser){
		UserDTO userDTO = new UserDTO(domainUser.get_address(),domainUser.getUserName());
		return userDTO;
	}
	public static User toUserDomain(UserDTO dtoUser){
		User domainUser = new User(dtoUser.get_address(),dtoUser.getUserName());
		return domainUser;
	}
		
	
	public static AuthorDTO toAuthorDTO(Author domainAuthor){
		AuthorDTO authorDTO = new AuthorDTO(domainAuthor.get_name(), domainAuthor.get_age(), domainAuthor.get_mostKnownForGenre(), domainAuthor.get_description());
		authorDTO.set_authorId(domainAuthor.get_authorId());
		Set<ReviewDTO> reviewDTOSet = new HashSet<ReviewDTO>();
		for(Review r : domainAuthor.getAuthorReviews()){
			reviewDTOSet.add(toReviewDTO(r));
		}
		authorDTO.setAuthorReviews(reviewDTOSet);
		return authorDTO;
	}
	
	public static Author toAuthorDomain(AuthorDTO dtoAuthor){
		Author authorDomain = new Author(dtoAuthor.get_name(), dtoAuthor.get_age(), dtoAuthor.get_mostKnownForGenre(), dtoAuthor.get_description());
		Set<Review> reviewDomain = new HashSet<Review>();
		for(ReviewDTO r : dtoAuthor.getAuthorReviews()){
			reviewDomain.add(toReviewDomain(r));
		}
		authorDomain.setAuthorReviews(reviewDomain);
		return authorDomain;
	}
	

}
