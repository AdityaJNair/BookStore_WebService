/**
 * 
 */
package library.content.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import library.content.domain.enums.Rating;
/**
 * Review class that is an embeddable - A user can make a review, and this class links to Books entity class. Users can make reviews to 
 * books and then give them a comment and rating
 * @author Aditya
 *
 */
@Embeddable
public class Review {
	
	//book reviewed
	@ManyToOne(fetch=FetchType.LAZY)
	private Book bookReviewed;
	
	//comment for the review
	@Column(name="REVIEW_COMMENT", nullable=false)
	private String reviewComment;
	
	//review rating
	@Enumerated(EnumType.STRING)
	@Column(name="REVIEW_RATING", nullable=false)
	private Rating reviewRating;
	
	//unique isbn representation of the book reviewed
	@Column(nullable=false, name="REVIEW_ISBN", unique=true)
	private String isbn;
	
	public Review(){
		
	}
	
	public Review(String s, Rating r,String isbn){
		this.reviewComment = s;
		this.reviewRating = r;
		this.isbn=isbn;
	}
	
	
	
	public Book getBookReviewed() {
		return bookReviewed;
	}

	public void setBookReviewed(Book bookReviewed) {
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

        Review other = (Review)obj;
        return bookReviewed.equals(other.getBookReviewed()) && reviewComment.equals(other.reviewComment) && reviewRating.equals(other.reviewRating) && isbn.equals(other.isbn);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).
				append(bookReviewed).append(reviewComment).append(reviewRating).append(isbn).
	            toHashCode();
	}

	
}
