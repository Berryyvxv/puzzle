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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.construction.puzzle.controllers.PuzzleController;

/**
 * Tests for the PuzzleController class
 */
class PuzzleControllerTest {

    private PuzzleController puzzleController;

    @Mock
    private ClassPathResource classPathResource;

    /**
     * Set up the test environment
     */
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        puzzleController = new PuzzleController();
    }

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
    void testGetUnsolvedPuzzleImage() throws IOException {
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
        assertThrows(ResponseStatusException.class, () -> {
            puzzleController.getUnsolvedPuzzleImage("999");
        });

        // Resource cannot be opened (throws IOException)
        when(classPathResource.exists()).thenReturn(true);
        when(classPathResource.getInputStream()).thenThrow(new IOException());
        assertThrows(ResponseStatusException.class, () -> {
            puzzleController.getUnsolvedPuzzleImage("1");
        });
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
        assertThrows(ResponseStatusException.class, () -> {
            puzzleController.getSolvedPuzzleImage("999");
        });

        // Resource cannot be opened (throws IOException)
        when(classPathResource.exists()).thenReturn(true);
        when(classPathResource.getInputStream()).thenThrow(new IOException());
        assertThrows(ResponseStatusException.class, () -> {
            puzzleController.getSolvedPuzzleImage("1");
        });
    }

}


    
