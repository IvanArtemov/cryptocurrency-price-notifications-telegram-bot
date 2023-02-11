package com.vanart.okxnotificationbot;

import com.vanart.okxnotificationbot.dto.Instrument;
import com.vanart.okxnotificationbot.dto.TradesItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OkxAdapter {

    @Value("${okx.paths.trades}")
    private String tradesPath;
    @Value("${okx.paths.instruments}")
    private String instrumentsPath;

    private final OkxHttpClient okxHttpClient;

    @Autowired
    public OkxAdapter(OkxHttpClient okxHttpClient) {
        this.okxHttpClient = okxHttpClient;
    }

    public Double getLastPrice(String instId) {
        var resp = okxHttpClient.sendGetRequest(tradesPath + "?instId=" + instId + "&limit=1",
                new TradesItem());
        return resp.get(0).getPx();
    }

    public List<Instrument> getIstruments() {
        var resp = okxHttpClient.sendGetRequest(instrumentsPath + "?instType=SPOT",
                new Instrument());
        return resp;
    }

}
