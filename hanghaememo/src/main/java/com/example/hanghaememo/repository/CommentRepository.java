package com.example.hanghaememo.repository;

import com.example.hanghaememo.entity.Comments;
import com.example.hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllById(Long id);

    List<Comments> findAllByOrderByModifiedAtDesc();
}
