package org.guzzing.studay_data_invocator.region.repository;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.guzzing.studay_data_invocator.region.model.Region;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RegionRepository {

    private static final String SAVE_ALL_QUERY = """
            INSERT INTO regions (code, sido, sigungu, upmyeondong, point, area)
            VALUES (:code, :sido, :sigungu, :upmyeondong, ST_GeomFromText(:point), ST_GeomFromText(:area))
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RegionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void saveAll(List<Region> regions) {
        WKTWriter wktWriter = new WKTWriter();

        List<MapSqlParameterSource> batchParam = regions.stream()
                .map(region -> {
                    Map<String, Object> paramMap = new ConcurrentHashMap<>();

                    paramMap.put("code", region.getId());
                    paramMap.put("sido", region.getSido());
                    paramMap.put("sigungu", region.getSigungu());
                    paramMap.put("upmyeondong", region.getUpmyeondong());
                    paramMap.put("point", wktWriter.write(region.getPoint()));
                    paramMap.put("area", wktWriter.write(region.getArea()));

                    return new MapSqlParameterSource(paramMap);
                })
                .toList();

        jdbcTemplate.batchUpdate(SAVE_ALL_QUERY, batchParam.toArray(new SqlParameterSource[0]));
    }
}
