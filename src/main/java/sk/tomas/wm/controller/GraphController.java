package sk.tomas.wm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/graph")
public class GraphController {


    @RequestMapping
    public String index() {
        return "index.html";
    }


}
