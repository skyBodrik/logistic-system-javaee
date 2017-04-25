package logistic.web;

import javax.ejb.*;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bodrik on 21.04.17.
 */
@Stateful
@RequestScoped
public class RequestStorage implements iStorage, Serializable  {

    private Map<String, Object> storage = new HashMap<>();

    public Map<String, Object> getStorage() {
        return storage;
    }

    public void set(Map<String, Object> storage) {
        this.storage = storage;
    }

}
