// Server-Side/ Backend
package com.construction.puzzle.controllers;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/puzzle")
public class PuzzleController {

    /**
     * Parses the puzzle ID from the request URL path
     *
     * @param requestPath the request URL path
     * @return the puzzle ID
     * @throws IllegalArgumentException if the request path does not contain a valid puzzle ID
     */
    private String parsePuzzleIdFromRequestPath(String requestPath) {
        String[] pathParts = requestPath.split("/");
        if (pathParts.length != 4 || !pathParts[1].equals("puzzle") || (!pathParts[2].equals("unsolved") && !pathParts[2].equals("solved"))) {
            throw new IllegalArgumentException("Invalid request path: " + requestPath);
        }
        return pathParts[3];
    }

    /**
     * Returns the unsolved puzzle image for a specific puzzle ID
     *
     * @param requestPath the request URL path
     * @return ResponseEntity containing the image file and headers
     * @throws IOException if there was an error reading the image file
     */
    @GetMapping("/unsolved/**")
    public ResponseEntity<Resource> getUnsolvedPuzzleImage(@PathVariable String requestPath) throws IOException {
        String puzzleId = parsePuzzleIdFromRequestPath(requestPath);
        Resource resource = new ClassPathResource("static/inkie_" + puzzleId + ".png");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    /**
     * Returns the solved puzzle image for a specific puzzle ID
     *
     * @param requestPath the request URL path
     * @return ResponseEntity containing the image file and headers
     * @throws IOException if there was an error reading the image file
     */
    @GetMapping("/solved/**")
    public ResponseEntity<Resource> getSolvedPuzzleImage(@PathVariable String requestPath) throws IOException {
        String puzzleId = parsePuzzleIdFromRequestPath(requestPath);
        Resource resource = new ClassPathResource("static/inkie_" + puzzleId + "_answer.png");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    /**
     * Returns the index page
     *
     * @return the name of the index page
     */
    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }
}
