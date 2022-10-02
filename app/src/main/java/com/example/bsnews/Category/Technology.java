package com.example.bsnews.Category;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.example.bsnews.API.ApiService;
import com.example.bsnews.API.Server;
import com.example.bsnews.Adapter.NewsAdapter;
import com.example.bsnews.BuildConfig;
import com.example.bsnews.Entity.News;
import com.example.bsnews.Entity.ResponseNews;
import com.example.bsnews.MainActivity;
import com.example.bsnews.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class Technology extends AppCompatActivity {

private RecyclerView news;
private NewsAdapter adapter;
        List<News> list = new ArrayList<>();
final String category = "technology";
        ProgressDialog loading;
        ApiService api;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology);

        news = findViewById(R.id.news);
        api = Server.getApiService();
        adapter = new NewsAdapter(Technology.this, list);

        news.setHasFixedSize(true);
        news.setLayoutManager(new LinearLayoutManager(Technology.this));
        news.setAdapter(adapter);
        refresh();

        //membuat back button toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

public void refresh() {
        loading = new ProgressDialog(Technology.this);
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        showDialog();
        api.getListNews("id", category, BuildConfig.NEWS_API_TOKEN).enqueue(new Callback<ResponseNews>() {
@Override
public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
        if (response.isSuccessful()){
        hideDialog();
        list = response.body().getNewsList();
        news.setAdapter(new NewsAdapter(Technology.this, list));
        adapter.notifyDataSetChanged();
        } else {
        hideDialog();
        Toast.makeText(Technology.this, "Gagal mengambil data !", Toast.LENGTH_SHORT).show();
        }
        }

@Override
public void onFailure(Call<ResponseNews> call, Throwable t) {
        hideDialog();
        Toast.makeText(Technology.this, "Gagal menyambung ke internet !", Toast.LENGTH_SHORT).show();
        }
        });
        }

private void showDialog() {
        if (!loading.isShowing())
        loading.show();
        }

private void hideDialog() {
        if (loading.isShowing())
        loading.dismiss();
        }

@Override
public boolean onSupportNavigateUp() {
        Intent intent = new Intent(Technology.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return true;
        }

@Override
public void onBackPressed() {
        Intent intent = new Intent(Technology.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        }
