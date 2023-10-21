package org.guzzing.studay_data_invocator.academy.model.vo.academy_info;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.guzzing.studay_data_invocator.academy.model.vo.academy_info.vo.AcademyName;
import org.guzzing.studay_data_invocator.academy.model.vo.academy_info.vo.PhoneNumber;
import org.guzzing.studay_data_invocator.academy.model.vo.academy_info.vo.ShuttleAvailability;

@Embeddable
public class AcademyInfo {

    @Embedded
    private AcademyName name;

    @Embedded
    private PhoneNumber contact;

    @Enumerated(value = EnumType.STRING)
    private ShuttleAvailability shuttle;

    public AcademyInfo(final String name, final String contact, final String shuttle) {
        this.name = new AcademyName(name);
        this.contact = new PhoneNumber(contact);
        this.shuttle = ShuttleAvailability.getShuttleAvailability(shuttle);
    }

    protected AcademyInfo() {
    }

    public String getName() {
        return name.getValue();
    }

    public String getContact() {
        return contact.getContact();
    }

    public String getShuttle() {
        return shuttle.name();
    }
}
