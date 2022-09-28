package xyz.seleya.ethereum.ens.ensjavaclient;

import java.util.stream.Stream;

public enum TextRecordsKey {
    AVATAR("avatar"),
    DESCRIPTION("description"),
    DISPLAY("display"),
    EMAIL("email"),
    KEYWORDS("keywords"),
    MAIL("mail"),
    NAME("name"),
    NOTICE("notice"),
    LOCATION("location"),
    PHONE("phone"),
    URL("url"),
    GITHUB("vnd.github"),
    TWITTER("vnd.twitter");

    String key;
    TextRecordsKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static Stream<TextRecordsKey> stream() {
        return Stream.of(TextRecordsKey.values());
    }
}
