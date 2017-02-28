package com.tadpole.northmuse.service.impl;

import com.tadpole.northmuse.vo.AnalysisResponse;
import com.tadpole.northmuse.vo.RequestType;
import com.tadpole.northmuse.vo.ResourceTarget;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 2017/2/28.
 */
public class HarLogProcessor {

    public static AnalysisResponse process(HarLog harLog) {

        List<ResourceTarget> targets = new ArrayList<>();

        for (HarEntry harEntry: harLog.getEntries()) {

            ResourceTarget resourceTarget = ResourceTarget.builder()
                .request(harEntry.getRequest())
                .response(harEntry.getResponse())
                .serverIPAddress(harEntry.getServerIPAddress())
                .timings(harEntry.getTimings())
                .build();

            String requestUrl = resourceTarget.getRequest().getUrl();

            if (requestUrl.endsWith(".css")) {
                resourceTarget.setRequestType(RequestType.CSS);
            } else  if (requestUrl.endsWith(".js")) {

                resourceTarget.setRequestType(RequestType.JS);

            } else if (requestUrl.endsWith(".png") || requestUrl.endsWith(".jpg") || requestUrl.endsWith("jpeg")) {
                resourceTarget.setRequestType(RequestType.PICTURE);
            }

            String mimeType = harEntry.getResponse().getContent().getMimeType();

            if (mimeType.contains("json")) {
                resourceTarget.setRequestType(RequestType.JSON);
            } else if (mimeType.contains("html")) {
                resourceTarget.setRequestType(RequestType.HTML);
            }

            targets.add(resourceTarget);
        }

        return AnalysisResponse.builder()
            .entries(targets)
            .build();
    }
}
