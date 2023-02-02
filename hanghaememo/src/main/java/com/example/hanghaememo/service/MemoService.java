package com.example.hanghaememo.service;

import com.example.hanghaememo.dto.MemoRequestDto;
import com.example.hanghaememo.dto.MemoResponseDto;
import com.example.hanghaememo.dto.RegisterRequestDto;
import com.example.hanghaememo.entity.Memo;
import com.example.hanghaememo.entity.User;
import com.example.hanghaememo.repository.MemoRepository;
import com.example.hanghaememo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.hanghaememo.entity.UserRoleEnum;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.hanghaememo.jwt.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;


@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional
    public boolean validateTokenFromHeader(HttpServletRequest request)
    {
        String token = jwtUtil.resolveToken(request);
        if (token != null) {
            if(jwtUtil.validateToken(token)) {
                return true;
            }else {
                return false;
            }
        } else{
            return false;
        }
    }

    @Transactional
    public Memo createMemo(HttpServletRequest request, MemoRequestDto requestDto) {
        String token = jwtUtil.resolveToken(request);
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.getUserInfoFromToken(token);
                if (claims == null) {
                    throw new IllegalArgumentException("Token Error");
                }

                // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회

                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                        () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
                );

                Memo memo = new Memo(requestDto, user);
                memoRepository.save(memo);
                return memo;
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Memo> getMemos() {
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Long update(HttpServletRequest request, Long id, MemoRequestDto requestDto) {
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

            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<Memo> memoList = memoRepository.findAll();

            Memo memo = memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
            memo.update(requestDto);

//            if(userRoleEnum ==userRoleEnum.USER)
//            {
//                memoList = memoRepository.findAllById(user.getId()).orElseThrow(
//                        () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//                );
//            }
//            else
//            {
//                Memo memo = memoRepository.findall(user.getId()).orElseThrow(()-> new IllegalArgumentException("아이디가 존재하지 않습니다."));
//            }
//            if (user.getPwd().equals(requestDto.getPwd())) {
//                memo.update(requestDto);
//                System.out.println("업데이트 완료");
//            } else {
//                throw new IllegalArgumentException("패스워드가 틀립니다. 수정할 수 없습니다.");
//            }
            return memo.getId();
        } else {
            return null;
        }
    }

    @Transactional
    public Long deleteMemo(HttpServletRequest request, Long id, MemoRequestDto requestDto) {
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

            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            memoRepository.deleteById(id);
            System.out.println("삭제완료");

            return id;
        } else {
            return null;
        }
    }
}