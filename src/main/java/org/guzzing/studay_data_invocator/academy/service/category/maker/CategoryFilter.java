package org.guzzing.studay_data_invocator.academy.service.category.maker;

import java.util.List;

public class CategoryFilter {

    public static boolean isContains(List<String> filters, String value) {
        for(String filter : filters) {
            if(filter.equals(value)) {
                return true;
            }
        }
        return false;
    }

}
