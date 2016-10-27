package es.elvalledeljedi.swlgcdb.domain.entity;

import java.io.Serializable;

/**
 * Created by jcgarcia on 23/05/2016.
 */
public class BaseEntity implements Serializable {
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
