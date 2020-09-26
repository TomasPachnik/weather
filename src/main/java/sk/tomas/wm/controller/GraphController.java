package sk.tomas.wm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/graph")
public class GraphController {

    @RequestMapping
    public String index(Model model) {
        String[] labels = {"pondelok", "utorok", "streda", "štvrtok", "piatok", "sobota", "nedeľa"};
        double[] temperature = {8.62, 8.02, 13.62, 9.62, 8.62, 12.62, 18.62};
        double[] humidity = {282, 350, 411, 502, 635, 809, 947};
        double[] pressure = {168, 170, 178, 190, 203, 276, 408};
        model.addAttribute("labels", labels);
        model.addAttribute("temperature", temperature);
        model.addAttribute("humidity", humidity);
        model.addAttribute("pressure", pressure);
        return "index.html";
    }


}
