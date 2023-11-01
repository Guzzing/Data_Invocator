package org.guzzing.studay_data_invocator.region.model.vo;

import static org.guzzing.studay_data_invocator.global.location.RegionUnit.SIDO;
import static org.guzzing.studay_data_invocator.global.location.RegionUnit.SIGUNGU;
import static org.guzzing.studay_data_invocator.global.location.RegionUnit.UPMYEONDONG;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import org.guzzing.studay_data_invocator.global.exception.AddressException;
import org.guzzing.studay_data_invocator.global.location.RegionUnit;

@Getter
@Embeddable
public class Address {

    @Transient
    private static final String SIGUNGU_REGEXP = ".+(시)|.+(구)|.+(군)";

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigungu", nullable = false)
    private String sigungu;

    @Column(name = "upmyeondong", nullable = false)
    private String upmyeondong;

    protected Address() {
    }

    protected Address(final String sido, final String sigungu, final String upmyeondong) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.upmyeondong = upmyeondong;
    }

    public static Address of(final String sido, final String sigungu, final String upmyeondong) {
        validate(sido, SIDO);
        validate(sigungu, SIGUNGU);
        validate(upmyeondong, UPMYEONDONG);

        String regulatedSigungu = regulateSigungu(sigungu);
        String regulatedUpmyeondong = regulateUpmyeondong(upmyeondong);

        return new Address(sido, regulatedSigungu, regulatedUpmyeondong);
    }

    public String getFullAddress() {
        return MessageFormat.format("{0} {1} {2}", this.sido, this.sigungu, this.upmyeondong);
    }

    private static String regulateSigungu(final String sigungu) {
        StringBuilder regulatedSigungu = new StringBuilder();

        Pattern pattern = Pattern.compile(SIGUNGU_REGEXP);
        Matcher matcher = pattern.matcher(sigungu);

        while (matcher.find()) {
            regulatedSigungu.append(matcher.group())
                    .append(" ");
        }

        return regulatedSigungu.toString()
                .trim();
    }

    private static String regulateUpmyeondong(final String upmyeondong) {
        return upmyeondong.chars()
                .filter(codePoint -> !Character.isDigit(codePoint))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static void validate(final String data, final RegionUnit regionUnit) {
        if (data == null) {
            throw new AddressException("지역 데이터는 반드시 주어져야 합니다.");
        }
        if (!regionUnit.isMatched(data)) {
            throw new AddressException(regionUnit + "에 매칭되지 않은 지역 데이터입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(sido, address.sido) && Objects.equals(sigungu, address.sigungu)
                && Objects.equals(upmyeondong, address.upmyeondong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sido, sigungu, upmyeondong);
    }
}
