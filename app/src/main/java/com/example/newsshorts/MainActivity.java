package com.example.newsshorts;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsshorts.api.ApiClient;
import com.example.newsshorts.models.Article;
import com.example.newsshorts.models.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final String API_KEY = "40ac15f98854419db03f862aaff971ee";
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Article> mArticles = new ArrayList<>();
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView2 = findViewById(R.id.recyclerview2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
      //  mRecyclerView2.setLayoutManager(layoutManager);



        retrieveJson("");
       // retrieveJson2(bitcoin,API_KEY);


    }

    public void retrieveJson(final String keyword){


        Call<News> call;
        String country = Utils.getCountry();
        String language = Utils.getLungeage();


        if (keyword.length() >= 0){
            call = ApiClient.getInstance().getApi().getNewsTopheadline(country, API_KEY);
        }
        else {
            call = ApiClient.getInstance().getApi().getNewssearch(keyword,language,"publishedAt",API_KEY);
        }

        call.enqueue(new Callback<News>() {



            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){
                    mArticles.clear();
                    mArticles = response.body().getArticles();
                    adapter = new Adapter(mArticles, MainActivity.this);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return  country.toLowerCase();
    }

    public void retrieveJson2(String q, String apiKey){

        Call<News> call = ApiClient.getInstance().getApi().getNewsTopheadline(q, apiKey);
        call.enqueue(new Callback<News>() {

            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){
                    mArticles.clear();
                    mArticles = response.body().getArticles();
                    adapter = new Adapter(mArticles, MainActivity.this);
                    mRecyclerView2.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    public String getq(){
//        Locale locale = Locale.getDefault();
//        String q = locale.getExtension()
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchmenuitem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length()>2){
                    retrieveJson(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                retrieveJson(newText);
                return false;
            }
        });

        searchmenuitem.getIcon().setVisible(false,false);

        return true;
    }
}