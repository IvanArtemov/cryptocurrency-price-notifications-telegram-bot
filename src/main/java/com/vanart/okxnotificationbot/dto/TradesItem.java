package com.vanart.okxnotificationbot.dto;

public class TradesItem {
    private String instId;
    private String side;
    private double sz;
    private double px;
    private long tradeId;
    private long ts;

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public double getSz() {
        return sz;
    }

    public void setSz(double sz) {
        this.sz = sz;
    }

    public double getPx() {
        return px;
    }

    public void setPx(double px) {
        this.px = px;
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
