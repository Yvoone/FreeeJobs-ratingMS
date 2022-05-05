package com.freeejobs.rating.controller;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.context.WebApplicationContext;

import com.freeejobs.rating.WebConfig;
import com.freeejobs.rating.dto.RatingDTO;
import com.freeejobs.rating.response.APIResponse;
import com.freeejobs.rating.response.Status;
import com.freeejobs.rating.model.Rating;
import com.freeejobs.rating.model.RatingAudit;
import com.freeejobs.rating.service.RatingService;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {
	
	@Mock
	private RatingService ratingService;
	
	@InjectMocks
    private RatingController ratingController;
	
	private Rating rating;
	private RatingDTO ratingDTO;
	private List<Rating> ratings;
    private RatingAudit ratingAudit;
	private int numberOfListingPerPage=10;
	
	@BeforeEach
    void setUp() {
        rating = RatingFixture.createRating();
        ratingDTO = RatingFixture.createRatingDTO();
        ratings = RatingFixture.createRatingList();        
        ratingAudit = RatingFixture.createRatingAudit();
    }
	
	//getJobListingById
	
	@SuppressWarnings("unchecked")
	@Test
    void testGetRatingsByTargetId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long targetId = Long.valueOf(2);
        when(ratingService.isId(String.valueOf(targetId))).thenReturn(true);
        when(ratingService.getRatingsByTargetId(targetId)).thenReturn(ratings);

        APIResponse rating = ratingController.getRatingsByTargetId(response, targetId);
        assertEquals(ratings.get(0).getId(), ((List<Rating>) rating.getData()).get(0).getId());
    }
	
	@Test
    void testGetRatingsByTargetIdisNotID() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long targetId = Long.valueOf(2);
        when(ratingService.isId(String.valueOf(targetId))).thenReturn(false);

        APIResponse rating = ratingController.getRatingsByTargetId(response, targetId);
        assertEquals(((Rating) rating.getData()), null);
        //assertEquals(jobListing.getId(), ((JobListing) listings.getData()).getId());
    }
	
	@Test
    void testGetRatingsByTargetIdNull() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long targetId = Long.valueOf(2);
        when(ratingService.isId(String.valueOf(targetId))).thenReturn(true);
        when(ratingService.getRatingsByTargetId(targetId)).thenReturn(null);

        APIResponse rating = ratingController.getRatingsByTargetId(response, targetId);
        assertEquals(((Rating) rating.getData()), null);
    }
	
	@Test
    void testGetRatingsByTargetIdThrowException() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long targetId = Long.valueOf(2);
        when(ratingService.isId(String.valueOf(targetId))).thenReturn(true);
        when(ratingService.getRatingsByTargetId(targetId)).thenThrow(UnexpectedRollbackException.class);

        APIResponse rating = ratingController.getRatingsByTargetId(response, targetId);
        assertEquals(((Rating) rating.getData()), null);
    }

    //////////////////////

    @SuppressWarnings("unchecked")
	@Test
    void testGetRatingsByReviewerId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long targetId = Long.valueOf(1);
        when(ratingService.isId(String.valueOf(targetId))).thenReturn(true);
        when(ratingService.getRatingsByReviewerId(targetId)).thenReturn(ratings);

        APIResponse rating = ratingController.getRatingsByReviewerId(response, targetId);
        assertEquals(ratings.get(0).getId(), ((List<Rating>) rating.getData()).get(0).getId());
    }
	
	@Test
    void testGetRatingsByReviewerIdisNotID() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long targetId = Long.valueOf(1);
        when(ratingService.isId(String.valueOf(targetId))).thenReturn(false);

        APIResponse rating = ratingController.getRatingsByReviewerId(response, targetId);
        assertEquals(((Rating) rating.getData()), null);
        //assertEquals(jobListing.getId(), ((JobListing) listings.getData()).getId());
    }
	
	@Test
    void testGetRatingsByReviewerIdNull() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long targetId = Long.valueOf(1);
        when(ratingService.isId(String.valueOf(targetId))).thenReturn(true);
        when(ratingService.getRatingsByReviewerId(targetId)).thenReturn(null);

        APIResponse rating = ratingController.getRatingsByReviewerId(response, targetId);
        assertEquals(((Rating) rating.getData()), null);
    }
	
	@Test
    void testGetRatingsByReviewerIdThrowException() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long targetId = Long.valueOf(1);
        when(ratingService.isId(String.valueOf(targetId))).thenReturn(true);
        when(ratingService.getRatingsByReviewerId(targetId)).thenThrow(UnexpectedRollbackException.class);

        APIResponse rating = ratingController.getRatingsByReviewerId(response, targetId);
        assertEquals(((Rating) rating.getData()), null);
    }

    /////////////////////////////////

    @SuppressWarnings("unchecked")
	@Test
    void testGetRatingsByReviewerIdJobId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long reviewerId = Long.valueOf(1);
        Long jobId = Long.valueOf(1);
        when(ratingService.isId(String.valueOf(jobId))).thenReturn(true);
        when(ratingService.isId(String.valueOf(reviewerId))).thenReturn(true);
        when(ratingService.getRatingsByReviewerIdJobId(reviewerId, jobId)).thenReturn(ratings);

        APIResponse rating = ratingController.getRatingsByReviewerIdJobId(response, reviewerId, jobId);
        List<Rating> resRatings = (List<Rating>) rating.getData();
        assertEquals(resRatings.get(0).getId(), ratings.get(0).getId());
        
    }

    @SuppressWarnings("unchecked")
	@Test
    void testGetRatingsByReviewerIdJobId_ReviewIdNotId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long reviewerId = Long.valueOf(1);
        Long jobId = Long.valueOf(1);
        when(ratingService.isId(String.valueOf(jobId))).thenReturn(false);
        // when(ratingService.isId(String.valueOf(reviewerId))).thenReturn(true);
        // when(ratingService.getRatingsByReviewerIdJobId(reviewerId, jobId)).thenReturn(rating);

        APIResponse rating = ratingController.getRatingsByReviewerIdJobId(response, reviewerId, jobId);
        List<Rating> resRatings = (List<Rating>) rating.getData();
        assertEquals(resRatings.size(), 0);
        
    }

    @SuppressWarnings("unchecked")
	@Test
    void testGetRatingsByReviewerIdJobId_JobIdNotId() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long reviewerId = Long.valueOf(1);
        Long jobId = Long.valueOf(1);
        when(ratingService.isId(String.valueOf(reviewerId))).thenReturn(false);

        APIResponse rating = ratingController.getRatingsByReviewerIdJobId(response, reviewerId, jobId);
        List<Rating> resRatings = (List<Rating>) rating.getData();
        assertEquals(resRatings.size(), 0);
        
    }

    @SuppressWarnings("unchecked")
	@Test
    void testGetRatingsByReviewerIdJobId_null() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long reviewerId = Long.valueOf(1);
        Long jobId = Long.valueOf(1);
        when(ratingService.isId(String.valueOf(jobId))).thenReturn(true);
        when(ratingService.isId(String.valueOf(reviewerId))).thenReturn(true);
        when(ratingService.getRatingsByReviewerIdJobId(reviewerId, jobId)).thenReturn(null);

        APIResponse rating = ratingController.getRatingsByReviewerIdJobId(response, reviewerId, jobId);
        List<Rating> resRatings = (List<Rating>) rating.getData();
        assertEquals(resRatings, null);
        
    }

    @SuppressWarnings("unchecked")
	@Test
    void testGetRatingsByReviewerIdJobId_throwExcpetion() throws URISyntaxException {    
        HttpServletResponse response = mock(HttpServletResponse.class); 
        Long reviewerId = Long.valueOf(1);
        Long jobId = Long.valueOf(1);
        when(ratingService.isId(String.valueOf(jobId))).thenReturn(true);
        when(ratingService.isId(String.valueOf(reviewerId))).thenReturn(true);
        when(ratingService.getRatingsByReviewerIdJobId(reviewerId, jobId)).thenThrow(UnexpectedRollbackException.class);

        APIResponse rating = ratingController.getRatingsByReviewerIdJobId(response, reviewerId, jobId);
        List<Rating> resRatings = (List<Rating>) rating.getData();
        assertEquals(resRatings.size(), 0);
        
    }

    //////////////////////////////////////////
		
		@Test
	    void testCreateRating() {
			HttpServletResponse response = mock(HttpServletResponse.class); 
			Date date = new Date();
			ratingDTO.setDateCreated(date);
			ratingDTO.setDateUpdated(date);
			
			rating.setDateCreated(date);
			rating.setDateUpdated(date);
			
			List<Rating> emptyArray = new ArrayList<Rating>();
			
            when(ratingService.getRatingsByReviewerIdJobId(ratingDTO.getReviewerId(), ratingDTO.getJobId())).thenReturn(emptyArray);
			Status stat = new Status();
			stat.setStatusCode(Status.Type.OK.getCode());
			stat.setMessage("Successfully create rating.");
			stat.setStatusText(Status.Type.OK.getText());
			
			when(ratingService.isBlank(ratingDTO.getReviewTitle())).thenReturn(false);
			when(ratingService.isBlank(ratingDTO.getReview())).thenReturn(false);
			when(ratingService.isId(String.valueOf(ratingDTO.getJobId()))).thenReturn(true);
	        when(ratingService.isId(String.valueOf(ratingDTO.getReviewerId()))).thenReturn(true);
	        when(ratingService.isId(String.valueOf(ratingDTO.getTargetId()))).thenReturn(true);
	        when(ratingService.isId(String.valueOf(ratingDTO.getRatingScale()))).thenReturn(true);
	        when(ratingService.addRating(ratingDTO)).thenReturn(rating);
	        APIResponse res = ratingController.createRating(response, ratingDTO);
	        Rating resRatings = (Rating) res.getData();
	        Status resStatus = res.getStatus();
	        


	        assertEquals(resStatus.getStatusCode(), stat.getStatusCode());
	        assertEquals(resStatus.getStatusText(), stat.getStatusText());
	        assertEquals(resStatus.getMessage(), stat.getMessage());
	        assertEquals(resRatings.getId(), rating.getId());
	    }
		@Test
	    void testCreateRatingError() {
			HttpServletResponse response = mock(HttpServletResponse.class);
			when(ratingService.isBlank(rating.getReviewTitle())).thenReturn(true);
			when(ratingService.isBlank(rating.getReview())).thenReturn(true);
			when(ratingService.isId(String.valueOf(rating.getJobId()))).thenReturn(false);
	        when(ratingService.isId(String.valueOf(rating.getReviewerId()))).thenReturn(false);
	        when(ratingService.isId(String.valueOf(rating.getTargetId()))).thenReturn(false);
	        when(ratingService.isId(String.valueOf(rating.getRatingScale()))).thenReturn(false);
	        //when(jobListingService.addJobListing(jobListing)).thenReturn(jobListing);
	        APIResponse res = ratingController.createRating(response, ratingDTO);
	        Rating resRatings = (Rating) res.getData();
	        verify(ratingService, Mockito.times(0)).addRating(ratingDTO);

	        assertEquals(resRatings, null);
	    }
		
		@Test
	    void testCreateRatingNull() throws URISyntaxException {    
			HttpServletResponse response = mock(HttpServletResponse.class);
			Date date = new Date();
			ratingDTO.setDateCreated(date);
			ratingDTO.setDateUpdated(date);
			
			rating.setDateCreated(date);
			rating.setDateUpdated(date);
			when(ratingService.isBlank(rating.getReviewTitle())).thenReturn(false);
			when(ratingService.isBlank(rating.getReview())).thenReturn(false);
			when(ratingService.isId(String.valueOf(rating.getJobId()))).thenReturn(true);
	        when(ratingService.isId(String.valueOf(rating.getReviewerId()))).thenReturn(true);
	        when(ratingService.isId(String.valueOf(rating.getTargetId()))).thenReturn(true);
	        when(ratingService.isId(String.valueOf(rating.getRatingScale()))).thenReturn(true);
	        when(ratingService.addRating(ratingDTO)).thenReturn(null);
	        APIResponse res = ratingController.createRating(response, ratingDTO);
	        Rating resRatings = (Rating) res.getData();

	        //assertEquals(jobListing.getId(), resListings.getId());
	        assertEquals(resRatings, null);
	    }
		
		@Test
	    void testCreateRatingThrowException() throws URISyntaxException {    
			HttpServletResponse response = mock(HttpServletResponse.class);
			Date date = new Date();
			ratingDTO.setDateCreated(date);
			ratingDTO.setDateUpdated(date);
			
			rating.setDateCreated(date);
			rating.setDateUpdated(date);
			when(ratingService.isBlank(rating.getReviewTitle())).thenReturn(false);
			when(ratingService.isBlank(rating.getReview())).thenReturn(false);
			when(ratingService.isId(String.valueOf(rating.getJobId()))).thenReturn(true);
	        when(ratingService.isId(String.valueOf(rating.getReviewerId()))).thenReturn(true);
	        when(ratingService.isId(String.valueOf(rating.getTargetId()))).thenReturn(true);
	        when(ratingService.isId(String.valueOf(rating.getRatingScale()))).thenReturn(true);
	        when(ratingService.addRating(ratingDTO)).thenThrow(UnexpectedRollbackException.class);
	        APIResponse res = ratingController.createRating(response, ratingDTO);
	        Rating resRatings = (Rating) res.getData();

	        assertNull(resRatings);
	    }
		
		@Test
	    void testCreateRatingCreatedBefore() {
			HttpServletResponse response = mock(HttpServletResponse.class); 
			
            when(ratingService.getRatingsByReviewerIdJobId(rating.getReviewerId(), rating.getJobId())).thenReturn(ratings);
		
	        APIResponse res = ratingController.createRating(response, ratingDTO);
	        Rating resRatings = (Rating) res.getData();
	        verify(ratingService, Mockito.times(0)).addRating(ratingDTO);

	        assertEquals(resRatings, null);
	    }
}