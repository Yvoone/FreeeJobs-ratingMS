package com.freeejobs.rating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.freeejobs.rating.dto.RatingDTO;
import com.freeejobs.rating.model.Rating;


@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
	public Rating save(RatingDTO ratingDTO);
	public Rating findById(long id);
	public List<Rating> findAll();

	@Query("select r from Rating r where r.targetId = ?1")
	public List<Rating> getRatingsByTargetId(long userId);
	
	@Query("select r from Rating r where r.reviewerId = ?1")
	public List<Rating> getRatingsByReviewerId(long userId);

	@Query("select r from Rating r where r.reviewerId = ?1 and r.jobId=?2")
	public List<Rating> getRatingsByReviewerIdJobId(long reviewerId, long jobId);

}
