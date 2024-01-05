package com.eduardo.AdoptAPetAPI.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shelter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "number")
    private String number;

}
