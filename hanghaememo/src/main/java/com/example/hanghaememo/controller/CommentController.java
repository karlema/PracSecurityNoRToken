package com.example.hanghaememo.controller;

import com.example.hanghaememo.dto.CommentRequestDto;
import com.example.hanghaememo.dto.CommentResponseDto;
import com.example.hanghaememo.jwt.JwtUtil;
import com.example.hanghaememo.repository.CommentRepository;
import com.example.hanghaememo.repository.MemoRepository;
import com.example.hanghaememo.repository.UserRepository;
import com.example.hanghaememo.service.CommentService;
import com.example.hanghaememo.service.PostCommentServiceImpl;
import com.example.hanghaememo.service.StoryCommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostCollectionRecreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
@RestController
//@RequiredArgsConstructor //private final 선언해줘야
public class CommentController {
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;
//    @Qualifier("StoryCommentServiceImpl")

    private final CommentService storyCommentService;
    private final CommentService postCommentService;

    public CommentController(CommentRepository commentRepository,
                             JwtUtil jwtUtil,
                             UserRepository userRepository,
                             MemoRepository memoRepository,
                             @Qualifier("StoryCommentServiceImpl")
                             CommentService storyCommentService,
                             @Qualifier("PostCommentServiceImpl")
                             CommentService postCommentService) {
        this.commentRepository = commentRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.memoRepository = memoRepository;
        this.storyCommentService = storyCommentService;
        this.postCommentService = postCommentService;
    }
@PreDestroy
    public void test()
    {
        System.out.println(this.storyCommentService.getClass());
        System.out.println(this.postCommentService.getClass());
//        System.out.println(this.storyCommentService.getClass());
        System.out.println("--------------------------------------------------------------------");
    }

    @PostMapping("/api/comments/{memoid}")
    public CommentResponseDto createComment(HttpServletRequest request, @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long memoid)
    {
        return storyCommentService.createComment(request,commentRequestDto,memoid);
    }

//    public String createComment(HttpServletRequest request, @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long memoid)
//    {
//
//        commentService.createComment(request,commentRequestDto,memoid);
//        return "댓글 성공";
//    }
}
