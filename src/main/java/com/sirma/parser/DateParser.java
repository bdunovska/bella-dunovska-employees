package com.sirma.parser;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static com.sirma.parser.EmployeeParser.DATE_TO_STRING_INDEX;

public class DateParser {
    private static final String TODAY_STRING_REPRESENTATION = "NULL";
    //adds support for various date formats
    private static final String[] POSSIBLE_DATE_FORMATS =
            {
                    "yyyy.MM.dd G 'at' HH:mm:ss z",
                    "EEE, MMM d, ''yy",
                    "h:mm a",
                    "hh 'o''clock' a, zzzz",
                    "K:mm a, z",
                    "yyyyy.MMMMM.dd GGG hh:mm aaa",
                    "EEE, d MMM yyyy HH:mm:ss Z",
                    "yyMMddHHmmssZ",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                    "YYYY-'W'ww-u",
                    "EEE, dd MMM yyyy HH:mm:ss z",
                    "EEE, dd MMM yyyy HH:mm zzzz",
                    "yyyy-MM-dd'T'HH:mm:ssZ",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSzzzz",
                    "yyyy-MM-dd'T'HH:mm:sszzzz",
                    "yyyy-MM-dd'T'HH:mm:ss z",
                    "yyyy-MM-dd'T'HH:mm:ssz",
                    "yyyy-MM-dd'T'HH:mm:ss",
                    "yyyy-MM-dd'T'HHmmss.SSSz",
                    "yyyy-MM-dd",
                    "yyyyMMdd",
                    "dd/MM/yy",
                    "dd/MM/yyyy"
            };


    public static LocalDate parseDate(String inputDate) {
        Date parsedDate = null;
        try {
            parsedDate = DateUtils.parseDate(inputDate, POSSIBLE_DATE_FORMATS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return LocalDate.ofInstant(parsedDate.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate parseDateTo(String[] employeeDataLine) {
        LocalDate dateTo;
        if (employeeDataLine[DATE_TO_STRING_INDEX] == null || TODAY_STRING_REPRESENTATION.equals(employeeDataLine[3])) {
            dateTo = LocalDate.now();
        } else {
            dateTo = parseDate(employeeDataLine[DATE_TO_STRING_INDEX]);
        }
        return dateTo;
    }
}
