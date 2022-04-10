package com.freeejobs.rating.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "rating")
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
		
	@Column(name = "jobId")
	private long jobId;
	
	@Column(name = "reviewerId")
	private long reviewerId;
	
	@Column(name = "targetId")
	private long targetId;
	
	@Column(name = "reviewTitle")
	private String reviewTitle;
	
	@Column(name = "review")
	private String review;
	
	@Column(name = "ratingScale")
	private int ratingScale;
	
	@Column(name = "dateCreated", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	@Column(name = "dateUpdated", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public long getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(long reviewerId) {
		this.reviewerId = reviewerId;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getRatingScale() {
		return ratingScale;
	}

	public void setRatingScale(int ratingScale) {
		this.ratingScale = ratingScale;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@Override
	public String toString() {
		return "Rating [jobId=" + jobId + ", reviewerId=" + reviewerId + ", targetId=" + targetId + ", reviewTitle="
				+ reviewTitle + ", review=" + review + ", ratingScale=" + ratingScale + ", dateCreated=" + dateCreated
				+ ", dateUpdated=" + dateUpdated + "]";
	}
	

}
