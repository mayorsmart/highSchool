package com.highschool.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private  String firstName;

    private String lastName;

    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Role> roles;

    private boolean enabled;
}
