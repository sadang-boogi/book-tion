package com.rebook.review.repository;

import com.rebook.review.domain.ReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("""
            SELECT re FROM ReviewEntity re 
            LEFT JOIN FETCH re.user  
            WHERE re.book.id = :bookId 
            ORDER BY re.updatedAt DESC
            """)
    Slice<ReviewEntity> findAllBy(@Param("bookId") Long bookId, Pageable pageable);
}
