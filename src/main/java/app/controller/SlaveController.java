package app.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class SlaveController {

    @CrossOrigin
    @RequestMapping(value = {"/slave", "slave"}, method = RequestMethod.POST)
    public @ResponseBody
    void getSlaves(@RequestBody Map<String, Integer> slaves) {
        System.out.println("Received slaves: ");
        for (String s : slaves.keySet()                ) {
            System.out.println(s+" = "+slaves.get(s));
        }
    }

}
