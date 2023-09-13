package com.facu.altisima.service.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormat.format(date);
    }
}
