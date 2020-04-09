package org.scadalts.e2e.test.impl.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.json.DataSourceCriteriaJson;
import org.scadalts.e2e.page.impl.criterias.json.IdentifierJson;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class JsonUtil {

    public static File toJsonFile(List<DataSourceCriteria> criterias, String fileName) {

        File jsonFile = _preparingFile(fileName);
        List<DataSourceCriteriaJson> jsonCriterias = criterias.stream()
                .map(a -> DataSourceCriteriaJson.builder()
                                            .enabled(a.isEnabled())
                                            .identifier(new IdentifierJson<>(a.getIdentifier()))
                                            .build())
                .collect(Collectors.toList());
        try {
            _serialize(jsonFile, jsonCriterias);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
        return jsonFile;
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

    private static void _serialize(File json, List<DataSourceCriteriaJson> criterias) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(json, criterias);
    }
}
