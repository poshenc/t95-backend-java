package com.t95.t95backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "watchlists")
@Table(name = "watchlists")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Watchlist {

    @Id
    @SequenceGenerator(name = "watchlists_id_gen", sequenceName = "watchlists_id_seq", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "watchlists_id_gen")

    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Watchlist(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public Watchlist(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }
}
