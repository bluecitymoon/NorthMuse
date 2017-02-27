package com.tadpole.northmuse.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * Created by Jerry on 2017/2/24.
 */
@Data
@Builder
public class AnalysisResponse {

    @Getter
    private String htmlText;

}
