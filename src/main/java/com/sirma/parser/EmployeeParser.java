package com.sirma.parser;

import com.sirma.model.Employee;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class EmployeeParser {
    protected static final String SEPARATOR = ", ";
    protected static final int EMPLOYEE_ID_STRING_INDEX = 0;
    protected static final int PROJECT_ID_STRING_INDEX = 1;
    protected static final int DATE_FROM_STRING_INDEX = 2;
    protected static final int DATE_TO_STRING_INDEX = 3;


    public static Employee parse(String line) {
        String[] employeeDataLine = line.split(SEPARATOR);

        long employeeId = Long.parseLong(employeeDataLine[EMPLOYEE_ID_STRING_INDEX].trim());
        long projectId = Long.parseLong(employeeDataLine[PROJECT_ID_STRING_INDEX].trim());

        LocalDate dateFrom = DateParser.parseDate(employeeDataLine[DATE_FROM_STRING_INDEX]);
        LocalDate dateTo = DateParser.parseDateTo(employeeDataLine);

        return new Employee(
                employeeId,
                projectId,
                dateFrom,
                dateTo
        );
    }




}