package com.tadpole.northmuse.vo;

import de.sstoehr.harreader.model.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by Jerry on 2017/2/28.
 */
@Builder
@Data
public class OriginalRequest {

    private HttpMethod method;
    private String url;
    private String httpVersion;
    private List<HarCookie> cookies;
    private List<HarHeader> headers;
    private List<HarQueryParam> queryString;
    private HarPostData postData;
    private Long headersSize;
    private Long bodySize;
    private String comment;
    private RequestType requestType;

}
