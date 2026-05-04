package com.crio.starter.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO containing only the meme ID
 * Used when creating a new meme to return the generated ID
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeIdResponse {
    
    /**
     * Unique identifier of the created meme
     */
    private String id;
}