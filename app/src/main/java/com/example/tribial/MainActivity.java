package com.example.tribial;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tribial.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean keep = true;
    private final int duration = 1250;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // Sets the layout for the content view

        splashScreen.setKeepOnScreenCondition(new SplashScreen.KeepOnScreenCondition() {
            @Override
            public boolean shouldKeepOnScreen() {
                return keep;
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(runner, duration);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageAdapter adapter = getImageAdapter();

        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @NonNull
    private ImageAdapter getImageAdapter() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.bontoc);
        arrayList.add(R.drawable.ifugao_elders_in_tradtional_dress);
        arrayList.add(R.drawable.apayao_isneg);

        ImageAdapter adapter = new ImageAdapter(MainActivity.this, arrayList);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, Drawable integer) {
                startActivity(new Intent(MainActivity.this, ImageViewActivity.class).putExtra("image", (CharSequence) integer), ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, imageView, "image").toBundle());
            }
        });
        return adapter;
    }

    private final Runnable runner = new Runnable() {
        @Override
        public void run() {
            keep = false;
        }
    };

}