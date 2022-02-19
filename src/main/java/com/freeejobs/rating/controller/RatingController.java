package com.freeejobs.rating.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freeejobs.rating.model.Rating;
import com.freeejobs.rating.service.RatingService;

@RestController
@RequestMapping(value="/rating")
@CrossOrigin
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	@RequestMapping(value="/getRatingsByUserId", method= RequestMethod.GET)
	public List<Rating> getRatingsByUserId(HttpServletResponse response,
			@RequestParam long userId) throws URISyntaxException {
		
		List<Rating> ratings = null;
		
		try {
			ratings = ratingService.getRatingsByUserId(userId);
				if(ratings == null) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return null;
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
				}
			
				
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return ratings;
	}
	
	@RequestMapping(value="/getRatingsByUserIdJobId", method= RequestMethod.GET)
	public List<Rating> getRatingsByUserIdJobId(HttpServletResponse response,
			@RequestParam long userId, @RequestParam long jobId) throws URISyntaxException {
		
		List<Rating> ratings = new ArrayList<Rating>();
		
		try {
			ratings = ratingService.getRatingsByUserIdJobId(userId, jobId);
//				if(ratings == null) {
//					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//					return null;
//				} else {
					response.setStatus(HttpServletResponse.SC_OK);
//				}
			
				
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return ratings;
	}
	
	@PostMapping("/create")
    public void createRating(HttpServletResponse response, @RequestBody Rating rating) {
		//before create check if exist alr
		List<Rating> ratings = ratingService.getRatingsByUserIdJobId(rating.getUserId(), rating.getJobId());
		if(ratings.size()>0) {
			//ratings created already
		}
		//validate fields
		//handle errors
		ratingService.addRating(rating);
    }

}
