package com.congthiep.musictablayout.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.congthiep.musictablayout.EditSongActivity;
import com.congthiep.musictablayout.R;
import com.congthiep.musictablayout.adapter.OnItemClickListener;
import com.congthiep.musictablayout.adapter.SongAdapter;
import com.congthiep.musictablayout.db_helper.DatabaseHelper;
import com.congthiep.musictablayout.model.Song;

import java.util.List;

public class ListFragment extends Fragment {

    public ListFragment() {

    }
    public final static int REQUEST_CODE = 1001;

    private List<Song> songList;
    private RecyclerView recyclerViewSongList;
    private SongAdapter songAdapter;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dbHelper = new DatabaseHelper(getContext());
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        songList = dbHelper.getAllSongs();
        recyclerViewSongList = view.findViewById(R.id.songList);
        recyclerViewSongList.setLayoutManager(new LinearLayoutManager(getContext()));
        songAdapter = new SongAdapter(getContext(),songList);
        recyclerViewSongList.setAdapter(songAdapter);
        songAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onUpdateClick(int position) {
                Intent intent = new Intent(getActivity(), EditSongActivity.class);
                Song song = songList.get(position);
                intent.putExtra("song",song);
                startActivityForResult(intent,REQUEST_CODE);
            }

            @Override
            public void onDeleteClick(int position) {
                try{
                    Song song = songList.get(position);
                    dbHelper.deleteSong(song);
                    songList.remove(position);
                    songAdapter.setSongListList(songList);
                    recyclerViewSongList.setAdapter(songAdapter);
                    Toast.makeText(getContext(),"Xoá bài hát thành công",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getContext(),"Xoá bài hát thất bại",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                if(data==null){
                    Toast.makeText(getContext(),"Sửa bài hát thất bại",Toast.LENGTH_LONG).show();
                }else{
                    songList = dbHelper.getAllSongs();
                    songAdapter.setSongListList(songList);
                    recyclerViewSongList.setAdapter(songAdapter);
                    Toast.makeText(getContext(),"Sửa bài hát thành công",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
