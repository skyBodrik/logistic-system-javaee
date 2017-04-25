package logistic.web.dao;

/**
 * Created by bodrik on 24.04.17.
 */
public interface iDao<T> {
    public void add(T obj);

    public void update(T obj);

    public void delete(T obj);

    public void refresh(T obj);
}
