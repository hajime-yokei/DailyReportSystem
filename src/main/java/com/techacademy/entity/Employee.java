package com.techacademy.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Transactional;

import lombok.Data;

@Data
@Entity
@Where(clause = "delete_flag = 0")
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(name="delete_flag")
    private Integer delete_flag;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @OneToOne(mappedBy="employee", cascade = CascadeType.ALL)
    private Authentication authentication;

    @PreRemove
    @Transactional
    private void preRemove() {
        if (authentication!=null) {
            authentication.setEmployee(null);
        }
    }

}
