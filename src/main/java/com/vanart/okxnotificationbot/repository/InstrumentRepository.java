package com.vanart.okxnotificationbot.repository;

import com.vanart.okxnotificationbot.OkxAdapter;
import com.vanart.okxnotificationbot.dto.Instrument;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InstrumentRepository {
    private final Map<String, Instrument> instrumentsMap = new HashMap<>();
    private final OkxAdapter adapter;
    private volatile LocalDate lastUpdateDate = LocalDate.now();

    @Autowired
    public InstrumentRepository(OkxAdapter adapter) {
        this.adapter = adapter;
    }

    @PostConstruct
    private void init() {
        updateInstruments();
    }

    public Optional<Instrument> getInstrument(String instId) {
        updateInstrumentsIfOld();
        return Optional.ofNullable(instrumentsMap.get(instId));
    }

    public List<Instrument> getInstrumentsBySubstring(String instIdSubstring) {
        updateInstrumentsIfOld();
        return instrumentsMap
                .values().stream()
                .filter(instrument -> instrument.getInstId().startsWith(instIdSubstring.toUpperCase()))
                .collect(Collectors.toList());
    }

    private void updateInstruments() {
        var instMap = adapter
                .getIstruments().stream()
                .collect(Collectors.toMap(Instrument::getInstId, Function.identity()));
        instrumentsMap.putAll(instMap);
        lastUpdateDate = LocalDate.now();
    }

    private void updateInstrumentsIfOld() {
        LocalDate date = LocalDate.now().minusDays(1);
        if (lastUpdateDate.isBefore(date)) {
            updateInstruments();
        }
    }

    public List<Instrument> getInstruments() {
        return instrumentsMap.values().stream().toList();
    }
}
