package me.kenux.travelog.domain.enums;

public enum SiteType {

    DECK("데크"),
    STONE("파쇄석"),
    SAND("모래"),
    BLOCK("블럭"),
    GRASS("잔디"),
    DIRT("흙바닥");

    private final String value;

    SiteType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
