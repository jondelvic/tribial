package com.example.tribial;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

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

        // Audio Visual Section
        Button watchVideo = findViewById(R.id.watch_video_btn);

        watchVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlayerActivity.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Map functionalities

        // Get a handle to the fragment and register the callback
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(17.080997755592357, 120.90573450430661)).title("Hanging Coffins of Sagada"));
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
            public void onClick(ImageView imageView, Drawable img) {
                startActivity(new Intent(MainActivity.this, BontocDetailedActivity.class).putExtra("image", (CharSequence) img),
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, imageView, "image").toBundle());
            }

            @Override
            public void onClick(ImageView imageView, Drawable img, int position) {

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