package com.example.hanghaememo.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.hanghaememo.dto.MemoRequestDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor

public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String pwd;


    // User ID는 하나만 가질수 있다.
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @JsonManagedReference
    @OneToMany(fetch =FetchType.LAZY,mappedBy = "memo",cascade = CascadeType.ALL)
//    @JoinColumn(name = "Comment_id", nullable = false)
    private List<Comments> comments = new ArrayList<>();

    public Memo(MemoRequestDto requestDto,User user) {
        this.username = requestDto.getUserName();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.pwd = requestDto.getPwd();
        this.user = user;
    }

    public void update(MemoRequestDto requestDto) {
        this.username = requestDto.getUserName();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.pwd = requestDto.getPwd();
    }

    public void setComments(Comments comments)
    {
        this.comments.add(comments);
    }
}