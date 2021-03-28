package by.grsu.iot.article.commonmark;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonmark.renderer.html.HtmlWriter;
import org.springframework.util.CommonsLogWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ObjectWriter extends HtmlWriter {

    private static Log log = LogFactory.getLog(ObjectWriter.class);

    private List<Object> nodes = new ArrayList<>();

    public ObjectWriter() {
        super(new CommonsLogWriter(log));
    }

    @Override
    public void raw(String s) {
        nodes.add(Collections.singletonMap("raw", s));
    }

    @Override
    public void text(String text) {
        nodes.add(Collections.singletonMap("text", text));
    }

    @Override
    public void tag(String name) {
    }

    @Override
    public void tag(String name, Map<String, String> attrs) {
        nodes.add(Collections.singletonMap(name, attrs));
    }

    @Override
    public void tag(String name, Map<String, String> attrs, boolean voidElement) {
        tag(name, attrs);
    }

    @Override
    public void line() {
    }

    @Override
    protected void append(String s) {
        nodes.add(Collections.singletonMap("line", s));
    }

    public List<Object> getNodes() {
        return nodes;
    }
}