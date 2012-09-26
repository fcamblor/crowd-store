package com.crowdstore.models.hello;

import com.crowdstore.models.common.GenericIdentifiable;

/**
 * @author fcamblor
 */
public class HelloModel extends GenericIdentifiable {
    Integer value;

    public HelloModel() {
        super();
    }

    public HelloModel(Long id) {
        super(id);
    }

    public HelloModel setValue(Integer _value) {
        this.value = _value;
        return this;
    }

    public Integer getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HelloModel that = (HelloModel) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
