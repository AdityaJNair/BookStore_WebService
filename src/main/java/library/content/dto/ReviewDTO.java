/**
 * 
 */
package library.content.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import library.content.domain.enums.Rating;

/**
 * @author Aditya
 *
 */
@XmlRootElement(name="Review")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReviewDTO {

	private BookDTO bookReviewed;
	
	private String reviewComment;
	
	private Rating reviewRating;
	
	private String isbn;
	
	public ReviewDTO(String s, Rating r,String isbn){
		this.reviewComment = s;
		this.reviewRating = r;
		this.isbn=isbn;
	}

	public BookDTO getBookReviewed() {
		return bookReviewed;
	}

	public void setBookReviewed(BookDTO bookReviewed) {
		this.bookReviewed = bookReviewed;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public Rating getReviewRating() {
		return reviewRating;
	}

	public void setReviewRating(Rating reviewRating) {
		this.reviewRating = reviewRating;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	
}
