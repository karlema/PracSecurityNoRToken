package com.example.hanghaememo.service;

import com.example.hanghaememo.dto.CommentRequestDto;
import com.example.hanghaememo.dto.CommentResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {

    boolean validateTokenFromHeader(HttpServletRequest request);

    CommentResponseDto createComment(HttpServletRequest request, CommentRequestDto commentRequestDto, Long memoId);

}
