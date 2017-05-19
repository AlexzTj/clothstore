package com.ecommerce.model.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StringToDate implements Converter<String, Date> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringToDate.class);

    @Override
    public Date convert(String date) {
        try {
            return new Date(date);
        } catch (Exception e) {
            LOGGER.error("exception occurred during string To date conversion", e);
            return null;
        }
    }
}
