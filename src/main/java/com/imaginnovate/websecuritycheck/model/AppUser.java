package com.imaginnovate.websecuritycheck.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_SEQ")
    @SequenceGenerator(name = "users_SEQ", sequenceName = "users_SEQ", allocationSize = 1)

    @Column(name="user_id")
    private Integer userId;

    @Column(name="user_name",unique = true)
    private String username;

    @Column(name="user_passwd")
    private String password;

    @Column(name="user_email")
    private String email;

    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(
            name="roles",
            joinColumns = @JoinColumn(name="user_id")
    )
    @Column(name="user_role")
    private Set<String> roles;


}
