package com.freeejobs.rating.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.freeejobs.rating.constants.AuditEnum;
import com.freeejobs.rating.dto.RatingDTO;
import com.freeejobs.rating.model.Rating;
import com.freeejobs.rating.model.RatingAudit;
import com.freeejobs.rating.repository.RatingAuditRepository;
import com.freeejobs.rating.repository.RatingRepository;


@Service
public class RatingService {
	
	private static final Logger LOGGER = LogManager.getLogger(RatingService.class);
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private RatingAuditRepository ratingAuditRepository;
	
	public List<Rating> getRatingsByTargetId(long targetId) {
		return ratingRepository.getRatingsByTargetId(targetId);
	}
	public List<Rating> getRatingsByReviewerId(long reviewerId) {
		return ratingRepository.getRatingsByReviewerId(reviewerId);
	}
	public List<Rating> getRatingsByReviewerIdJobId(long reviewerId, long jobId) {
		return ratingRepository.getRatingsByReviewerIdJobId(reviewerId,jobId);
	}

	public Rating addRating(RatingDTO rating) {
		Rating addRating = new Rating();
		addRating.setJobId(rating.getJobId());
		addRating.setRatingScale(rating.getRatingScale());
		addRating.setReview(rating.getReview());
		addRating.setReviewTitle(rating.getReviewTitle());
		addRating.setTargetId(rating.getTargetId());
		addRating.setReviewerId(rating.getReviewerId());
		addRating.setDateCreated(new Date());
		addRating.setDateUpdated(new Date());
		Rating addedRating = ratingRepository.save(addRating);
		insertAudit(addedRating, AuditEnum.INSERT.getCode());
		return addedRating;
		
	}

	public boolean isId(String id) {
		return String.valueOf(id).matches("[0-9]+");
	}
	
	public boolean isBlank(String value) {
		return StringUtils.isBlank(value);
	}
	
	public RatingAudit insertAudit(Rating rating, String opsType) {
		RatingAudit newAuditEntry = new RatingAudit();
		newAuditEntry.setAuditData(rating.toString());
		newAuditEntry.setOpsType(opsType);
		newAuditEntry.setDateCreated(new Date());
		newAuditEntry.setCreatedBy(String.valueOf(rating.getReviewerId()));
		
		return ratingAuditRepository.save(newAuditEntry);
	}
}

