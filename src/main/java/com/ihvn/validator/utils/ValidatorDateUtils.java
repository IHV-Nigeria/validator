package com.ihvn.validator.utils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ValidatorDateUtils {

    public LocalDate convertToLocalDateTime(XMLGregorianCalendar xmlGregorianCalendar){

        Date utilDate = xmlGregorianCalendar.toGregorianCalendar().getTime();
        return LocalDateTime.ofInstant( utilDate.toInstant(), ZoneId.systemDefault() ).toLocalDate();
    }
}
