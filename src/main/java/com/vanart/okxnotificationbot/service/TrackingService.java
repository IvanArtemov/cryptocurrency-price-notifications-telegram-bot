package com.vanart.okxnotificationbot.service;

import com.vanart.okxnotificationbot.OkxAdapter;
import com.vanart.okxnotificationbot.OkxNotificationsBot;
import com.vanart.okxnotificationbot.dto.Instrument;
import com.vanart.okxnotificationbot.dto.Tracking;
import com.vanart.okxnotificationbot.repository.TrackersRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TrackingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingService.class.getName());
    private final TrackersRepository trackersRepository;
    private final OkxAdapter okxAdapter;
    private final OkxNotificationsBot bot;
    private final Map<String, Double> instrumentLastPriceMap = new HashMap<>();


    @Autowired
    public TrackingService(TrackersRepository trackersRepository, OkxAdapter okxAdapter, OkxNotificationsBot bot) {
        this.trackersRepository = trackersRepository;
        this.okxAdapter = okxAdapter;
        this.bot = bot;
    }

    @Transactional
    public void startTracking(Long chatId, Instrument instrument, Double prevPrice, Double step) {
        var tracking = new Tracking();
        tracking.setChatId(chatId);
        tracking.setInstrument(instrument.getInstId());
        tracking.setStep(step);
        tracking.setPrevPrice(prevPrice);
        trackersRepository.save(tracking);
        LOGGER.info("Tracking started chat: " + chatId + " instrument " + instrument.getInstId() + " step " + step);
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 10000)
    @Transactional
    public void checkPrices() {
        var instruments = trackersRepository.getAll().stream()
                .map(Tracking::getInstrument).distinct().toList();
        instruments.forEach(instrument -> {
            var price = okxAdapter.getLastPrice(instrument);
            var trackers = trackersRepository
                    .getAll().stream()
                    .filter(tracking -> instrument.equals(tracking.getInstrument()));
            trackers.forEach(tracking -> {
                var prevPrice = tracking.getPrevPrice();
                var step = tracking.getStep();
                if (Math.abs(prevPrice - price) > step) {
                    var message = instrument + " price has ";
                    if (price > prevPrice) {
                        message += "increased \uD83D\uDD3C";
                    } else {
                        message += "decreased \uD83D\uDD3D";
                    }
                    tracking.setPrevPrice(price);
                    bot.sendMessage(tracking.getChatId(), message + " from " + prevPrice + " to " + price);
                }

            });
            instrumentLastPriceMap.put(instrument, price);
        });
    }

}
