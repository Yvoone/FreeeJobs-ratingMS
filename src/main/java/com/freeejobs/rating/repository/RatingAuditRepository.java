package com.freeejobs.rating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.freeejobs.rating.model.Rating;
import com.freeejobs.rating.model.RatingAudit;


@Repository
public interface RatingAuditRepository extends JpaRepository<RatingAudit, Long> {
	
}
