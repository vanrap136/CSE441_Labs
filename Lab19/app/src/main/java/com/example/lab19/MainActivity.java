package com.example.lab19;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvResult = findViewById(R.id.tvResult);

        List<Employee> employees = parseXmlFromAssets();

        if (employees != null && !employees.isEmpty()) {
            StringBuilder resultText = new StringBuilder();
            for (Employee employee : employees) {
                resultText.append(employee.toString()).append("\n");
            }
            tvResult.setText(resultText.toString());
        } else {
            tvResult.setText("Không thể đọc dữ liệu XML.");
        }
    }

    private List<Employee> parseXmlFromAssets() {
        try {
            // Lấy InputStream từ file employee.xml trong thư mục assets
            InputStream is = getAssets().open("employee.xml");

            // Tạo SAXParserFactory
            SAXParserFactory factory = SAXParserFactory.newInstance();
            // Tạo SAXParser
            SAXParser saxParser = factory.newSAXParser();
            // Lấy XMLReader từ SAXParser
            XMLReader xmlReader = saxParser.getXMLReader();

            // Tạo MySAXHandler
            MySAXHandler mySAXHandler = new MySAXHandler();
            // Đặt ContentHandler cho XMLReader
            xmlReader.setContentHandler(mySAXHandler);

            // Bắt đầu phân tích XML từ InputStream
            xmlReader.parse(new InputSource(is));

            // Đóng InputStream
            is.close();

            // Trả về danh sách nhân viên đã được phân tích
            return mySAXHandler.getEmployeeList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}