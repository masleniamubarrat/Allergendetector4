package com.example.allergendetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.example.allergendetector.PopularBlog.Blog;


public class PopularBlog extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BlogAdapter blogAdapter;
    private List<Blog> blogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_blog);

        recyclerView=findViewById(R.id.blog_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        blogList = new ArrayList<>();
        blogAdapter = new BlogAdapter(blogList);
        recyclerView.setAdapter(blogAdapter);

        fetchData();
    }

    private void fetchData(){
        //a HTTP request that willl retrieve blog posts from Google
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.googleapis.com/customsearch/v1?q=allergy+from+foods|lactose+in+foods|gluten+in+foods|lactose+intolerance|gluten+intolerance&key=AIzaSyAlyy0Wggva329nTM6Ivd0sA0f3-nMvdXg")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PopularBlog.this,"Failed to fetch data",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               final String jsonData = response.body().string();

               //the JSON data wil be parsed using Gson library
                Gson  gson = new Gson();
                BlogResponse blogResponse = gson.fromJson(jsonData, BlogResponse.class);

                if(blogResponse != null && blogResponse.items !=null){
                    runOnUiThread(new Runnable() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void run() {
                            blogList.clear();
                            blogList.addAll(blogResponse.items);
                            blogAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

    }

    static class Blog {
        private String headline;
        private String link;

        public String getHeadline(){
            return headline;
        }
        public void setHeadline(String headline){
            this.headline = headline;
        }
        public String getLink(){
            return link;
        }
        public void setLink(String link){
            this.link = link;
        }
    }

    static class BlogResponse{
        private List<Blog> items;

        //getters and setters for the fields are created

        public  List<Blog> getItems(){
            return items;
        }
        public void setItems(List<Blog> items){
            this.items = items;
        }
    }
}