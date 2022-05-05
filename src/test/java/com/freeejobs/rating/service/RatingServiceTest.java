package com.freeejobs.rating.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.freeejobs.rating.WebConfig;
import com.freeejobs.rating.constants.AuditEnum;
import com.freeejobs.rating.controller.RatingController;
import com.freeejobs.rating.controller.RatingFixture;
import com.freeejobs.rating.dto.RatingDTO;
import com.freeejobs.rating.model.RatingAudit;
import com.freeejobs.rating.repository.RatingAuditRepository;
import com.freeejobs.rating.repository.RatingRepository;
import com.freeejobs.rating.response.APIResponse;
import com.freeejobs.rating.response.Status;
import com.freeejobs.rating.model.Rating;
import com.freeejobs.rating.service.RatingService;

@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {
	@Mock
	private RatingRepository ratingRepository;
	
	@Mock
	private RatingAuditRepository ratingAuditRepository;
	
	@InjectMocks
    private RatingService ratingService;
	
	@Mock
    private RatingService ratingServiceMock;
	
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
	
	@Test
    void testGetRatingsByTargetId() {
		
        Long targetId = Long.valueOf(2);
        when(ratingRepository.getRatingsByTargetId(targetId)).thenReturn(ratings);
        

        List<Rating> resRatings = ratingService.getRatingsByTargetId(targetId);
        assertEquals(resRatings.get(0).getId(), ratings.get(0).getId());
    }
	
    @Test
    void testGetRatingsByReviewerId() {
		
        Long reviewerId = Long.valueOf(1);
        when(ratingRepository.getRatingsByReviewerId(reviewerId)).thenReturn(ratings);
        

        List<Rating> resRatings = ratingService.getRatingsByReviewerId(reviewerId);
        assertEquals(resRatings.get(0).getId(), ratings.get(0).getId());
    }

    @Test
    void testGetRatingsByReviewerIdJobId() {
		
        Long reviewerId = Long.valueOf(1);
        Long jobId = Long.valueOf(1);
        when(ratingRepository.getRatingsByReviewerIdJobId(reviewerId,jobId)).thenReturn(ratings);
        

        List<Rating> resRatings = ratingService.getRatingsByReviewerIdJobId(reviewerId,jobId);
        assertEquals(resRatings.get(0).getId(), ratings.get(0).getId());
    }

    @Test
    void testAddRating() {    
		Date date = new Date();
		ratingDTO.setDateCreated(date);
		ratingDTO.setDateUpdated(date);

		when(ratingRepository.save(ratingDTO)).thenReturn(rating);
        ratingAudit.setOpsType(AuditEnum.INSERT.getCode());
        Mockito.lenient().when(ratingService.insertAudit(rating, AuditEnum.INSERT.getCode())).thenReturn(ratingAudit);

        Rating resRating = ratingService.addRating(ratingDTO);
        verify(ratingRepository, Mockito.times(1)).save(ratingDTO);

        assertEquals(resRating.getJobId(), ratingDTO.getJobId());
        assertEquals(resRating.getReviewerId(), ratingDTO.getReviewerId());
        assertEquals(resRating.getTargetId(), ratingDTO.getTargetId());
        assertEquals(resRating.getReviewTitle(), ratingDTO.getReviewTitle());
        assertEquals(resRating.getReview(), ratingDTO.getReview());
        assertNotNull(ratingDTO.getDateCreated());
        assertNotNull(ratingDTO.getDateUpdated());
        assertNotNull(resRating.getDateCreated());
        assertNotNull(resRating.getDateUpdated());
        assertNotNull(ratingDTO.getId());
    }

    @Test
    void testIsId() {    

        boolean valid = ratingService.isId("1");
        
        assertTrue(valid);
    }
	@Test
    void testIsNotId() {    

        boolean valid = ratingService.isId("abc");
        
        assertFalse(valid);
    }
	
	@Test
    void testIsBlank() {    

        boolean valid = ratingService.isBlank("");
        
        assertTrue(valid);
    }
	@Test
    void testIsNotBlank() {    

        boolean valid = ratingService.isBlank("ABC");
        
        assertFalse(valid);
    }
	
	@Test
    void testInsertAudit() {    

		Date date = new Date();
        RatingAudit ratingAuditLo = new RatingAudit();
        ratingAuditLo.setId(1);
        ratingAuditLo.setOpsType(AuditEnum.INSERT.getCode());
        ratingAuditLo.setAuditData(rating.toString());
        ratingAuditLo.setCreatedBy(String.valueOf(rating.getReviewerId()));
        ratingAuditLo.setDateCreated(date);

        assertEquals(ratingAuditLo.getId(), 1);
        assertEquals(ratingAuditLo.getAuditData(), rating.toString());
        assertEquals(ratingAuditLo.getDateCreated(), date);
        assertEquals(ratingAuditLo.getCreatedBy(), String.valueOf(rating.getReviewerId()));
        assertEquals(ratingAuditLo.getOpsType(),AuditEnum.INSERT.getCode());

    }
	
	@Test
    void testAuditEnum() {    

		AuditEnum.INSERT.setCode("T");
		AuditEnum.INSERT.setDescription("Test");

        assertEquals(AuditEnum.INSERT.getDescription(), "Test");
        assertEquals(AuditEnum.INSERT.getCode(), "T");

    }

}