package es.elvalledeljedi.swlgcdb.common.repository;

import java.util.List;

import es.elvalledeljedi.swlgcdb.domain.entity.BaseEntity;

/**
 * Created by jcgarcia on 23/05/2016.
 */
public class Repository<T extends BaseEntity> {

    private final DataSource<T> dataSource;

    public Repository(DataSource<T> dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource<T> getDataSource() {
        return dataSource;
    }

    public List<T> getAll() {
        return dataSource.getAll();
    }

    public List<T> getFiltered(String mainField) {
        return dataSource.getFiltered(mainField);
    }

    public T getOne(String id) {
        return dataSource.getOne(id);
    }

    public int add(T item) {
        return dataSource.add(item);
    }

    public int delete(T item) {
        return dataSource.delete(item);
    }

}
