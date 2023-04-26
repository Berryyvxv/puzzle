package com;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.construction.puzzle.PuzzleApplication;
import com.construction.puzzle.controllers.PuzzleController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for the PuzzleController class
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = PuzzleApplication.class)
@AutoConfigureMockMvc
class PuzzleControllerTest {

    @Autowired
    private PuzzleController puzzleController;

    @Mock
    private ClassPathResource classPathResource;

    /**
     * Set up the test environment
     */
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    // PuzzleControllerTest(PuzzleController puzzleController) {
    //     this.puzzleController = puzzleController;
    // }

    /**
     * Test the getUnsolvedPuzzleImage method with a valid puzzle ID
     * 
     * Partitions:
     * - Valid puzzle ID (1, 2, 3, ...)
     * - Invalid puzzle ID (not found in resources)
     * 
     * Subdomains:
     * - Resource exists
     * - Resource does not exist
     * - Resource can be opened
     * - Resource cannot be opened (throws IOException)
     * 
     * Coverage:
     * - ResponseEntity is not null
     * - Response headers have the correct content type
     * - Response body is not null
     */
    @Test
    void testGetUnsolvedPuzzleImage() throws Exception {
        // Valid puzzle ID, resource exists, can be opened
        when(classPathResource.exists()).thenReturn(true);
        when(classPathResource.getInputStream()).thenReturn(null);
        ResponseEntity<Resource> response = puzzleController.getUnsolvedPuzzleImage("1");
        assertNotNull(response);
        HttpHeaders headers = response.getHeaders();
        assertEquals(MediaType.IMAGE_PNG, headers.getContentType());
        assertNotNull(response.getBody());

        // Invalid puzzle ID, resource does not exist
        when(classPathResource.exists()).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> {
            puzzleController.getUnsolvedPuzzleImage("999a");
        });

        // Resource cannot be opened (throws IOException)
        // when(classPathResource.exists()).thenReturn(true);
        // when(classPathResource.getInputStream()).thenThrow(new IOException());
        // assertThrows(ResponseStatusException.class, () -> {
        //     puzzleController.getUnsolvedPuzzleImage("2");
        //     System.out.println(puzzleController.getUnsolvedPuzzleImage("2"));
        // });
    }

    /**
     * Test the getSolvedPuzzleImage method with a valid puzzle ID
     * 
     * Partitions:
     * - Valid puzzle ID (1, 2, 3, ...)
     * - Invalid puzzle ID (not found in resources)
     * 
     * Subdomains:
     * - Resource exists
     * - Resource does not exist
     * - Resource can be opened
     * - Resource cannot be opened (throws IOException)
     * 
     * Coverage:
     * - ResponseEntity is not null
     * - Response headers have the correct content type
     * - Response body is not null
     */
    @Test
    void testGetSolvedPuzzleImage() throws IOException {
        // Valid puzzle ID, resource exists, can be opened
        when(classPathResource.exists()).thenReturn(true);
        when(classPathResource.getInputStream()).thenReturn(null);
        ResponseEntity<Resource> response = puzzleController.getSolvedPuzzleImage("1");
        assertNotNull(response);
        HttpHeaders headers = response.getHeaders();
        assertEquals(MediaType.IMAGE_PNG, headers.getContentType());
        assertNotNull(response.getBody());

        // Invalid puzzle ID, resource does not exist
        when(classPathResource.exists()).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> {
            puzzleController.getSolvedPuzzleImage("999b");
        });

        // Resource cannot be opened (throws IOException)
        // when(classPathResource.exists()).thenReturn(true);
        // when(classPathResource.getInputStream()).thenThrow(new IOException());
        // assertThrows(ResponseStatusException.class, () -> {
        //     puzzleController.getSolvedPuzzleImage("1");
        // });
    }

}


    
