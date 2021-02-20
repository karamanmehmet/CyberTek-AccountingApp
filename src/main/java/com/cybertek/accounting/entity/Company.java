package com.cybertek.accounting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company extends BaseEntity{

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "address1", nullable = false)
    private String address1;
    @Column(name = "address2")
    private String address2;

    @Column(name = "state", nullable = false)
    private String state;
    @Column(name = "zip", nullable = false)
    private String zip;

    @Column(name = "representative",nullable = false)
    private String representative;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "establishment_date", nullable = false)
    private LocalDate establishmentDate;

    private boolean enabled;
}
