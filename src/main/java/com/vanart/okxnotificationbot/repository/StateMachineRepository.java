package com.vanart.okxnotificationbot.repository;

import com.vanart.okxnotificationbot.sm.SMEvents;
import com.vanart.okxnotificationbot.sm.SMStates;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StateMachineRepository {

    private final Map<Long, StateMachine<SMStates, SMEvents>> stateMachineMap = new HashMap<>();

    public Optional<StateMachine<SMStates, SMEvents>> find(Long chatId) {
        return Optional.ofNullable(stateMachineMap.get(chatId));
    }

    public void save(Long chatId, StateMachine<SMStates, SMEvents> stateMachine) {
        stateMachineMap.put(chatId, stateMachine);
    }
}
