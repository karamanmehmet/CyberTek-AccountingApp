package com.cybertek.accounting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "companies")
@AllArgsConstructor
@NoArgsConstructor
public class Company extends BaseEntity{

    @Column(name = "title")
    private String title;

    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;

    @Column(name = "state")
    private String state;
    @Column(name = "zip")
    private String zip;

    @Column(name = "representative")
    private String representative;

    @Column(name = "email")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd.MM.yyyy hh:mm")
    private Date establishmentDate;
}
