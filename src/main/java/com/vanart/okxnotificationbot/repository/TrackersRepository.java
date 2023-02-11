package com.vanart.okxnotificationbot.repository;

import com.vanart.okxnotificationbot.dto.Tracking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrackersRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Tracking tracking) {
        entityManager.persist(tracking);
    }

    public List<Tracking> getAll() {
        return (List<Tracking>) entityManager.createQuery("from Tracking").getResultList();
    }

    public List<Tracking> getByChatId(Long chatId) {
        return (List<Tracking>) entityManager.createQuery("from Tracking t where t.chatId = :chatId")
                .setParameter("chatId", chatId)
                .getResultList();
    }

    @Transactional
    public void removeTracking(Long chatId, Long trackingId) {
        var tracking = entityManager
                .find(Tracking.class, trackingId);
        if (tracking.getChatId().equals(chatId)) {
            entityManager.remove(tracking);
        }
    }
}
