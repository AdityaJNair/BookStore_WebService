/**
 * 
 */
package library.content.purchase;

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

import library.content.purchase.enums.Rating;

@Embeddable
@XmlRootElement(name="Review")
@XmlAccessorType(XmlAccessType.FIELD)
public class Review {
	

	//@JoinColumn(name="USER_ID", nullable=false)
	@ManyToOne(fetch=FetchType.LAZY)
	private Book bookReviewed;
	
	@Column(name="REVIEW_COMMENT", nullable=false)
	private String reviewComment;
	
	@Enumerated(EnumType.STRING)
	@Column(name="REVIEW_RATING", nullable=false)
	private Rating reviewRating;
	
	@Column(nullable=false, name="REVIEW_ISBN", unique=true)
	private String isbn;
	
	public Review(){
		
	}
	
	public Review(String s, Rating r,String bookname){
		this.reviewComment = s;
		this.reviewRating = r;
		this.isbn=bookname;
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
		buffer.append(bookReviewed.get_bookId());
		return buffer.toString();
	}
/*	//FIX THIS
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Review))
            return false;
        if (obj == this)
            return true;

        Review other = (Review)obj;
        return bookReviewed.equals(other.getBookReviewed()) && reviewComment.equals(other.reviewComment) && reviewRating.equals(other.reviewRating);
	}
	//FIX THIS
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            toHashCode();
	}*/

	
}
