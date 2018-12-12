package com.highschool.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Code {

    @Id
    @GeneratedValue
    private Long codeId;

    private String codeStr;

    private int codeType; // 0:active, 1: reset PW

    @Column(columnDefinition="DATETIME")
    private Date codeDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
