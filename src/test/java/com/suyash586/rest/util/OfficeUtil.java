package com.suyash586.rest.util;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.suyash586.rest.domain.Office;

public class OfficeUtil {

    private static final String ID = "id";
    private static final String LOCATION = "location";
    
    

    private OfficeUtil() {
    }

    public static Office createOffice() {
        return new Office(ID, LOCATION,new Integer("530"),new Time(9, 30, 00), new Time(19, 30, 00));
    }

    public static List<Office> createOfficeList(int howMany) {
        List<Office> officeList = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            officeList.add(new Office(ID, LOCATION,new Integer("530"),new Time(9, 30, 00), new Time(19, 30, 00)));
        }
        return officeList;
    }

}
