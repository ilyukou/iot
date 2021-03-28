package by.grsu.iot.article.converter;

import java.io.IOException;
import java.util.List;

public interface ConverterService {

    String markdownToHtml(String markdown);

    List<Object> markdownToObject(String markdown) throws IOException;
}
