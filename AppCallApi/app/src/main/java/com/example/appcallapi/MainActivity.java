package com.example.appcallapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log; // Thêm import này để log lỗi

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // Để log
    private PostAdapter postAdapter; // Sử dụng biến cho Adapter
    private final List<Post> postList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo Adapter và gán cho RecyclerView
        postAdapter = new PostAdapter(postList);
        recyclerView.setAdapter(postAdapter);

        // Bắt đầu task để gọi API
        new FetchPostsTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchPostsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://jsonplaceholder.typicode.com/posts") // URL API
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return response.body().string();
                } else {
                    Log.e(TAG, "Request failed: " + response.code() + " " + response.message());
                    return null;
                }
            } catch (IOException e) {
                Log.e(TAG, "Network error: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(String json) {
            if (json != null) {
                try {
                    JSONArray array = new JSONArray(json);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        // Đảm bảo các trường khớp với JSON từ API
                        int id = obj.getInt("id");
                        String title = obj.getString("title");
                        String body = obj.getString("body");
                        int userId = obj.getInt("userId"); // Thêm userId

                        postList.add(new Post(id, title, body, userId)); // Thêm Post mới vào danh sách
                    }
                    // Thông báo cho Adapter biết dữ liệu đã thay đổi
                    postAdapter.notifyDataSetChanged();
                    Log.d(TAG, "Dữ liệu đã tải thành công: " + postList.size() + " bài viết.");
                } catch (JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Không nhận được dữ liệu JSON từ API.");
                // Có thể hiển thị thông báo lỗi lên UI ở đây
            }
        }
    }
}