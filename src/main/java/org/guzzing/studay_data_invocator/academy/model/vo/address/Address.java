package org.guzzing.studay_data_invocator.academy.model.vo.address;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import org.guzzing.studay_data_invocator.academy.model.vo.address.vo.AddressDetail;
import org.guzzing.studay_data_invocator.academy.model.vo.address.vo.Dosi;
import org.guzzing.studay_data_invocator.academy.model.vo.address.vo.Sigungu;
import org.guzzing.studay_data_invocator.academy.model.vo.address.vo.Upmyeondong;

@Embeddable
public class Address {

    @Embedded
    private AddressDetail addressDetail;

    @Embedded
    private Dosi dosi;

    @Embedded
    private Sigungu sigungu;

    @Embedded
    private Upmyeondong upmyeondong;

    public Address(final String address) {
        this.addressDetail = new AddressDetail(address);
        String[] parsedAddress = addressDetail.parseAddressData();
        this.dosi = new Dosi(parsedAddress[0]);
        this.sigungu = new Sigungu(parsedAddress[1]);
        this.upmyeondong = new Upmyeondong(parsedAddress[2]);
    }

    protected Address() {
    }

    private String[] parseAddress(final String address) {
        return address.split(" ");
    }

    public String getAddressDetail() {
        return addressDetail.getValue();
    }

    public String getDosi() {
        return dosi.getValue();
    }

    public String getSigungu() {
        return sigungu.getValue();
    }

    public String getUpmyeondong() {
        return upmyeondong.getValue();
    }
}
