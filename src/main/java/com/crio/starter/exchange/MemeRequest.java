package com.crio.starter.exchange;

import lombok. AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation. constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Request DTO for creating or updating a Meme
 * Contains validation constraints for input data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeRequest {
    
    /**
     * Name of the person posting the meme
     * Cannot be null, empty or blank
     * Must be between 1-100 characters
     */
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;
    
    /**
     * URL of the meme image
     * Cannot be null, empty or blank
     * Must be a valid URL format starting with http or https
     */
    @NotNull(message = "URL cannot be null")
    @NotEmpty(message = "URL cannot be empty")
    @NotBlank(message = "URL cannot be blank")
    @Pattern(regexp = "^https?://.*", message = "URL must be valid and start with http or https")
    private String url;
    
    /**
     * Caption for the meme
     * Cannot be null, empty or blank
     * Must be between 1-500 characters
     */
    @NotNull(message = "Caption cannot be null")
    @NotEmpty(message = "Caption cannot be empty")
    @NotBlank(message = "Caption cannot be blank")
    @Size(min = 1, max = 500, message = "Caption must be between 1 and 500 characters")
    private String caption;
}