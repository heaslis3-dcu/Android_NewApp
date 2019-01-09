package id_16109759_hdsd.newfeedapi.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import id_16109759_hdsd.newfeedapi.R;

public class NewsDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener
{
    private String TAG = "NewsDetailActivity";
    private ImageView imageView;
    private TextView appbar_title, appbar_subtitle, date, time, title;
    private boolean isHideToolbarView = false;
    private FrameLayout date_behavior;
    private LinearLayout titleAppbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String mUrl, mImg, mTitle, mDate, mSource, mAuthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);

        Log.d(TAG, "onCreate RecyckerView");
    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i)
    {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }
}
