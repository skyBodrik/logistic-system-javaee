package logistic.web;

import java.util.Map;

/**
 * Created by bodrik on 21.04.17.
 */
public interface iStorage {

    public Map<String, Object> getStorage();

    public void set(Map<String, Object> storage);

}
