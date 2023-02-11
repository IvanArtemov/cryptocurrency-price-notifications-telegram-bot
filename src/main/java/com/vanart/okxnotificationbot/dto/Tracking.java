package com.vanart.okxnotificationbot.dto;


import jakarta.persistence.*;

@Entity
@Table(name = "okxnb_tracking")
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long chatId;
    @Column(nullable = false)
    private String instrument;

    @Column(nullable = false)
    private Double step;
    @Column(nullable = false)
    private Double prevPrice;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getPrevPrice() {
        return prevPrice;
    }

    public void setPrevPrice(Double lastPrice) {
        this.prevPrice = lastPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
