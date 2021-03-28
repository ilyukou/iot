package by.grsu.iot.article.converter;

import by.grsu.iot.article.commonmark.ObjectRenderer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Service
public class ConverterServiceImpl implements ConverterService {

    @Override
    public String markdownToHtml(String markdown) {

        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);

    }

    @Override
    public List<Object> markdownToObject(String markdown) throws IOException {
        Parser parser = Parser.builder().build();

        Node document = parser.parseReader(new StringReader(markdown));

        ObjectRenderer renderer = ObjectRenderer.builder().build();
        renderer.render(document);
        List<Object> nodes = renderer.getNodes();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return nodes;
    }
}
