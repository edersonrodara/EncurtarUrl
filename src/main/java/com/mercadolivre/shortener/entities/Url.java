package com.mercadolivre.shortener.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true)
    private String urlOriginal;

//    @Column(unique = true)
    private String urlShort;
    private long numberVisitants;
    private LocalDate createDate;
    private LocalDate updateDate;

}
