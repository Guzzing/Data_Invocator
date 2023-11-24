package org.guzzing.studay_data_invocator.academy.model.vo.info;

import java.util.Objects;

public enum ShuttleAvailability {
    AVAILABLE,
    NEED_INQUIRE,
    FREE;

    public static ShuttleAvailability getShuttleAvailability(final String shuttle) {
        return Objects.equals(shuttle, "0") ? NEED_INQUIRE : AVAILABLE;
    }
}
