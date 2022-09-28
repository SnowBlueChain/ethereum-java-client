package xyz.seleya.ethereum.ens.ensjavaclient.textrecords;

import java.util.stream.Stream;

public enum ServiceKey {
     GITHUB("vnd.github"),
     TWITTER("vnd.twitter");
     String key;
     ServiceKey (String key) {
        this.key = key;
     }

     public String getKey() {
        return key;
     }

    public static Stream<ServiceKey> stream() {
        return Stream.of(ServiceKey.values());
    }
}
