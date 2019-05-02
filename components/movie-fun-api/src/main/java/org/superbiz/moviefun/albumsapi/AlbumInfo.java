package org.superbiz.moviefun.albumsapi;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class AlbumInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String artist;
    private String title;
    private int year;
    private int rating;

    public AlbumInfo() {
    }

    public AlbumInfo(String artist, String title, int year, int rating) {
        this.artist = artist;
        this.title = title;
        this.year = year;
        this.rating = rating;
    }


    public Long getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumInfo albumInfo = (AlbumInfo) o;

        if (getYear() != albumInfo.getYear()) return false;
        if (getRating() != albumInfo.getRating()) return false;
        if (!getId().equals(albumInfo.getId())) return false;
        if (!getArtist().equals(albumInfo.getArtist())) return false;
        return getTitle().equals(albumInfo.getTitle());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getArtist().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getYear();
        result = 31 * result + getRating();
        return result;
    }
}
