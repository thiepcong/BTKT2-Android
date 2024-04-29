package com.congthiep.musicapp.model;

import java.io.Serializable;

public class Song implements Serializable {
    private int id;
    private String songName;
    private String artist;
    private String album;
    private String genre;
    private boolean favorite;
    public Song() {
    }

    public Song(int id, String songName, String artist, String album, String genre, boolean favorite) {
        this.id = id;
        this.songName = songName;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
