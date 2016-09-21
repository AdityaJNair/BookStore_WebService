/**
 * 
 */
package library.content.dto;

import library.content.domain.Author;
import library.content.domain.Book;
import library.content.domain.Review;
import library.content.domain.User;

/**
 * Mapper class for converting domain objects to dto objects
 * @author adijn
 *
 */
public class DTOMapper {

	/**
	 * Converts domain books to dto books
	 * @param domainBook
	 * @return
	 */
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
	
	/**
	 * Converts a dto book to a domain book
	 * @param bookDTO
	 * @return
	 */
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
	
	/**
	 * Converts a domain user to a dto user
	 * @param domainUser
	 * @return
	 */
	public static UserDTO toUserDTO(User domainUser){
		UserDTO userDTO = new UserDTO(domainUser.get_address(),domainUser.getUserName(), domainUser.getUserAge(), domainUser.getEmail());
		userDTO.set_userId(domainUser.get_userId());
		return userDTO;
	}
	
	/**
	 * converts a dto user to a domain user
	 * @param dtoUser
	 * @return
	 */
	public static User toUserDomain(UserDTO dtoUser){
		User domainUser = new User(dtoUser.get_address(),dtoUser.getUserName(),dtoUser.getUserBirth(), dtoUser.getEmail());
		return domainUser;
	}
		
	/**
	 * converts a domain author to a dto author
	 * @param domainAuthor
	 * @return
	 */
	public static AuthorDTO toAuthorDTO(Author domainAuthor){
		AuthorDTO authorDTO = new AuthorDTO(domainAuthor.get_name(), domainAuthor.get_age(), domainAuthor.get_mostKnownForGenre(), domainAuthor.get_description());
		authorDTO.set_authorId(domainAuthor.get_authorId());
		return authorDTO;
	}
	
	/**
	 * converts a dto author to a domain author
	 * @param dtoAuthor
	 * @return
	 */
	public static Author toAuthorDomain(AuthorDTO dtoAuthor){
		Author authorDomain = new Author(dtoAuthor.get_name(), dtoAuthor.get_age(), dtoAuthor.get_mostKnownForGenre(), dtoAuthor.get_description());
		return authorDomain;
	}
	
	/**
	 * Converts a dto review to a domain review
	 * @param dtoReview
	 * @return
	 */
	public static Review toReviewDomain(ReviewDTO dtoReview){
		Review reviewDomain = new Review(dtoReview.getReviewComment(), dtoReview.getReviewRating(), dtoReview.getIsbn());
		reviewDomain.setBookReviewed(DTOMapper.toBookDomain(dtoReview.getBookReviewed()));
		return reviewDomain;
	}
	
	/**
	 * Converts a domain review to a dto review
	 * @param domainReview
	 * @return
	 */
	public static ReviewDTO toReviewDTO(Review domainReview){
		ReviewDTO dtoReview = new ReviewDTO(domainReview.getReviewComment(), domainReview.getReviewRating(), domainReview.getIsbn());
		dtoReview.setBookReviewed(DTOMapper.toBookDTO(domainReview.getBookReviewed()));
		return dtoReview;
	}
	
	

}
