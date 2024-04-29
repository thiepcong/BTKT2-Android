package com.congthiep.musicapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.congthiep.musicapp.R;
import com.congthiep.musicapp.adapter.SongAdapter;
import com.congthiep.musicapp.adapter.SongSearchAdapter;
import com.congthiep.musicapp.db_helper.DatabaseHelper;
import com.congthiep.musicapp.model.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchAndStatisticsFragment extends Fragment {

    private List<Song> songList;
    private RecyclerView recyclerViewSongList;
    private SongSearchAdapter songSearchAdapter;
    private DatabaseHelper dbHelper;
    private Button btnSearch;
    private EditText searchText;
    private Spinner albumFilter;
    private TextView typeDetail;
    boolean checked = false;
    public SearchAndStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dbHelper = new DatabaseHelper(getContext());
        return inflater.inflate(R.layout.fragment_search_and_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        songList = dbHelper.getAllSongs();

        btnSearch = view.findViewById(R.id.btnSearch);
        searchText = view.findViewById(R.id.searchText);
        albumFilter = view.findViewById(R.id.albumFilter);
        typeDetail = view.findViewById(R.id.typeQuantity);

        recyclerViewSongList = view.findViewById(R.id.songList);
        recyclerViewSongList.setLayoutManager(new LinearLayoutManager(getContext()));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textF = searchText.getText().toString();
                if(textF.isEmpty()){
                    Toast.makeText(getContext(),"Vui lòng điền thông tin tìm kiếm",Toast.LENGTH_LONG).show();
                    return;
                }
                searchText.setText("");
                songList = dbHelper.getAllSongs();
                songList = songList.stream()
                                .filter((song -> song.getSongName().contains(textF)||song.getArtist().contains(textF)))
                                .collect(Collectors.toList());
                songSearchAdapter = new SongSearchAdapter(getContext(),songList);
                recyclerViewSongList.setAdapter(songSearchAdapter);
                typeDetail.setText(handleDetailType(songList));
            }
        });
        albumFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String album =(String) adapterView.getItemAtPosition(i);
                if(!checked) {
                    checked = true;
                    return;
                }
                List<Song> convertList = new ArrayList<>();
                if(i==0){
                    convertList = songList;
                }else{
                    convertList = songList.stream()
                            .filter((song -> song.getAlbum().contains(album)))
                            .collect(Collectors.toList());
                }

                songSearchAdapter = new SongSearchAdapter(getContext(),convertList);
                recyclerViewSongList.setAdapter(songSearchAdapter);
                typeDetail.setText(handleDetailType(songList));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    String handleDetailType(List<Song> songs){
        String tmp = "";
        Map<String, Integer> typeCountMap = new HashMap<>();
        for (Song song : songs) {
            String type = song.getGenre();
            typeCountMap.put(type, typeCountMap.getOrDefault(type, 0) + 1);
        }
        List<Map.Entry<String, Integer>> sortedTypeList = new ArrayList<>(typeCountMap.entrySet());
        sortedTypeList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        for (Map.Entry<String, Integer> entry : sortedTypeList) {
            tmp+="Type: " + entry.getKey() + ", Số lượng: " + entry.getValue()+"\n";
        }
        if(!tmp.isEmpty()) tmp = "Số lượng phần tử của mỗi loại (sắp xếp giảm dần):\n"+tmp;
        return tmp;
    }
}

