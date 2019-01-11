package id_16109759_hdsd.newfeedapi.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import id_16109759_hdsd.newfeedapi.R;
import id_16109759_hdsd.newfeedapi.pojo_models_objects.Article;
import id_16109759_hdsd.newfeedapi.utils.Utils;

//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{

    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;

    //Constructor to initialise List and Context objects
    public MyAdapter(List<Article> articles, Context context)
    {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
       // View view = LayoutInflater.from(viewGroup.getContext())
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item, viewGroup, false);

        //Constructor is found below inner class -
        // MyViewHolder(View itemView, OnItemClickListener onItemClickListener)
        return  new ViewHolder(view, onItemClickListener);
    }

    //Implemented Method
    //Method used to bind the data from the class ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final ViewHolder hold = holder;
        Article article = articles.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(article.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        hold.proBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        hold.proBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);


//        holder.author.setText(article.getAuthor());
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        holder.source.setText(article.getAuthor());
        holder.time.setText(Utils.DateToTimeFormat(article.getPublishedAt()));
    }

    //Implemented Method
    @Override
    public int getItemCount()
    {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    //Implemented Method
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Define 'list.xml' objects in Viewholder class
        public ImageView imageView;
        public TextView author, title, description, source, time;
        public ProgressBar proBar;

        //Viewholder Constructor
        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardImg);
            proBar = itemView.findViewById(R.id.progress_load_photo);
//            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desc);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.duration);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }

    }

//    private List<Article> articles;
//    private Context context;
//    public AdapterView.OnItemClickListener onItemClickListener;
//
//    //Member Variable - to link id's in item.xml
//    ImageView mImageView;
//    TextView mAuthor, mTitle, mDescription, mSource, mTime;
//
//    //Constructor -  Auto Generated
//    public MyAdapter(List<Article> articles, Context context)
//    {
//        this.articles = articles;
//        this.context = context;
//    }
//
//    // ViewHolder required when extending RecyclerView.Adapter......
//    @NonNull
//    @Override
//    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
//    {
//        View view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false)
//        return new ViewHolder(view, onItemClickListener);
//    }
//    // onBindViewHolder required when extending RecyclerView.Adapter......
//    @Override
//    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int position)
//    {
//        final ViewHolder holder = viewHolder;
//        Article model = articles.get(position);
//
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(Utils.getRandomDrawbleColor());
//        requestOptions.error(Utils.getRandomDrawbleColor());
//        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
//        requestOptions.centerCrop();
//
//        Glide.with(context)
//                .load(model.getUrlToImage())
//                .apply(requestOptions)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        holder.progressBar.setVisibility(View.GONE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        holder.progressBar.setVisibility(View.GONE);
//                        return false;
//                    }
//                });
//                .transition(DrawableTransitionOptions.withCrossFade())
//
//                .into(holder.imageView);
//
//        holder.mTitle.setText(model.getTitle());
//        holder.desc.setText(model.getDescription());
//        holder.source.setText(model.getSource().getName());
//        holder.duration.setText(" \u2022 " + Utils.DateToTimeFormat(model.getPublishedAt()));
//        holder.published_ad.setText(Utils.DateFormat(model.getPublishedAt()));
//        holder.author.setText(model.getAuthor());
//
//    }
//    // getItemCount required when extending RecyclerView.Adapter......
//    @Override
//    public int getItemCount()
//    {
//        return 0;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
//    {
//        AdapterView.OnItemClickListener onItemClickListener;
//
//        public ViewHolder(View itemView, AdapterView.OnItemClickListener onItemClickListener) {
//            super(itemView);
//
//            itemView.setOnClickListener(this);
//            // linking id's from item.xml
//                    mImageView = itemView.findViewById(R.id.cardImg);
//                    mAuthor = itemView.findViewById(R.id.author);
//                    mTitle = itemView.findViewById(R.id.title);
//                    mDescription = itemView.findViewById(R.id.desc);
//                    mSource = itemView.findViewById(R.id.source);
//                    mTime = itemView.findViewById(R.id.duration);
//
//                    this.onItemClickListener = onItemClickListener;
//
//        }
//        @Override
//        public void onClick(View view)
//        {
//            onItemClickListener.onItemClick(view,);
//        }
//    }
}
