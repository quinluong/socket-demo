package cache;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 41ngu
 */
public class CacheMap implements CacheInterface {

    private final Map<String, String> map;

    public CacheMap() {
        map = new HashMap();
    }

    @Override
    public void set(String key, String value) {
        map.put(key, value);
    }

    @Override
    public String get(String key) {
        return map.getOrDefault(key, "");
    }

    @Override
    public void del(String key) {
        map.remove(key);
    }

}
