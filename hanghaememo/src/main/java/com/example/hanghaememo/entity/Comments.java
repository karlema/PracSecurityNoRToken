package com.example.hanghaememo.entity;

import com.example.hanghaememo.dto.CommentRequestDto;
import com.example.hanghaememo.dto.MemoRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comments extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;
    @Column
    private String contents;
    @Column
    private String pwd;

    @JsonBackReference
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "memo_id", nullable = false)
    private Memo memo;


    public Comments(CommentRequestDto commentRequestDto,Memo memo)
    {
        this.username = commentRequestDto.getUserName();
        this.contents = commentRequestDto.getContents();
        this.pwd = commentRequestDto.getPwd();
        this.memo = memo;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.username = commentRequestDto.getUserName();
        this.contents = commentRequestDto.getContents();
        this.pwd = commentRequestDto.getPwd();

    }

}
