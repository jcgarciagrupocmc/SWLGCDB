package es.elvalledeljedi.swlgcdb.common.repository;

import java.util.List;

import es.elvalledeljedi.swlgcdb.domain.entity.BaseEntity;

/**
 * Created by jcgarcia on 23/05/2016.
 */
public interface DataSource<T extends BaseEntity> {
    List<T> getAll();

    List<T> getFiltered(String mainField);

    T getOne(String id);

    /**
     * Añade el item al dataSource y devuelve un código de resultado
     * @return -1 si error
     */
    int add(T item);

    /**
     * Elimina un item del datasource y devuelve un código de resultado
     *
     * @return -1 si error
     */
    int delete(T item);
}
