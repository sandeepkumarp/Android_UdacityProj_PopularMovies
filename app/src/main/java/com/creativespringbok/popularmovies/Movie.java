package com.creativespringbok.popularmovies;

/**
 * Created by Sandeep on 08-11-2015.
 */
public class Movie {
    String itemTitle;
    int itemPoster;         //drawable ref id
    String itemSynopsis;
    String itemRating;
    String itemReleaseDate;

    public Movie(String itemTitle, int itemPoster, String itemSynopsis, String itemRating, String itemReleaseDate) {
        this.itemTitle = itemTitle;
        this.itemPoster = itemPoster;
        this.itemSynopsis = itemSynopsis;
        this.itemRating = itemRating;
        this.itemReleaseDate = itemReleaseDate;
    }
}
