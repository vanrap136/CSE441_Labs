package com.example.lab19;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MySAXHandler extends DefaultHandler {

    private final List<Employee> employeeList;
    private Employee currentEmployee;
    private final StringBuilder currentValue; // Dùng để lưu trữ nội dung của các tag

    public MySAXHandler() {
        employeeList = new ArrayList<>();
        currentValue = new StringBuilder();
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    @Override
    public void startDocument() {
        // Được gọi khi bắt đầu đọc tài liệu XML
        // Không cần làm gì đặc biệt ở đây cho bài này
    }

    @Override
    public void endDocument() {
        // Được gọi khi kết thúc đọc tài liệu XML
        // Không cần làm gì đặc biệt ở đây cho bài này
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        // Được gọi khi bắt đầu một thẻ XML
        currentValue.setLength(0); // Xóa bộ đệm cho nội dung mới

        if (qName.equalsIgnoreCase("employee")) {
            currentEmployee = new Employee();
            // Lấy thuộc tính 'id' và 'title'
            currentEmployee.setId(attributes.getValue("id"));
            currentEmployee.setTitle(attributes.getValue("title"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        // Được gọi khi kết thúc một thẻ XML
        if (qName.equalsIgnoreCase("employee")) {
            employeeList.add(currentEmployee);
        } else if (qName.equalsIgnoreCase("name")) {
            currentEmployee.setName(currentValue.toString());
        } else if (qName.equalsIgnoreCase("phone")) {
            currentEmployee.setPhone(currentValue.toString());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        // Được gọi khi đọc nội dung (text) giữa các thẻ
        currentValue.append(new String(ch, start, length));
    }
}