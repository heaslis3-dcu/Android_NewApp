package id_16109759_hdsd.newfeedapi.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
//import android.widget.Adapter;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id_16109759_hdsd.newfeedapi.adapters.MyAdapter;
import id_16109759_hdsd.newfeedapi.api_interface.ApiClient;
import id_16109759_hdsd.newfeedapi.api_interface.ApiService;
import id_16109759_hdsd.newfeedapi.pojo_models_objects.Article;
import id_16109759_hdsd.newfeedapi.pojo_models_objects.News;
import id_16109759_hdsd.newfeedapi.utils.Contract;


import id_16109759_hdsd.newfeedapi.R;
import id_16109759_hdsd.newfeedapi.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private MyAdapter adapter;
    private String TAG = MainActivity.class.getSimpleName();
    private TextView topHeadline;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        topHeadline = findViewById(R.id.headlines);
        recyclerView = findViewById(R.id.recyclerview);

        /**
         * Date - 11/01/2019
         * Ref - horizontal recyclerview
         * https://stackoverflow.com/questions/28460300/
         * how-to-build-a-horizontal-listview-with-recyclerview
         */
        layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        /**
         * SnapHelper - Mimics behaviour of ViewPager - scrolling one RecyclerView at a duration
         * Date: 11/01/2018
         * Ref:
         * https://stackoverflow.com/questions/43024818/
         * recyclerview-horizontal-scroll-how-to-display-one-item-at-a-scroll-like-pager/44457668
         */
        SnapHelper snapHelper = new PagerSnapHelper();
        recyclerView.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(recyclerView);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh("");

        errorLayout = findViewById(R.id.errorLayout);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);

    }

    public void LoadJson(final String keyword){

//        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);

        ApiService apiInterface = ApiClient.getApiClient().create(ApiService.class);

        String country = Utils.getCountry();
        String language = Utils.getLanguage();

        Call<News> call;

        if (keyword.length() > 0 ){
            call = apiInterface.getEverything(keyword, language, "publishedAt", Contract.API_KEY);
        } else {
            call = apiInterface.getNews(country, Contract.API_KEY);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    adapter = new MyAdapter(articles, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    initListener();

                    topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);


                } else {

                    topHeadline.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }

                    showErrorMessage(
                            R.drawable.ic_no_result,
                            "No Result",
                            "Please Try Again!\n"+
                                    errorCode);

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                topHeadline.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(
                        R.drawable.oops,
                        "Oops..",
                        "Network failure, Please Try Again\n"+
                                t.toString());
            }
        });

    }



    private void initListener(){

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.cardImg);
                Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",  article.getUrlToImage());
                intent.putExtra("date",  article.getPublishedAt());
                intent.putExtra("source",  article.getSource().getName());
                intent.putExtra("author",  article.getAuthor());

                Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this,
                        pair
                );


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, optionsCompat.toBundle());
                }else {
                    startActivity(intent);
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    onLoadingSwipeRefresh(query);
                }
                else {
                    Toast.makeText(MainActivity.this, "Type more than two letters!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public void onRefresh() {
        LoadJson("");
    }

    private void onLoadingSwipeRefresh(final String keyword){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson(keyword);
                    }
                }
        );

    }

    private void showErrorMessage(int imageView, String title, String message){

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadingSwipeRefresh("");
            }
        });

    }


}




//public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
//{
//    private String TAG = "onCreate RecyclerView";
//
//    //    private static final String URL_DATA = "https://newsapi.org/v2/";
//    private RecyclerView recyclerView;
//    private RecyclerView.LayoutManager layoutManager;
//    private Adapter adapter;
//    private RelativeLayout errorLayout;
//    private TextView headlines;
//
//    //    private RecyclerView.Adapter adapter;
//    private List<Article> articles = new ArrayList<>();
//
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private TextView headline;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        //   swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
//
//
//        headlines = findViewById(R.id.headlines);
//        recyclerView = findViewById(R.id.recyclerview);
//        layoutManager = new LinearLayoutManager(MainActivity.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setNestedScrollingEnabled(false);
//
//        articles = new ArrayList<>();
//       // loadRecyclerViewData();
//        //Each item of the recyclerView is a fixed size
//       // recyclerView.setHasFixedSize(true);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////        Log.d(TAG, "onCreate RecyclerView");
//
////        onLoadingSwipeRefresh("");
//    }
//
//    public void LoadJson(final String keyword){
//
//        errorLayout.setVisibility(View.GONE);
//        swipeRefreshLayout.setRefreshing(true);
//
//        ApiService apiInterface = ApiClient.getApiClient().create(ApiService.class);
//
//        String country = Utils.getCountry();
//        String language = Utils.getLanguage();
//
//        Call<News> call;
//
//        if (keyword.length() > 0 ){
////            call = ApiService.getEverything(keyword, language, "publishedAt", Contract.API_KEY);
//            call = ApiService.getEverything();
//        } else {
//            call = apiInterface.getNews(country, Contract.API_KEY);
//        }
//
//        call.enqueue(new Callback<News>() {
//            @Override
//            public void onResponse(Call<News> call, Response<News> response) {
//                if (response.isSuccessful() && response.body().getArticle() != null){
//
//                    if (!articles.isEmpty()){
//                        articles.clear();
//                    }
//
//                    articles = response.body().getArticle();
//                    adapter = new MyAdapter(articles, MainActivity.this);
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//
//                    initListener();
//
//                    headline.setVisibility(View.VISIBLE);
//                    swipeRefreshLayout.setRefreshing(false);
//
//
//                } else {
//
//                    headline.setVisibility(View.INVISIBLE);
//                    swipeRefreshLayout.setRefreshing(false);
//
//                    String errorCode;
//                    switch (response.code()) {
//                        case 404:
//                            errorCode = "404 not found";
//                            break;
//                        case 500:
//                            errorCode = "500 server broken";
//                            break;
//                        default:
//                            errorCode = "unknown error";
//                            break;
//                    }
//
//                    showErrorMessage(
//                            R.drawable.no_result,
//                            "No Result",
//                            "Please Try Again!\n"+
//                                    errorCode);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<News> call, Throwable t) {
//                topHeadline.setVisibility(View.INVISIBLE);
//                swipeRefreshLayout.setRefreshing(false);
//                showErrorMessage(
//                        R.drawable.oops,
//                        "Oops..",
//                        "Network failure, Please Try Again\n"+
//                                t.toString());
//            }
//        });
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setQueryHint("Search Latest News...");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (query.length() > 2){
//                    onLoadingSwipeRefresh(query);
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Type more than two letters!", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        searchMenuItem.getIcon().setVisible(false, false);
//
//        return true;
//    }
//
//    private void onLoadingSwipeRefresh(final String keyword){
//
//        swipeRefreshLayout.post(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        LoadJson(keyword);
//                    }
//                }
//        );
//
//    }
//
//    @Override
//    public void onRefresh()
//    {
//
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture)
//    {
//
//    }
//}

