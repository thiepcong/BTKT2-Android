package com.congthiep.musictablayout.db_helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.congthiep.musictablayout.model.Song;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "songs_database";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SONG_NAME = "song_name";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_ALBUM = "album";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_FAVORITE = "favorite";

    // Câu lệnh tạo bảng
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_SONGS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_SONG_NAME + " TEXT," +
                    COLUMN_ARTIST + " TEXT," +
                    COLUMN_ALBUM + " TEXT," +
                    COLUMN_GENRE + " TEXT," +
                    COLUMN_FAVORITE + " INTEGER)";

    // Câu lệnh xóa bảng
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_SONGS;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_NAME, song.getSongName());
        values.put(COLUMN_ARTIST, song.getArtist());
        values.put(COLUMN_ALBUM, song.getAlbum());
        values.put(COLUMN_GENRE, song.getGenre());
        values.put(COLUMN_FAVORITE, song.isFavorite() ? 1 : 0);
        db.insert(TABLE_SONGS, null, values);
        db.close();
    }

    public void updateSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_NAME, song.getSongName());
        values.put(COLUMN_ARTIST, song.getArtist());
        values.put(COLUMN_ALBUM, song.getAlbum());
        values.put(COLUMN_GENRE, song.getGenre());
        values.put(COLUMN_FAVORITE, song.isFavorite() ? 1 : 0);
        db.update(TABLE_SONGS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(song.getId())});
        db.close();
    }

    public void deleteSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SONGS, COLUMN_ID + " = ?", new String[]{String.valueOf(song.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public List<Song> getAllSongs() {
        List<Song> songList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SONGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                song.setSongName(cursor.getString(cursor.getColumnIndex(COLUMN_SONG_NAME)));
                song.setArtist(cursor.getString(cursor.getColumnIndex(COLUMN_ARTIST)));
                song.setAlbum(cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM)));
                song.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
                song.setFavorite(cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE)) == 1);
                songList.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songList;
    }

    @SuppressLint("Range")
    public Song getSongByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONGS, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        Song song = null;
        if (cursor != null && cursor.moveToFirst()) {
            song = new Song();
            song.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            song.setSongName(cursor.getString(cursor.getColumnIndex(COLUMN_SONG_NAME)));
            song.setArtist(cursor.getString(cursor.getColumnIndex(COLUMN_ARTIST)));
            song.setAlbum(cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM)));
            song.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
            song.setFavorite(cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE)) == 1);
            cursor.close();
        }
        db.close();
        return song;
    }

    @SuppressLint("Range")
    public List<Song> getAllSongsByName(String name) {
        List<Song> songList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONGS, null, COLUMN_SONG_NAME + " LIKE ?", new String[]{"%" + name + "%"}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                song.setSongName(cursor.getString(cursor.getColumnIndex(COLUMN_SONG_NAME)));
                song.setArtist(cursor.getString(cursor.getColumnIndex(COLUMN_ARTIST)));
                song.setAlbum(cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM)));
                song.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
                song.setFavorite(cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE)) == 1);
                songList.add(song);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return songList;
    }

    @SuppressLint("Range")
    public List<Song> getAllSongsByAlbum(String album) {
        List<Song> songList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONGS, null, COLUMN_ALBUM + " = ?", new String[]{album}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                song.setSongName(cursor.getString(cursor.getColumnIndex(COLUMN_SONG_NAME)));
                song.setArtist(cursor.getString(cursor.getColumnIndex(COLUMN_ARTIST)));
                song.setAlbum(cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM)));
                song.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
                song.setFavorite(cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE)) == 1);
                songList.add(song);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return songList;
    }
}
