package by.grsu.iot.api.controller.article;

import by.grsu.iot.article.converter.ConverterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ConverterService converterService;

    public ArticleController(ConverterService converterService) {
        this.converterService = converterService;
    }

    @PostMapping("markdownToObject")
    public ResponseEntity<List<Object>> markdownToObject(
            @RequestBody String markdown
    ) throws IOException {
        return new ResponseEntity<>(
                converterService.markdownToObject(markdown),
                HttpStatus.OK);
    }

    @PostMapping("markdownToHtml")
    public ResponseEntity<String> markdownToHtml(
            @RequestBody String markdown
    ) {
        return new ResponseEntity<>(
                converterService.markdownToHtml(markdown),
                HttpStatus.OK);
    }
}
