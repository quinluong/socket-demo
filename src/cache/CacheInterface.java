package cache;

public interface CacheInterface {

    void set(String key, String value);

    String get(String key);

    void del(String key);
}
