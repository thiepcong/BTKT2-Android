package com.congthiep.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.congthiep.musicapp.fragment.InformationFragment;
import com.congthiep.musicapp.fragment.ListFragment;
import com.congthiep.musicapp.fragment.SearchAndStatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton addSongButton;
    public final static int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        addSongButton = findViewById(R.id.add_song_button);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.ic_home) {
                selectedFragment = new ListFragment();
            } else if (itemId == R.id.ic_detail) {
                selectedFragment = new InformationFragment();
            } else if (itemId == R.id.ic_search) {
                selectedFragment = new SearchAndStatisticsFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }

            return true;
        });

        // Mặc định chọn Fragment danh sách khi mở ứng dụng
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ListFragment()).commit();

        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSongActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                if(data==null){
                    Toast.makeText(getApplicationContext(),"Thêm bài hát thất bại",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Thêm bài hát thành công",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

