package com.example.hanghaememo.service;

import com.example.hanghaememo.dto.CommentRequestDto;
import com.example.hanghaememo.entity.Comments;
import com.example.hanghaememo.entity.Memo;
import com.example.hanghaememo.entity.User;
import com.example.hanghaememo.jwt.JwtUtil;
import com.example.hanghaememo.repository.CommentRepository;
import com.example.hanghaememo.repository.MemoRepository;
import com.example.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.hanghaememo.dto.CommentResponseDto;
import javax.servlet.http.HttpServletRequest;
@Service
@RequiredArgsConstructor
@Qualifier("PostCommentServiceImpl")
public class PostCommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final MemoRepository memoRepository;
    @Transactional
    public boolean validateTokenFromHeader(HttpServletRequest request)
    {
        String token = jwtUtil.resolveToken(request);
        if (token != null)
        {
            if(jwtUtil.validateToken(token))
                return true;
            else
                return false;
        }
        return false;
    }

    @Transactional // 확실한 성공,실패 보장
    public CommentResponseDto createComment(HttpServletRequest request, CommentRequestDto commentRequestDto,Long memoId)
    {
        if (validateTokenFromHeader(request)) {
            String token = jwtUtil.resolveToken(request);
            Claims claims = jwtUtil.getUserInfoFromToken(token);
            if(claims == null) {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Memo memo = memoRepository.findById(memoId).orElseThrow(()->new IllegalArgumentException("메모 ID가 존재하지 않습니다."));

            Comments comments = new Comments(commentRequestDto,memo);
            memo.setComments(comments);

            commentRepository.save(comments);
            return new CommentResponseDto(comments);

        } else {
            return null;
        }
    }
}
