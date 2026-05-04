package com.crio.starter.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Request DTO for updating an existing Meme
 * All fields are optional, only provided fields will be updated
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemeRequest {
    
    /**
     * Updated URL of the meme image (optional)
     */
    @Pattern(regexp = "^(http|https)://.*$", message = "URL must be valid and start with http or https")
    private String url;
    
    /**
     * Updated caption for the meme (optional)
     */
    @Size(min = 1, max = 500, message = "Caption must be between 1 and 500 characters")
    private String caption;
}