package com.cybertek.accounting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @NotNull
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    @NotNull
    private Company company;

    private boolean enabled;

}
