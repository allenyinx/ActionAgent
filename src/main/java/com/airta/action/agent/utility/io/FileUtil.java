package com.airta.action.agent.utility.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    protected static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public List<String> readPredefinedUrlList(String fileName) {

        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            list = stream
//                    .filter(line -> !line.startsWith("line3"))
//                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }

//        list.forEach(System.out::println);
        return list;
    }

    public static boolean isCommonFileType(String fileName) {

        return fileName.endsWith(".pdf") || fileName.endsWith(".doc");
    }
}
