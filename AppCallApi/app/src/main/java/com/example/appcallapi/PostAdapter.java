package com.example.appcallapi;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post> postList;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Gắn layout cho mỗi item của RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        // Gắn dữ liệu từ Post object vào các View trong item
        Post post = postList.get(position);
        holder.tvPostId.setText("ID: " + post.getId());
        holder.tvPostTitle.setText(post.getTitle());
        holder.tvPostBody.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        // Trả về tổng số lượng item trong danh sách
        return postList.size();
    }

    // ViewHolder class để giữ các View cho mỗi item
    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvPostId;
        TextView tvPostTitle;
        TextView tvPostBody;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPostId = itemView.findViewById(R.id.tvPostId);
            tvPostTitle = itemView.findViewById(R.id.tvPostTitle);
            tvPostBody = itemView.findViewById(R.id.tvPostBody);
        }
    }
}