package com.mike.buschamp.line.controller;

import com.mike.buschamp.line.dto.BusLine;
import com.mike.buschamp.line.dto.BusStop;
import com.mike.buschamp.line.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin
public class BusController {

    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("/lines")
    public List<BusLine> getBusLines() {
        try {
            return busService.getBusLinesWithMostStops(10);
        } catch (IOException ex) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Could not get bus lines");
        }
    }

    @GetMapping("/lines/{number}/stops")
    public List<BusStop> getBusStops(@PathVariable("number") String lineNumber) {
        try {
            return busService.getBusStops(lineNumber);
        } catch (IOException ex) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Could not get bus stops");
        }
    }

}
