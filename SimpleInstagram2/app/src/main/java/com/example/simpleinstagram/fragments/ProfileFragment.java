package com.example.simpleinstagram.fragments;

import android.util.Log;

import com.example.simpleinstagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import static com.example.simpleinstagram.Post.KEY_CREATED_AT;

public class ProfileFragment extends PostsFragment {

    private  final  String TAG = "ProfileFragment";

    @Override
    protected void queryPosts() {
        ParseQuery<Post> query = new ParseQuery<Post>(Post.class);
        query.include(Post.key_user);
        query.setLimit(20);
        query.whereEqualTo(Post.key_user, ParseUser.getCurrentUser());
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
