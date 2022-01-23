package com.beers.repository.model;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name="Beers")
@Access(value= AccessType.FIELD)
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String manufacturer;
    private int graduation;
    private String name;
}
