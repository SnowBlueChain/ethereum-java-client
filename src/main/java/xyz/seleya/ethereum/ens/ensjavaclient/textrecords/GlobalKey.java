package xyz.seleya.ethereum.ens.ensjavaclient.textrecords;

public enum GlobalKey {
    AVATOR("avator"),
    DESCRIPTION("description"),
    DISPLAY("display"),
    EMAIL("email"),
    KEYWORDS("keywords"),
    MAIL("mail"),
    NOTICE("notice"),
    LOCATION("location"),
    PHONE("phone"),
    URL("url");

    String key;
    GlobalKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
