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

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{
    private List<Song> songList;
    private Context context;
    private static OnItemClickListener mListener;

    public SongAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    public SongAdapter(Context context) {
        this.context = context;
    }

    public void setSongListList(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        Song song = songList.get(position);

        holder.imageViewFavorite.setImageResource(song.isFavorite()?R.drawable.ic_heart:
                R.drawable.ic_non_heart);
        holder.textViewNameSinger.setText(song.getArtist()+", "+song.getAlbum());
        holder.textViewNameSong.setText(song.getSongName());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFavorite;
        TextView textViewNameSong;
        TextView textViewNameSinger;
        TextView buttonUpdate;
        TextView buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewFavorite = itemView.findViewById(R.id.imageViewFavorite);
            textViewNameSong = itemView.findViewById(R.id.textViewNameSong);
            textViewNameSinger = itemView.findViewById(R.id.textViewNameSinger);
            buttonUpdate = itemView.findViewById(R.id.textViewUpdate);
            buttonDelete = itemView.findViewById(R.id.textViewDelete);

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onUpdateClick(position);
                        }
                    }
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
