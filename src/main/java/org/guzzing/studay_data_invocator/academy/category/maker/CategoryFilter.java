package org.guzzing.studay_data_invocator.academy.category.maker;

import java.util.List;

public class CategoryFilter {

    public static boolean isContains(List<String> filters, String value) {
        for(String filter : filters) {
            if(value.contains(filter)) {
                return true;
            }
        }
        return false;
    }

}
