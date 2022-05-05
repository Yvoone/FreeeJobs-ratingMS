package com.freeejobs.rating.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.freeejobs.rating.model.RatingAudit;
import com.freeejobs.rating.response.Status;
import com.freeejobs.rating.dto.RatingDTO;
import com.freeejobs.rating.model.Rating;

public class RatingFixture {
	
	public static Rating createRating() {

		Rating rating = new Rating();
        rating.setId(Long.valueOf(1));
        rating.setJobId(Long.valueOf(1));
        rating.setReviewerId(1);
        rating.setTargetId(2);
        rating.setReviewTitle("Review Title Test");
        rating.setReview("Test Review");
        rating.setRatingScale(5);
        rating.setDateCreated(new Date());
        rating.setDateUpdated(new Date());
        

        return rating;
    }
	
	public static List<Rating> createRatingList() {

        List<Rating> ratings = new ArrayList<>();
        Rating rating = new Rating();
        rating.setId(Long.valueOf(2));
        rating.setJobId(Long.valueOf(2));
        rating.setReviewerId(3);
        rating.setTargetId(4);
        rating.setReviewTitle("Review Title Test");
        rating.setReview("Test Review");
        rating.setRatingScale(5);
        rating.setDateCreated(new Date());
        rating.setDateUpdated(new Date());
        ratings.add(rating);

        

        return ratings;
    }
	
	public static RatingDTO createRatingDTO() {

		RatingDTO rating = new RatingDTO();
        rating.setId(Long.valueOf(1));
        rating.setJobId(Long.valueOf(1));
        rating.setReviewerId(1);
        rating.setTargetId(2);
        rating.setReviewTitle("Review Title Test");
        rating.setReview("Test Review");
        rating.setRatingScale(5);
        rating.setDateCreated(new Date());
        rating.setDateUpdated(new Date());
        

        return rating;
    }
	// public static List<JobListing> createOpenJoblistingList() {

    //     List<JobListing> listings = new ArrayList<>();
    //     JobListing jobListing = new JobListing();
    //     jobListing.setId(Long.valueOf(2));
    //     jobListing.setAuthorId(Long.valueOf(2));
    //     jobListing.setStatus(JobListingStatusEnum.OPEN_FOR_APPLICATION.getCode());
    //     jobListing.setTitle("Test Title");
    //     jobListing.setRate("10");
    //     jobListing.setRateType("Per Hour");
    //     jobListing.setDetails("Test Details");
    //     jobListing.setDateCreated(new Date());
    //     jobListing.setDateUpdated(new Date());
    //     listings.add(jobListing);

    //     return listings;
    // }
	
	// public static Optional<JobListing> createJobListingOptional() {

	// 	JobListing listing = new JobListing();
    //     Optional<JobListing> listings = Optional.of(listing);
    //     listing.setId(Long.valueOf(2));
    //     listing.setAuthorId(Long.valueOf(2));
    //     listing.setStatus(JobListingStatusEnum.OPEN_FOR_APPLICATION.getCode());
    //     listing.setTitle("Test Title");
    //     listing.setRate("10");
    //     listing.setRateType("Per Hour");
    //     listing.setDetails("Test Details");
    //     listing.setDateCreated(new Date());
    //     listing.setDateUpdated(new Date());

    //     return listings;
    // }
	
//	public static Status createStatusError() {
//		Status status = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed.");;
//		return status;
//	}
//	
//	public static Status createStatusOk() {
//		Status status = new Status(Status.Type.OK, "Successful.");
//		return status;
//	}
	
	public static RatingAudit createRatingAudit() {

		RatingAudit ratingAudit = new RatingAudit();
		ratingAudit.setAuditData("Audit");
		ratingAudit.setCreatedBy("SYSTEM");
		ratingAudit.setDateCreated(new Date());
		ratingAudit.setId(1);
        

        return ratingAudit;
    }
	
	
}