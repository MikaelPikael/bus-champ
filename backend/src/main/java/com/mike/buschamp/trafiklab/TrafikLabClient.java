package com.mike.buschamp.trafiklab;

import com.mike.buschamp.trafiklab.dto.JourneyPatternPointOnLine;
import com.mike.buschamp.trafiklab.dto.JourneyPatternPointOnLineApiResponse;
import com.mike.buschamp.trafiklab.dto.StopPoint;
import com.mike.buschamp.trafiklab.dto.StopPointApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class TrafikLabClient {

    private final String jourUri;
    private final String stopUri;

    @Autowired
    public TrafikLabClient(TrafikLabApiProperties trafikLabApiProperties) {
        jourUri = trafikLabApiProperties.getUrl() + "?model=jour&key=" + trafikLabApiProperties.getKey() + "&DefaultTransportModeCode=BUS";
        stopUri = trafikLabApiProperties.getUrl() + "?model=stop&key=" + trafikLabApiProperties.getKey() + "&DefaultTransportModeCode=BUS";
    }
    @Cacheable("busLines")
    public List<JourneyPatternPointOnLine> getJourData() throws IOException {
        JourneyPatternPointOnLineApiResponse response = sendGetRequest(jourUri, JourneyPatternPointOnLineApiResponse.class);
        return response.getResponseData().getResult();
    }

    @Cacheable("busStops")
    public List<StopPoint> getStopData() throws IOException {
            StopPointApiResponse response = sendGetRequest(stopUri, StopPointApiResponse.class);
        return response.getResponseData().getResult();
    }

    private <T> T sendGetRequest(String uri, Class<T> type) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");

        ResponseEntity<T> responseEntity = restTemplate.exchange(uri, HttpMethod.GET,
                new HttpEntity<>(headers), type);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new IOException("Request failed with status code " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }

}
