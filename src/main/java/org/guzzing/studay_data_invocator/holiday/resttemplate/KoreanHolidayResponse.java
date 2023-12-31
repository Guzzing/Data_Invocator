package org.guzzing.studay_data_invocator.holiday.resttemplate;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class KoreanHolidayResponse {

    private Body body;

    @Setter
    @Getter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body {

        @XmlElementWrapper(name = "items")
        @XmlElement(name = "item")
        private List<Item> items;

        public List<Item> getItems() {
            if (items == null) {
                return new ArrayList<>();
            }
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }

    @Setter
    @Getter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {

        private String dateKind;
        private String dateName;
        private String isHoliday;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate locdate;
        private int seq;
    }
}

