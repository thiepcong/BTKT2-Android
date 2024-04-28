package com.congthiep.musicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.congthiep.musicapp.R;
import com.congthiep.musicapp.model.Song;

import java.util.List;

public class SongSearchAdapter extends RecyclerView.Adapter<SongSearchAdapter.ViewHolder> {
    private List<Song> songList;
    private Context context;

    public SongSearchAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    public SongSearchAdapter(Context context) {
        this.context = context;
    }

    public void setSongListList(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song_search, parent, false);
        return new SongSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongSearchAdapter.ViewHolder holder, int position) {
        Song song = songList.get(position);

        holder.imageViewFavorite.setImageResource(song.isFavorite()?R.drawable.ic_heart:
                R.drawable.ic_non_heart);
        holder.textViewNameSinger.setText(song.getArtist());
        holder.textViewNameSong.setText(song.getSongName());
        holder.textViewNameAlbum.setText(song.getAlbum());
        holder.textViewGenre.setText(song.getGenre());
    }


    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFavorite;
        TextView textViewNameSong;
        TextView textViewNameSinger;
        TextView textViewNameAlbum;
        TextView textViewGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewFavorite = itemView.findViewById(R.id.imageViewFavorite);
            textViewNameSong = itemView.findViewById(R.id.textViewNameSong);
            textViewNameSinger = itemView.findViewById(R.id.textViewNameSinger);
            textViewGenre = itemView.findViewById(R.id.textViewGenre);
            textViewNameAlbum = itemView.findViewById(R.id.textViewAlbum);
        }
    }
}
