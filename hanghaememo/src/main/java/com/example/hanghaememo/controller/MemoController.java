package com.example.hanghaememo.controller;

import com.example.hanghaememo.dto.MemoRequestDto;
import com.example.hanghaememo.dto.RegisterRequestDto;
import com.example.hanghaememo.security.UserDetailsImpl;
import com.example.hanghaememo.service.MemoService;
import com.example.hanghaememo.entity.Memo;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@RestController
//@NoArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/api/memo/")
public class MemoController {

    private final MemoService memoService;
    @GetMapping("/")
    public ModelAndView home()
    {
        return new ModelAndView("index");
    }

    @PostMapping("memos")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return memoService.createMemo(requestDto,userDetails.getUser());
    }

    @GetMapping("memos")
    public List<Memo> getMemos()
    {
        return memoService.getMemos();
    }
    @PutMapping("memos/{id}")
    public Long updateMemo(HttpServletRequest request, @PathVariable Long id,@RequestBody MemoRequestDto requestDto) {
        return memoService.update(request,id,requestDto);
    }

    @DeleteMapping("memos/{id}")
    public Long deleteMemo(HttpServletRequest request, @PathVariable Long id,@RequestBody MemoRequestDto requestDto) {
        return memoService.deleteMemo(request,id,requestDto);
    }
}
