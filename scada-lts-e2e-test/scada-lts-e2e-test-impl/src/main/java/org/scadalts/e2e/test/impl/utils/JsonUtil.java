package org.scadalts.e2e.test.impl.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.json.DataSourceCriteriaJson;
import org.scadalts.e2e.page.impl.criterias.json.IdentifierJson;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class JsonUtil {

    public static File serialize(List<UpdateDataSourceCriteria> criterias, String fileName) {

        List<DataSourceCriteriaJson> jsonCriterias = criterias.stream()
                .map(a -> DataSourceCriteriaJson.builder()
                                            .enabled(a.isEnabled())
                                            .identifier(new IdentifierJson<>(a.getIdentifier()))
                                            .build())
                .collect(Collectors.toList());
        try {
            File jsonFile = _preparingFile(fileName);
            _serialize(jsonFile, jsonCriterias);
            return jsonFile;
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            return new File(fileName);
        }
    }

    public static List<DataSourceCriteriaJson> deserialize(String fileName) {
        try {
            return _deserialize(fileName);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private static List<DataSourceCriteriaJson> _deserialize(String fileName) throws IOException {
        File json = _preparingFile(fileName);
        if(json.length() == 0) {
            logger.warn("File is empty: {}", fileName);
            return Collections.emptyList();
        }
        return _parse(json);
    }

    private static List<DataSourceCriteriaJson> _parse(File json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.readValue(json, new TypeReference<List<DataSourceCriteriaJson>>() {});
    }

    private static File _preparingFile(String fileName) throws IOException {
        File json = new File(fileName);
        File path = new File(json.getParent());
        if(!path.exists()) {
            path.mkdirs();
        }
        if(!json.exists()) {
            json.createNewFile();
        }
        return json;
    }

    private static void _serialize(File json, List<DataSourceCriteriaJson> criterias) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(json, criterias);
    }
}
