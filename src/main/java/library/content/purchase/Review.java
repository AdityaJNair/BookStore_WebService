/**
 * 
 */
package library.content.purchase;

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

@Entity
@XmlRootElement(name="Review")
@Access(AccessType.FIELD)
@XmlAccessorType(XmlAccessType.FIELD)
public class Review {
	
	@Id
	@GeneratedValue(generator="ID-GENERATOR")
	private Long user_iD;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable=false)
	private User reviewFromUser;
	
	@Column(name="REVIEW_COMMENT", nullable=false)
	private String reviewComment;
	
	@Enumerated(EnumType.STRING)
	@Column(name="REVIEW_RATING", nullable=false)
	private Rating reviewRating;
	
	public Review(){
		
	}
	
	public Review(User u,String s, Rating r){
		this.reviewComment = s;
		this.reviewFromUser = u;
		this.reviewRating = r;
	}

	public User getReviewFromUser() {
		return reviewFromUser;
	}

	public void setReviewFromUser(User reviewFromUser) {
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
