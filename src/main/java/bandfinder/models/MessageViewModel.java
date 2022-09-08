package bandfinder.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public record MessageViewModel(int id, String senderName, String receiverName, String content, String time) {
    public static String formatTimestamp(Timestamp t) {
        String pattern;
        if(!t.toLocalDateTime().toLocalDate().isBefore(LocalDate.now())) {
            pattern = "HH:mm";
        } else {
            pattern = "HH:mm dd/MM/yyyy";
        }
        return new SimpleDateFormat(pattern).format(t);
    }

    public static String formatDate(Date d) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d);
        cal2.setTime(Calendar.getInstance().getTime());
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        String pattern;
        if(sameDay) {
            pattern = "HH:mm";
        } else {
            pattern = "HH:mm dd/MM/yyyy";
        }
        return new SimpleDateFormat(pattern).format(d);
    }
}
