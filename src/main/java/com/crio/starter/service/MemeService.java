package com.crio.starter.service;

import com.crio.starter.entity.Meme;
import com.crio.starter.exchange.MemeRequest;
import com.crio.starter.exchange.MemeResponse;
import com.crio.starter.exchange.UpdateMemeRequest;
import com.crio.starter.exception.DuplicateMemeException;
import com.crio.starter.exception.MemeNotFoundException;
import com.crio.starter.repository.MemeRepository;
import lombok.RequiredArgsConstructor;
import lombok. extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java. util.List;
import java. util.stream.Collectors;

/**
 * Service layer for Meme operations
 * Contains business logic for creating, retrieving, updating and deleting memes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemeService {
    
    private final MemeRepository memeRepository;
    private final ModelMapper modelMapper;
    
    /**
     * Create a new meme
     * Checks for duplicates before creating
     * Validates that fields are not empty
     * 
     * @param memeRequest Request containing meme data
     * @return ID of the created meme
     * @throws DuplicateMemeException if a meme with same name, url and caption already exists
     * @throws IllegalArgumentException if any field is empty or blank
     */
    @Transactional
    public String createMeme(MemeRequest memeRequest) {
        log.info("Creating new meme for user: {}", memeRequest.getName());
        
        // Additional validation for empty strings (trimmed)
        if (memeRequest.getName() == null || memeRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (memeRequest.getUrl() == null || memeRequest.getUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }
        if (memeRequest.getCaption() == null || memeRequest.getCaption().trim().isEmpty()) {
            throw new IllegalArgumentException("Caption cannot be empty");
        }
        
        // Check for duplicate meme
        boolean isDuplicate = memeRepository.existsByNameAndUrlAndCaption(
            memeRequest.getName(),
            memeRequest.getUrl(),
            memeRequest.getCaption()
        );
        
        if (isDuplicate) {
            log.warn("Duplicate meme detected for user: {}", memeRequest.getName());
            throw new DuplicateMemeException("A meme with the same name, url and caption already exists");
        }
        
        // Map request to entity
        Meme meme = modelMapper.map(memeRequest, Meme.class);
        meme.setCreatedAt(LocalDateTime.now());
        meme.setUpdatedAt(LocalDateTime.now());
        
        // Save to database
        Meme savedMeme = memeRepository.save(meme);
        log.info("Meme created successfully with ID: {}", savedMeme.getId());
        
        return savedMeme.getId();
    }
    
    /**
     * Get latest 100 memes ordered by creation date
     * Returns empty list if no memes exist
     * 
     * @return List of latest 100 memes (empty list if none exist)
     */
    @Transactional(readOnly = true)
    public List<MemeResponse> getLatestMemes() {
        log.info("Fetching latest 100 memes");
        
        List<Meme> memes = memeRepository.findTop100ByOrderByCreatedAtDesc();
        
        // Handle null or empty case
        if (memes == null || memes.isEmpty()) {
            log.info("No memes found in database, returning empty list");
            return new ArrayList<>();
        }
        
        log.info("Found {} memes", memes.size());
        
        return memes.stream()
                . map(meme -> modelMapper.map(meme, MemeResponse.class))
                .collect(Collectors. toList());
    }
    
    /**
     * Get a meme by its ID
     * 
     * @param id ID of the meme
     * @return Meme response object
     * @throws MemeNotFoundException if meme with given ID doesn't exist
     */
    @Transactional(readOnly = true)
    public MemeResponse getMemeById(String id) {
        log.info("Fetching meme with ID: {}", id);
        
        Meme meme = memeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Meme not found with ID: {}", id);
                    return new MemeNotFoundException("Meme not found with id: " + id);
                });
        
        log.info("Meme found:  {}", meme.getId());
        
        return modelMapper.map(meme, MemeResponse.class);
    }
    
    /**
     * Update an existing meme
     * Only url and caption can be updated
     * 
     * @param id ID of the meme to update
     * @param updateRequest Request containing fields to update
     * @return Updated meme response
     * @throws MemeNotFoundException if meme with given ID doesn't exist
     */
    @Transactional
    public MemeResponse updateMeme(String id, UpdateMemeRequest updateRequest) {
        log.info("Updating meme with ID: {}", id);
        
        Meme meme = memeRepository.findById(id)
                .orElseThrow(() -> {
                    log. error("Meme not found with ID: {}", id);
                    return new MemeNotFoundException("Meme not found with id: " + id);
                });
        
        // Update only provided fields
        if (updateRequest. getUrl() != null && !updateRequest.getUrl().isEmpty()) {
            meme.setUrl(updateRequest.getUrl());
        }
        
        if (updateRequest.getCaption() != null && !updateRequest.getCaption().isEmpty()) {
            meme.setCaption(updateRequest.getCaption());
        }
        
        meme.setUpdatedAt(LocalDateTime.now());
        
        Meme updatedMeme = memeRepository.save(meme);
        log.info("Meme updated successfully with ID: {}", updatedMeme.getId());
        
        return modelMapper. map(updatedMeme, MemeResponse.class);
    }
    
    /**
     * Delete a meme by its ID
     * 
     * @param id ID of the meme to delete
     * @throws MemeNotFoundException if meme with given ID doesn't exist
     */
    @Transactional
    public void deleteMeme(String id) {
        log.info("Deleting meme with ID:  {}", id);
        
        if (!memeRepository.existsById(id)) {
            log.error("Meme not found with ID: {}", id);
            throw new MemeNotFoundException("Meme not found with id: " + id);
        }
        
        memeRepository.deleteById(id);
        log.info("Meme deleted successfully with ID:  {}", id);
    }
}