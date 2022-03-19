package com.freeejobs.rating.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.freeejobs.rating.model.Rating;
import com.freeejobs.rating.repository.RatingRepository;


@Service
public class RatingService {
	
	private static final Logger LOGGER = LogManager.getLogger(RatingService.class);
	
	@Autowired
	private RatingRepository ratingRepository;
	
	public List<Rating> getRatingsByTargetId(long targetId) {
		return ratingRepository.getRatingsByTargetId(targetId);
	}
	public List<Rating> getRatingsByReviewerId(long reviewerId) {
		return ratingRepository.getRatingsByReviewerId(reviewerId);
	}
	public List<Rating> getRatingsByReviewerIdJobId(long reviewerId, long jobId) {
		return ratingRepository.getRatingsByReviewerIdJobId(reviewerId,jobId);
	}

	public Rating addRating(Rating rating) {
		rating.setDateCreated(new Date());
		rating.setDateUpdated(new Date());
		return ratingRepository.save(rating);
	}

	public boolean isId(String id) {
		return String.valueOf(id).matches("[0-9]+");
	}
}

