package org.guzzing.studay_data_invocator.region.service;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.guzzing.studay_data_invocator.region.model.vo.Address;

public record AddressSet(
        Set<Address> addresses
) {

    public static AddressSet init() {
        return new AddressSet(new CopyOnWriteArraySet<>());
    }

    public void addAddress(final Address address) {
        this.addresses.add(address);
    }

    public boolean notContainAddress(final Address address) {
        return !this.addresses.contains(address);
    }

    public void storeCurrentSet(final AddressSet addressSet) {
        this.addresses.addAll(addressSet.addresses);
    }

}
