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
	
	public Review(){
		
	}
	
	public Review(String s, Rating r){
		this.reviewComment = s;
		this.reviewRating = r;
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


	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(reviewRating+ " ");
		buffer.append(reviewComment+" ");
		return buffer.toString();
	}
	//FIX THIS
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Review))
            return false;
        if (obj == this)
            return true;

        Review other = (Review)obj;
        return false;
	}
	//FIX THIS
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            toHashCode();
	}

	
}
