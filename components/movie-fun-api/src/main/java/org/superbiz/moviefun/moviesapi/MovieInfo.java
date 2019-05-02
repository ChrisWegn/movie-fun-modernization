package org.superbiz.moviefun.moviesapi;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MovieInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String director;
    private String title;
    private int year;
    private String genre;
    private int rating;

    public long getId() {
        return id;
    }

    public String getDirector() {
        return director;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieInfo movieInfo = (MovieInfo) o;

        if (getId() != movieInfo.getId()) return false;
        if (getYear() != movieInfo.getYear()) return false;
        if (getRating() != movieInfo.getRating()) return false;
        if (!getDirector().equals(movieInfo.getDirector())) return false;
        if (!getTitle().equals(movieInfo.getTitle())) return false;
        return getGenre().equals(movieInfo.getGenre());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getDirector().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getYear();
        result = 31 * result + getGenre().hashCode();
        result = 31 * result + getRating();
        return result;
    }

    public MovieInfo() {
    }

    public MovieInfo(String title, String director, String genre, int rating, int year) {
        this.director = director;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public MovieInfo(String director, String title, int year) {
        this.director = director;
        this.title = title;
        this.year = year;
    }
}