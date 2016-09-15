/**
 * 
 */
package library.content.dto;

import java.util.Iterator;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import library.content.purchase.Rating;

@XmlRootElement(name="Review")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReviewDTO {
	
	private Long reviewID;
	
	private UserDTO reviewFromUser;
	

	private String reviewComment;
	

	private Rating reviewRating;
	
	public ReviewDTO(){
		
	}
	
	public ReviewDTO(UserDTO u,String s, Rating r){
		this.reviewComment = s;
		this.reviewFromUser = u;
		this.reviewRating = r;
	}

	public UserDTO getReviewFromUser() {
		return reviewFromUser;
	}

	public void setReviewFromUser(UserDTO reviewFromUser) {
		this.reviewFromUser = reviewFromUser;
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

	public Long getUser_iD() {
		return reviewID;
	}

	public void setUser_iD(Long user_iD) {
		this.reviewID = user_iD;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(reviewID + " ");
		buffer.append(reviewRating+ " ");
		buffer.append(reviewComment+" ");
		buffer.append(reviewFromUser.toString());
		return buffer.toString();
	}
	
	@Override
	public int hashCode() {
		HashCodeBuilder b = new HashCodeBuilder(17, 31). 
	            append(reviewID).
	            append(reviewFromUser.toString()).
	            append(reviewComment).
	            append(reviewRating);

		return b.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ReviewDTO))
			return false;
		if (obj == this)
			return true;

		ReviewDTO rhs = (ReviewDTO) obj;
		EqualsBuilder b = new EqualsBuilder().append(reviewID, rhs.reviewID).append(reviewFromUser, rhs.reviewFromUser)
				.append(reviewComment, rhs.reviewComment).append(reviewRating, rhs.reviewRating);
		return b.isEquals();
	}
	
}
