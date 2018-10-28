package com.highschool.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name= "role")
public class Role {

    @Id
    @GeneratedValue
    private Long roleId;

    private String authority;

    private String email;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


}
