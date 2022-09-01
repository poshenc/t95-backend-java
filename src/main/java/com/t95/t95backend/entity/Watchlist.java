package com.t95.t95backend.entity;

import javax.persistence.*;

@Entity(name = "Watchlist")
@Table(name = "Watchlist")
public class Watchlist {

    @Id
    @SequenceGenerator(name = "watchlist_sequence", sequenceName = "watchlist_sequence", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "watchlist_sequence")

    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "watchlist_name", nullable = false, columnDefinition = "TEXT")
    private String watchlistName;

    @Column(name = "watchlist", nullable = true)
    private String watchlist;

    public Watchlist() {
    }

    public Watchlist(Integer userId, String watchlistName, String watchlist) {
        this.userId = userId;
        this.watchlistName = watchlistName;
        this.watchlist = watchlist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWatchlistName() {
        return watchlistName;
    }

    public void setWatchlistName(String watchlistName) {
        this.watchlistName = watchlistName;
    }

    public String getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(String watchlist) {
        this.watchlist = watchlist;
    }

    @Override
    public String toString() {
        return "Watchlist{" +
                "id=" + id +
                ", userId=" + userId +
                ", watchlistName='" + watchlistName + '\'' +
                ", watchlist='" + watchlist + '\'' +
                '}';
    }
}
