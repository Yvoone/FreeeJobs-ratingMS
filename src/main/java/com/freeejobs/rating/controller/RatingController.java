package com.freeejobs.rating.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@CrossOrigin("https://freeejobs-web.herokuapp.com")
public class RatingController {
	
	private static Logger LOGGER = LogManager.getLogger(RatingController.class);
	
	@Autowired
	private RatingService ratingService;
	
	@RequestMapping(value="/getRatingsByTargetId", method= RequestMethod.GET)
	public APIResponse getRatingsByTargetId(HttpServletResponse response,
			@RequestParam long targetId) throws URISyntaxException {
		
		List<Rating> ratings = null;
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");
		
		try {
			if(!ratingService.isId(String.valueOf(targetId))){
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By TargetId. Invalid Id.");
				LOGGER.error(responseStatus.toString());
			}else {
				ratings = ratingService.getRatingsByTargetId(targetId);
				if(ratings == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By TargetId.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully get Ratings By TargetId.");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By TargetId, Exception.");
			LOGGER.error(e.getMessage(), e);
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
			if(!ratingService.isId(String.valueOf(reviewerId))){
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By ReviewerId. Invalid Id.");
				LOGGER.error(responseStatus.toString());
			}else {
				ratings = ratingService.getRatingsByReviewerId(reviewerId);
				if(ratings == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By ReviewerId.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully get Ratings By ReviewerId.");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By ReviewerId, Exception.");
			LOGGER.error(e.getMessage(), e);
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
		List<String> errors = new ArrayList<String>();
		
		try {
			if (!ratingService.isId(String.valueOf(reviewerId))) {
				errors.add("Invalid reviewer id value");
			}
			if (!ratingService.isId(String.valueOf(jobId))) {
				errors.add("Invalid job id value");
			}
			if (errors.isEmpty()) {
				ratings = ratingService.getRatingsByReviewerIdJobId(reviewerId, jobId);
				if(ratings == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By ReviewerId JobId.");
					LOGGER.error(responseStatus.toString());
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully get Ratings By ReviewerId JobId.");
				}
			}else {
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR,
						"Failed to get Ratings By ReviewerId JobId. Invalid Rating Object.");
				String listOfErrors = errors.stream().map(Object::toString).collect(Collectors.joining(", "));
				LOGGER.error(responseStatus.toString() + " " + listOfErrors);
			}
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to get Ratings By ReviewerId JobId, Exception");
			LOGGER.error(e.getMessage(), e);
		}
		resp.setData(ratings);
		resp.setStatus(responseStatus);
		return resp;
	}
	
	@PostMapping("/create")
    public APIResponse createRating(HttpServletResponse response, @RequestBody Rating rating) {
		APIResponse resp = new APIResponse();
		Status responseStatus = new Status(Status.Type.OK, "Account login success.");		
		Rating ratingCreate = null;
		
		List<String> errors = new ArrayList<String>();
		try {
			//before create check if exist alr
			List<Rating> ratings = ratingService.getRatingsByReviewerIdJobId(rating.getReviewerId(), rating.getJobId());
			if(ratings.size()>0) {
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to create rating. Rating already created.");
				LOGGER.error(responseStatus.toString());
				resp.setStatus(responseStatus);
				return resp;
			}
			//validate fields
			if(ratingService.isBlank(rating.getReviewTitle())) {
				errors.add("Invalid title value");
			}
			if(ratingService.isBlank(rating.getReview())) {
				errors.add("Invalid review value");
			}
			if(!ratingService.isId(String.valueOf(rating.getJobId()))) {
				errors.add("Invalid job id value");
			}
			if(!ratingService.isId(String.valueOf(rating.getReviewerId()))) {
				errors.add("Invalid reviewer id value");
			}
			if(!ratingService.isId(String.valueOf(rating.getTargetId()))) {
				errors.add("Invalid target id value");
			}
			if(!ratingService.isId(String.valueOf(rating.getRatingScale()))) {
				errors.add("Invalid rating value");
			}
			if(errors.isEmpty()) {
				ratingCreate = ratingService.addRating(rating);
				if(ratingCreate == null) {
					//response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					//return null;
					responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to create rating.");
					
				} else {
					//response.setStatus(HttpServletResponse.SC_OK);
					responseStatus = new Status(Status.Type.OK, "Successfully create rating.");
				}
			}else {
				responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to create rating. Invalid Rating Object.");
				String listOfErrors = errors.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
				LOGGER.error(responseStatus.toString()+" "+listOfErrors);
			}
			
		} catch (Exception e) {
			System.out.println(e);
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			responseStatus = new Status(Status.Type.INTERNAL_SERVER_ERROR, "Failed to create rating, Exception");
			LOGGER.error(e.getMessage(), e);
		}
		resp.setData(ratingCreate);
		resp.setStatus(responseStatus);
		return resp;
    }

}
