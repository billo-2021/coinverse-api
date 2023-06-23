package com.coinverse.api.features.authentication.validators;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ValidationResultData {
    private final Map<String, Object> data;

    public ValidationResultData() {
        data = new HashMap<>();
    }

    public <C> C getDataAttributeValue(String attribute, Class<C> cls) {
        try {
            Object attributeValue = data.get(attribute);

            if (attributeValue != null) {
                return cls.cast(attributeValue);
            }

            throw new NoSuchElementException();

        } catch (ClassCastException ex) {
            throw new NoSuchElementException();
        }
    }

    public void addDataAttributeValue(String attribute, Object value) {
        data.put(attribute, value);
    }
}
