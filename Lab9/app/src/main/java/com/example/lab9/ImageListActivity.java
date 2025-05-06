package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageListActivity extends AppCompatActivity {

    private ListView listView;
    private ImageAdapter adapter;
    private List<ImageItem> imageItems;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        listView = findViewById(R.id.listView);

        imageItems = new ArrayList<>();

        imageItems.add(new ImageItem(
                "https://www.moldova.org/wp-content/uploads/2020/10/1_qDESOhHTWEKtBQNl5Hm2Pg-1250x885.jpg",
                "Unde poți învăța stând în fața gadget-ului",
                "https://www.moldova.org/topul-platformelor-de-e-learning-unde-poti-invata-stand-in-fata-gadget-ului/"));
        imageItems.add(new ImageItem(
                "https://cdn.elearningindustry.com/wp-content/uploads/2021/08/Top-5-Benefits-Of-eLearning-Education.png",
                "Top 4 Benefits Of eLearning Education",
                "https://elearningindustry.com/top-benefits-of-elearning-education"));
        imageItems.add(new ImageItem(
                "https://cdn.elearningindustry.com/wp-content/uploads/2019/07/top-6-eLearning-trends-of-2019.jpg",
                "Top 6 eLearning Trends Of 2019",
                "https://elearningindustry.com/current-elearning-trends-2019-future"));
        imageItems.add(new ImageItem(
                "https://ro.mindclass.eu/wp-content/uploads/2023/01/elearning.jpg",
                "Cum evaluezi performanța angajaților cu mindclass, platforma de e-learning? ",
                "https://ro.mindclass.eu/evaluare-platforma-elearning/"));
        imageItems.add(new ImageItem(
                "https://tribunainvatamantului.ro/wp-content/uploads/2020/08/020-eLearning-01-1536x1264.jpg",
                "eLearning. Scurt îndrumar de proiectare și utilizare",
                "http://tribunainvatamantului.ro/elearning-scurt-indrumar-de-proiectare-si-utilizare/"));

        adapter = new ImageAdapter(this, imageItems);
        listView.setAdapter(adapter);

        executorService = Executors.newFixedThreadPool(5);

        loadImages();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageItem selectedItem = imageItems.get(position);

                Intent intent = new Intent(ImageListActivity.this, WebViewActivity.class);
                intent.putExtra("webLink", selectedItem.getWebLink());
                startActivity(intent);
            }
        });
    }

    private void loadImages() {
        for (int i = 0; i < imageItems.size(); i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final ImageItem item = imageItems.get(index);
                        URL url = new URL(item.getImageUrl());
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(input);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                item.setBitmap(bitmap);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ImageListActivity.this,
                                        "Error loading image: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}