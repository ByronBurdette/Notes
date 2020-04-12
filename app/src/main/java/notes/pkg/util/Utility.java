package notes.pkg.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
    public static String ezDate(String timestamp) {
        String year=timestamp.substring(0,4), month=timestamp.substring(5,7), day=timestamp.substring(8,10), date="";

        String str = "This is a test for Git";
        String str1 = "Made another string to test git";

        if (timestamp != null)
            date=getMonthFromNumber(month) + " " + day + ", " + year;

        return date;
    }
    public static String getFormattedTimestamp(String inputTs) {
        String year=inputTs.substring(0,4), month=inputTs.substring(5,7), day=inputTs.substring(8,10);

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime lksjdf = LocalDateTime.from(inputFormat.parse(inputTs));
        return null;
    }

    private static String getYear() {
        return null;
    }

    public static String getMonthFromNumber(String monthNumber) {
        String month="";

        switch (monthNumber) {
            case "01":
                month = "Jan";
                break;
            case "02":
                month = "Feb";
                break;
            case "03":
                month = "Mar";
                break;
            case "04":
                month = "Apr";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "Jun";
                break;
            case "07":
                month = "Jul";
                break;
            case "08":
                month = "Aug";
                break;
            case "09":
                month = "Sep";
                break;
            case "10":
                month = "Oct";
                break;
            case "11":
                month = "Nov";
                break;
            case "12":
                month = "Dec";
                break;
        }

        return month;
    }
}
