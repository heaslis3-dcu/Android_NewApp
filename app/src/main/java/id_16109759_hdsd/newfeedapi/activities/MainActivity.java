package id_16109759_hdsd.newfeedapi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id_16109759_hdsd.newfeedapi.pojo_models_objects.Article;
import id_16109759_hdsd.newfeedapi.utils.Contract;


import id_16109759_hdsd.newfeedapi.R;

public class MainActivity extends AppCompatActivity
{
    private static final String URL_DATA = "https://newsapi.org/v2/";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        //Each item of the recyclerView is a fixed size
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        articles = new ArrayList<>();
//        for(int i =0; i<10; i++){
//            Article article = new Article(
//              "BBC",
//              "Maria keys",
//              "Beyond the mind!",
//              "This is the description Ipsum loren",
//              "https://pixabay.com/",
//                    "https://pixabay.com/en/hamburg-speicherstadt-channel-3846525/",
//                    "Next.com"
//
//            );
//        }

    }
}
