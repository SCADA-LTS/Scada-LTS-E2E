package org.scadalts.e2e.test.core.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.utils.FileUtil;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Log4j2
public class RegexUtil {

    public static Optional<File> getFilesFromMessage(String message, String regex) {
        logger.debug("message: {}", message);
        if(!Objects.isNull(message)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                String group = matcher.group().replace("http:", "").trim();
                File file = FileUtil.getFileFromFileSystem(group);
                return Optional.of(file);
            }
        }
        return Optional.empty();
    }
}
