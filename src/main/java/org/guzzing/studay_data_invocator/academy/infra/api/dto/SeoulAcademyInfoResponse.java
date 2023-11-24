package org.guzzing.studay_data_invocator.sourceacademy.infra.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class SeoulAcademyInfoResponse {

    @JsonProperty("neisAcademyInfo")
    private NeisAcademyInfo neisAcademyInfo;

    public static class NeisAcademyInfo {

        private List<SeoulAcademyInfo> row;

        public List<SeoulAcademyInfo> getRow() {
            return row;
        }
    }
}

