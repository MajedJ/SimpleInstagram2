package com.example.simpleinstagram;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.parse.ParseFile;

import java.util.List;

import com.bumptech.glide.Glide;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private  final  String TAG = "PostsAdaptor";


    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHandle;
        private ImageView ivImage;
        private TextView tvDescription;

        public ViewHolder (View itemView) {
            super(itemView);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            ivImage =  itemView.findViewById(R.id.ivImage);
            tvDescription =  itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Post post) {
            tvHandle.setText((post.getUser().getUsername()));
            ParseFile image = post.getImage();
            if(image != null) {
                Log.d(TAG, "imageUrls: "+ image.getUrl());
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            tvDescription.setText((post.getDescription()));
        }

    }
}
