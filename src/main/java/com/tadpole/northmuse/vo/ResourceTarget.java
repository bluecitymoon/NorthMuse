package com.tadpole.northmuse.vo;

import de.sstoehr.harreader.model.HarCache;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;
import de.sstoehr.harreader.model.HarTiming;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Jerry on 2017/2/28.
 */
@Builder
@Data
public class ResourceTarget {

    private HarRequest request;
    private HarResponse response;
    private HarCache cache;
    private HarTiming timings;
    private String serverIPAddress;
    private RequestType requestType;
    private String urlPath;
    private String rootUrl;

}
