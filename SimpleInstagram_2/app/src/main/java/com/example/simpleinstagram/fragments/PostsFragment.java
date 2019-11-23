package com.example.simpleinstagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleinstagram.Post;
import com.example.simpleinstagram.PostsAdapter;
import com.example.simpleinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static com.example.simpleinstagram.Post.KEY_CREATED_AT;

public class PostsFragment extends Fragment {

    private RecyclerView rvPosts;
    private  final  String TAG = "PostsFragment";
    protected PostsAdapter adapter;
    protected List<Post> mPosts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = view.findViewById(R.id.rvPosts);
        mPosts = new ArrayList<>();
        // Create the adaptor
        adapter = new PostsAdapter(getContext(), mPosts);
        //Create the data source
        // set the adaptor on the recycler viewer
        rvPosts.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager((getContext())));
        queryPosts();
    }
    protected void queryPosts() {

        ParseQuery<Post> query = new ParseQuery<Post>(Post.class);
        query.include(Post.key_user);
        query.setLimit(20);
        query.addDescendingOrder(KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e != null){
                    Log.d(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                mPosts.addAll(objects);
                adapter.notifyDataSetChanged();

                for(int i = 0; i< objects.size(); i++){
                    Log.d(TAG, "Post: "+ objects.get(i).getDescription());
                }
            }
        });
    }

}
