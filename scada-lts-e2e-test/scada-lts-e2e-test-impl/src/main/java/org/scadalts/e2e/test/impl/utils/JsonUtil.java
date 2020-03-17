package org.scadalts.e2e.test.impl.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.json.DataSourceCriteriaJson;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
public class JsonUtil {

    public static File toJsonFile(List<DataSourceCriteria> criterias, String fileName) {

        File json = _preparingFile(fileName);

        try {
            _serialize(criterias, json);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
        return json;
    }

    public static List<DataSourceCriteriaJson> toList(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        File json = new File(fileName);
        try {
            return objectMapper.readValue(json, new TypeReference<List<DataSourceCriteriaJson>>() {});
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private static File _preparingFile(String fileName) {
        File json = new File(fileName);
        File path = new File(json.getParent());
        if(!path.exists()) {
            path.mkdirs();
        }
        return json;
    }

    private static void _serialize(List<DataSourceCriteria> criterias, File json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(json, criterias);
    }
}
