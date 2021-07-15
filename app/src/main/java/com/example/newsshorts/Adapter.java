package com.example.newsshorts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.util.TimeUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsshorts.models.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Article> articales;
   // private List<Article> newss;
    private Context context;

    public Adapter(List<Article> articales, Context context) {
        this.articales = articales;
        this.context = context;
       // this.newss = new ArrayList<>(articales);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.item,parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holders, int position) {


        //holders.progressBar.setVisibility(View.VISIBLE);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Article a = articales.get(position);
        holders.title.setText(a.getTitle());
        holders.desc.setText(a.getDescription());
        holders.source.setText(a.getSource().getName());
        holders.author.setText(a.getAuthor());
        holders.published_at.setText(Utils.DateFormat(a.getPublishedAt()));
        holders.time.setText(" \u2022 " + Utils.DateToTimeFormat(a.getPublishedAt()));

        String imageurl = a.getUrlToImage();

        Picasso.with(context).load(imageurl).into(holders.imageView);
        holders.progressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return articales.size();
    }

//    @Override
//    public Filter getFilter() {
//        return filter;
//    }
//    Filter filter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<Article> filterlist = new ArrayList<>();
//            if (constraint.toString().isEmpty()){
//                filterlist.addAll(newss);
//
//            }else {
//                for (Article n: newss){
//                    if (n.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())){
//                        filterlist.add(n);
//                    }
//                }
//            }
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = filterlist;
//            return filterResults;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//            newss.clear();
//            newss.addAll((Collection<? extends Article>) results.values);
//            notifyDataSetChanged();
//
//        }
//    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc, author, published_at, source, time;
        ImageView imageView;
        CardView cardView;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            published_at = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.prograss_load_photo);
            cardView = itemView.findViewById(R.id.cardview);

        }

    }
}
