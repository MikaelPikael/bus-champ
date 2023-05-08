package com.mike.buschamp.line.service;

import com.mike.buschamp.line.dto.BusLine;
import com.mike.buschamp.line.dto.BusStop;
import com.mike.buschamp.trafiklab.TrafikLabClient;
import com.mike.buschamp.trafiklab.dto.JourneyPatternPointOnLine;
import com.mike.buschamp.trafiklab.dto.StopPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BusService {

    @Autowired
    private TrafikLabClient trafikLabClient;

    public List<BusLine> getBusLinesWithMostStops(int numberOfLines) throws IOException {
        final List<JourneyPatternPointOnLine> jourData = trafikLabClient.getJourData();
        final Map<String, Long> lineStopCounts = jourData
                .stream()
                .collect(Collectors.groupingBy(JourneyPatternPointOnLine::getLineNumber, Collectors.counting()));
        return lineStopCounts
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(numberOfLines)
                .map(entry -> {
                    BusLine busLine = new BusLine();
                    busLine.setName(entry.getKey());
                    busLine.setNumberOfStops(entry.getValue().intValue());
                    return busLine;
                }).collect(Collectors.toList());
    }

    public List<BusStop> getBusStops(String lineNumber) throws IOException {
        final List<JourneyPatternPointOnLine> jourData = trafikLabClient.getJourData();
        final List<String> pointNumbers = jourData
                .stream()
                .filter(line -> Objects.equals(line.getLineNumber(), lineNumber))
                .map(JourneyPatternPointOnLine::getJourneyPatternPointNumber)
                .collect(Collectors.toList());

        final List<StopPoint> stopData = trafikLabClient.getStopData();
        return stopData
                .stream()
                .filter(stopPoint -> pointNumbers.contains(stopPoint.getStopPointNumber()))
                .sorted(Comparator.comparing(StopPoint::getStopAreaNumber))
                .map(stopPoint -> {
                    BusStop busStop = new BusStop();
                    busStop.setName(stopPoint.getStopPointName());
                    busStop.setNumber(stopPoint.getStopPointNumber());
                    return busStop;
                })
                .collect(Collectors.toList());
    }

}
