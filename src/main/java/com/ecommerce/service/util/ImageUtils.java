package com.ecommerce.service.util;


import com.ecommerce.model.Image;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public final class ImageUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtils.class);
    private static JsonFactory factory = new JsonFactory();


    public static String imageToJson(List<Image> gallery) {
        StringWriter stringWriter = new StringWriter();
        try (JsonGenerator generator = factory.createJsonGenerator(stringWriter)) {
            generator.writeStartArray();
            for (Image img : gallery) {
                generator.writeStartObject();
                generator.writeStringField("name", img.getId().toString());
                generator.writeStringField("type", "image/" + StringUtils.getFilenameExtension(img.getImagePath()));
                generator.writeStringField("file", "/uploads/" + img.getImagePath());
                generator.writeEndObject();
            }
            generator.writeEndArray();
        } catch (IOException e) {
            stringWriter.getBuffer().setLength(0);
            LOGGER.error("error during converting to json", e);
        }
        return stringWriter.toString();
    }
}
