package com.crio.starter.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Response DTO for Meme data
 * Used to send meme information to the frontend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
public class MemeResponse {
    
    /**
     * Unique identifier of the meme
     */
    private String id;
    
    /**
     * Name of the person who posted the meme
     */
    private String name;
    
    /**
     * URL of the meme image
     */
    private String url;
    
    /**
     * Caption/description for the meme
     */
    private String caption;
    
    /**
     * Timestamp when the meme was created (optional for response)
     */
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the meme was last updated (optional for response)
     */
    private LocalDateTime updatedAt;
}