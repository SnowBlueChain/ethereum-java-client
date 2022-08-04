package xyz.seleya.ethereum.ens.ensjavaclient.textrecords;

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
}
