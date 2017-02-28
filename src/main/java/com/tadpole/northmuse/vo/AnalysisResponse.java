package com.tadpole.northmuse.vo;

import de.sstoehr.harreader.model.HarCreatorBrowser;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarLog;
import de.sstoehr.harreader.model.HarPage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2017/2/24.
 */
@Data
@Builder
public class AnalysisResponse implements Serializable{

    private List<String> requestTypes;
    private List<ResourceTarget> entries = new ArrayList<>();

}
