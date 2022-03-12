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

import com.freeejobs.rating.response.APIResponse;
import com.freeejobs.rating.response.Status;
import com.freeejobs.rating.model.Rating;
import com.freeejobs.rating.service.RatingService;

@RestController
@RequestMapping(value="/rating")
@CrossOrigin
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	@RequestMapping(value="/getRatingsByTargetId", method= RequestMethod.GET)
	public APIResponse getRatingsByTargetId(HttpServletResponse response,
			@RequestParam long targetId) throws URISyntaxException {
		
		List<Rating> ratings = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			ratings = ratingService.getRatingsByTargetId(targetId);
				if(ratings == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By TargetId.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully get Ratings By TargetId.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By TargetId, Exception.");
		}
		resp.setData(ratings);
		resp.setStatus(responseStatus);
		return resp;
	}
	
	@RequestMapping(value="/getRatingsByReviewerId", method= RequestMethod.GET)
	public APIResponse getRatingsByReviewerId(HttpServletResponse response,
			@RequestParam long reviewerId) throws URISyntaxException {
		
		List<Rating> ratings = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			ratings = ratingService.getRatingsByReviewerId(reviewerId);
				if(ratings == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By ReviewerId.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully get Ratings By ReviewerId.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By ReviewerId, Exception.");
		}
		resp.setData(ratings);
		resp.setStatus(responseStatus);
		return resp;
		
	}
	
	@RequestMapping(value="/getRatingsByReviewerIdJobId", method= RequestMethod.GET)
	public APIResponse getRatingsByReviewerIdJobId(HttpServletResponse response,
			@RequestParam long reviewerId, @RequestParam long jobId) throws URISyntaxException {
		
		List<Rating> ratings = new ArrayList<Rating>();
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			ratings = ratingService.getRatingsByReviewerIdJobId(reviewerId, jobId);
				if(ratings == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By ReviewerId JobId.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully get Ratings By ReviewerId JobId.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By ReviewerId JobId, Exception");
		}
		resp.setData(ratings);
		resp.setStatus(responseStatus);
		return resp;
	}
	
	@PostMapping("/create")
    public APIResponse createRating(HttpServletResponse response, @RequestBody Rating rating) {
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		//before create check if exist alr
		List<Rating> ratings = ratingService.getRatingsByReviewerIdJobId(rating.getReviewerId(), rating.getJobId());
		if(ratings.size()>0) {
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to create rating. Rating already created.");
			resp.setStatus(responseStatus);
			return resp;
		}
		
		Rating ratingCreate = null;
		try {
			//validate fields
			//handle errors
			ratingCreate = ratingService.addRating(rating);
				if(ratingCreate == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to create rating.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully create rating.");
				}
			
				
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to create rating, Exception");
		}
		resp.setData(ratings);
		resp.setStatus(responseStatus);
		return resp;
    }

}
