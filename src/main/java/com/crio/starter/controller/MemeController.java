package com.crio.starter.controller;

import com.crio.starter.exchange.MemeIdResponse;
import com.crio.starter.exchange.MemeRequest;
import com.crio.starter.exchange.MemeResponse;
import com.crio.starter.exchange.UpdateMemeRequest;
import com.crio.starter.service. MemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses. ApiResponses;
import io. swagger.v3.oas. annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation. Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * REST Controller for Meme operations
 * Handles HTTP requests for creating, retrieving, updating and deleting memes
 */
@RestController
@RequestMapping("/memes")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Meme Controller", description = "APIs for managing memes in XMeme application")
public class MemeController {
    
    private final MemeService memeService;
    
    /**
     * Create a new meme
     * 
     * @param memeRequest Request body containing name, url and caption
     * @return Response with the ID of created meme
     */
    @PostMapping
    @Operation(
        summary = "Create a new meme",
        description = "Creates a new meme with the provided name, url and caption.  Returns the unique ID of the created meme."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Meme created successfully",
            content = @Content(schema = @Schema(implementation = MemeIdResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Duplicate meme - a meme with same name, url and caption already exists",
            content = @Content
        )
    })
    public ResponseEntity<MemeIdResponse> createMeme(
            @Valid @RequestBody 
            @Parameter(description = "Meme data containing name, url and caption", required = true)
            MemeRequest memeRequest) {
        
        log.info("POST /memes - Creating new meme for user: {}", memeRequest. getName());
        
        String memeId = memeService. createMeme(memeRequest);
        MemeIdResponse response = new MemeIdResponse(memeId);
        
        log.info("Meme created with ID: {}", memeId);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Get latest 100 memes
     * Returns empty array if no memes exist
     * 
     * @return List of latest 100 memes ordered by creation date (empty array if none exist)
     */
    @GetMapping
    @Operation(
        summary = "Get latest 100 memes",
        description = "Retrieves the latest 100 memes ordered by creation date in descending order.  Returns empty array if no memes exist."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved memes (returns empty array if no memes exist)",
            content = @Content(schema = @Schema(implementation = MemeResponse.class))
        )
    })
    public ResponseEntity<List<MemeResponse>> getLatestMemes() {
        log.info("GET /memes - Fetching latest 100 memes");
        
        List<MemeResponse> memes = memeService.getLatestMemes();
        
        // Ensure we return an empty array instead of null
        if (memes == null) {
            memes = new ArrayList<>();
        }
        
        log.info("Returning {} memes", memes.size());
        
        // Always return 200 OK with the list (empty or populated)
        return ResponseEntity.ok(memes);
    }
    
    /**
     * Get a specific meme by ID
     * 
     * @param id ID of the meme to retrieve
     * @return Meme data
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Get meme by ID",
        description = "Retrieves a specific meme by its unique ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Meme found",
            content = @Content(schema = @Schema(implementation = MemeResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Meme not found with the given ID",
            content = @Content
        )
    })
    public ResponseEntity<MemeResponse> getMemeById(
            @PathVariable 
            @Parameter(description = "Unique ID of the meme", required = true)
            String id) {
        
        log.info("GET /memes/{} - Fetching meme by ID", id);
        
        MemeResponse meme = memeService. getMemeById(id);
        
        log.info("Meme found with ID: {}", id);
        
        return ResponseEntity.ok(meme);
    }
    
    /**
     * Update an existing meme
     * Only url and caption can be updated
     * 
     * @param id ID of the meme to update
     * @param updateRequest Request body containing fields to update
     * @return Updated meme data
     */
    @PatchMapping("/{id}")
    @Operation(
        summary = "Update a meme",
        description = "Updates an existing meme.  Only url and caption can be updated.  Name cannot be changed."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Meme updated successfully",
            content = @Content(schema = @Schema(implementation = MemeResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Meme not found with the given ID",
            content = @Content
        )
    })
    public ResponseEntity<MemeResponse> updateMeme(
            @PathVariable 
            @Parameter(description = "Unique ID of the meme to update", required = true)
            String id,
            @Valid @RequestBody 
            @Parameter(description = "Fields to update (url and/or caption)", required = true)
            UpdateMemeRequest updateRequest) {
        
        log.info("PATCH /memes/{} - Updating meme", id);
        
        MemeResponse updatedMeme = memeService. updateMeme(id, updateRequest);
        
        log.info("Meme updated successfully with ID: {}", id);
        
        return ResponseEntity.ok(updatedMeme);
    }
    
    /**
     * Delete a meme
     * 
     * @param id ID of the meme to delete
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a meme",
        description = "Deletes a meme by its unique ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Meme deleted successfully",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Meme not found with the given ID",
            content = @Content
        )
    })
    public ResponseEntity<Void> deleteMeme(
            @PathVariable 
            @Parameter(description = "Unique ID of the meme to delete", required = true)
            String id) {
        
        log.info("DELETE /memes/{} - Deleting meme", id);
        
        memeService. deleteMeme(id);
        
        log.info("Meme deleted successfully with ID: {}", id);
        
        return ResponseEntity.noContent().build();
    }
}