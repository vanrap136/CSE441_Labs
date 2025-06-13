package com.example.appjsonsxmlparser;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; // Thêm Toast để hiển thị thông báo ngắn

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory; // Thêm import này

import java.io.IOException;
import java.io.StringReader; // Thêm import này
import java.net.UnknownHostException; // Thêm import này

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // Để log
    private TextView txtResult;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);
        Button btnFetchJSON = findViewById(R.id.btnFetchJSON);
        Button btnFetchXML = findViewById(R.id.btnFetchXML);

        // Thiết lập sự kiện click cho nút Tải JSON
        btnFetchJSON.setOnClickListener(v -> {
            txtResult.setText("Đang tải JSON...");
            new FetchDataTask().execute("json");
        });

        // Thiết lập sự kiện click cho nút Tải XML
        btnFetchXML.setOnClickListener(v -> {
            txtResult.setText("Đang tải XML...");
            new FetchDataTask().execute("xml");
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchDataTask extends AsyncTask<String, Void, String[]> {
        // Tham số String[0] là loại ("json" hoặc "xml"), String[1] là kết quả trả về
        @Override
        protected String[] doInBackground(String... types) {
            String type = types[0];
            String url;
            // JSONPlaceholder là một API JSON công cộng, phù hợp để test
            String jsonUrl = "https://jsonplaceholder.typicode.com/posts/1";

            // Đối với XML, bạn cần một API thực sự trả về XML.
            // Ví dụ này sử dụng một URL giả định.
            // Để test, bạn có thể tạo một file XML đơn giản và host nó,
            // hoặc tìm một API XML công cộng với cấu trúc đơn giản.
            // Ví dụ XML: <data><title>Tiêu đề XML</title><body>Nội dung XML</body></data>
            String xmlUrl = "https://www.w3schools.com/xml/note.xml"; // Sử dụng một API XML có sẵn để test nhanh

            if (type.equals("json")) {
                url = jsonUrl;
            } else {
                url = xmlUrl;
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return new String[]{type, response.body().string()};
                } else {
                    Log.e(TAG, "Yêu cầu thất bại: " + response.code() + " " + response.message());
                    return new String[]{type, "Lỗi: Yêu cầu thất bại (" + response.code() + ")"};
                }
            } catch (UnknownHostException e) {
                Log.e(TAG, "Lỗi UnknownHost: " + e.getMessage());
                return new String[]{type, "Lỗi: Không tìm thấy máy chủ. Kiểm tra URL hoặc kết nối mạng."};
            } catch (IOException e) {
                Log.e(TAG, "Lỗi mạng: " + e.getMessage());
                e.printStackTrace();
                return new String[]{type, "Lỗi: " + e.getMessage()};
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String[] result) {
            String type = result[0];
            String data = result[1];

            if (data.startsWith("Lỗi:")) {
                txtResult.setText(data);
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
                return;
            }

            try {
                if (type.equals("json")) {
                    // Phân tích JSON
                    JSONObject obj = new JSONObject(data);
                    String title = obj.optString("title", "Không có tiêu đề"); // Dùng optString để tránh lỗi nếu trường không tồn tại
                    String body = obj.optString("body", "Không có nội dung");
                    txtResult.setText("Dữ liệu JSON:\n\nTiêu đề: " + title + "\n\nNội dung: " + body);
                } else {
                    // Phân tích XML
                    String parsedXmlResult = parseXml(data);
                    txtResult.setText("Dữ liệu XML:\n\n" + parsedXmlResult);
                }
            } catch (JSONException e) {
                Log.e(TAG, "Lỗi phân tích JSON: " + e.getMessage());
                txtResult.setText("Lỗi phân tích JSON: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) { // Bao gồm cả XmlPullParserException
                Log.e(TAG, "Lỗi phân tích XML hoặc khác: " + e.getMessage());
                txtResult.setText("Lỗi phân tích XML hoặc khác: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Phương thức riêng để phân tích XML
    private String parseXml(String xmlData) throws Exception {
        StringBuilder result = new StringBuilder();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(new StringReader(xmlData)); // Đọc từ chuỗi
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    // Bắt đầu thẻ (ví dụ: <title>)
                    if (tagName != null) {
                        result.append(tagName).append(": ");
                    }
                    break;
                case XmlPullParser.TEXT:
                    // Nội dung của thẻ (ví dụ: "Tiêu đề XML")
                    if (parser.getText() != null && !parser.getText().trim().isEmpty()) {
                        result.append(parser.getText().trim()).append("\n");
                    }
                    break;
                case XmlPullParser.END_TAG:
                    // Kết thúc thẻ
                    break;
            }
            eventType = parser.next();
        }
        return result.toString();
    }
}