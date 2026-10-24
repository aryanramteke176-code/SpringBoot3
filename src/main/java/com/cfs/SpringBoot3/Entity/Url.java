package com.cfs.SpringBoot3.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "url")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String path;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false, length = 2048)
    private String originalUrl;
}