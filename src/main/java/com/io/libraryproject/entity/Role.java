package com.io.libraryproject.entity;

import com.io.libraryproject.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;


    @Override
    public String toString() {
        return "Role{" +
                " roleType=" + roleType +
                '}';
    }
}
