package com.congthiep.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.congthiep.musicapp.db_helper.DatabaseHelper;
import com.congthiep.musicapp.model.Song;

import java.util.Arrays;

public class EditSongActivity extends AppCompatActivity {
    private EditText songNameInput;
    private EditText singerInput;
    private Spinner albumSpinner;
    private Spinner genreSpinner;
    private CheckBox favoriteCheckbox;
    private Button addSongButton;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_detail);

        dbHelper = new DatabaseHelper(getApplicationContext());

        songNameInput = findViewById(R.id.song_name_input);
        singerInput = findViewById(R.id.singer_input);
        albumSpinner = findViewById(R.id.album_input);
        genreSpinner = findViewById(R.id.genre_spinner);
        favoriteCheckbox = findViewById(R.id.favorite_checkbox);
        addSongButton = findViewById(R.id.add_song_button);
        addSongButton.setText("SỬA BÀI HÁT");
        Intent intent = getIntent();
        Song originSong =(Song) intent.getSerializableExtra("song");
        songNameInput.setText(originSong.getSongName());
        singerInput.setText(originSong.getArtist());
        String[] albumsArray = getResources().getStringArray(R.array.albums_array);
        String[] typesArray = getResources().getStringArray(R.array.types_array);
        try{
            albumSpinner.setSelection(Arrays.asList(albumsArray).indexOf(originSong.getAlbum()));
            genreSpinner.setSelection(Arrays.asList(typesArray).indexOf(originSong.getGenre()));
        }catch (Exception e){
            e.printStackTrace();
        }
        favoriteCheckbox.setChecked(originSong.isFavorite());

        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String songName = songNameInput.getText().toString();
                String singer = singerInput.getText().toString();
                String album = albumSpinner.getSelectedItem().toString();
                String genre = genreSpinner.getSelectedItem().toString();
                boolean favorite = favoriteCheckbox.isChecked();
                if(songName.isEmpty()||singer.isEmpty()||album.isEmpty()||genre.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập đủ các trường",Toast.LENGTH_LONG).show();
                    return;
                }
                dbHelper.updateSong(new Song(originSong.getId(),songName,singer,album,genre,favorite));
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
