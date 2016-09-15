/**
 * 
 */
package library.content.dto;

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

import library.content.purchase.Rating;

@XmlRootElement(name="Review")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReviewDTO {
	
	private Long user_iD;
	
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
		return user_iD;
	}

	public void setUser_iD(Long user_iD) {
		this.user_iD = user_iD;
	}

	
}
