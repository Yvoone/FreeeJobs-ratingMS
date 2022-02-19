package com.freeejobs.rating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.freeejobs.rating.model.Rating;


@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
	public Rating findById(long id);
	public List<Rating> findAll();
	
	@Query("select r from Rating r where t.userId = ?1")
	public List<Rating> getRatingsByUserId(long userId);

	@Query("select r from Rating r where t.userId = ?1 and t.jobId=?2")
	public List<Rating> getRatingsByUserIdJobId(long userId, long jobId);

}
