package com.example.hanghaememo.dto;

import com.example.hanghaememo.entity.Comments;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String userName;
    private String contents;

    public CommentResponseDto(Comments comments)
    {
        this.userName = comments.getUsername();
        this.contents = comments.getContents();
    }
}
