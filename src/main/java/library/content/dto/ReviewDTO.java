/**
 * 
 */
package library.content.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import library.content.domain.Review;
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
	
	public ReviewDTO(){
		
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
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(reviewRating+ " ");
		buffer.append(reviewComment+" ");
		buffer.append(bookReviewed.get_bookId()+" ");
		buffer.append(isbn);
		return buffer.toString();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Review))
            return false;
        if (obj == this)
            return true;

        ReviewDTO other = (ReviewDTO)obj;
        return bookReviewed.equals(other.getBookReviewed()) && reviewComment.equals(other.reviewComment) && reviewRating.equals(other.reviewRating) && isbn.equals(other.isbn);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).
				append(bookReviewed).append(reviewComment).append(reviewRating).append(isbn).
	            toHashCode();
	}
}
