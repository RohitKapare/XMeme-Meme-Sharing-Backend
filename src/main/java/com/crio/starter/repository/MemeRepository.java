package com.crio.starter.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.crio.starter.entity.Meme;

/**
 * Repository interface for Meme entity
 * Provides CRUD operations and custom queries for Meme data
 */
@Repository
public interface MemeRepository extends MongoRepository<Meme, String> {
    
    /**
     * Find meme by exact name, url and caption to check for duplicates
     * 
     * @param name Name of the meme poster
     * @param url URL of the meme image
     * @param caption Caption of the meme
     * @return Optional containing the meme if found
     */
    Optional<Meme> findByNameAndUrlAndCaption(String name, String url, String caption);
    
    /**
     * Find latest 100 memes ordered by creation date in descending order
     * 
     * @return List of latest 100 memes
     */
    @Query(sort = "{ 'createdAt' : -1 }")
    List<Meme> findTop100ByOrderByCreatedAtDesc();
    
    /**
     * Check if a meme exists with the given name, url and caption
     * 
     * @param name Name of the meme poster
     * @param url URL of the meme image
     * @param caption Caption of the meme
     * @return true if meme exists, false otherwise
     */
    boolean existsByNameAndUrlAndCaption(String name, String url, String caption);
}