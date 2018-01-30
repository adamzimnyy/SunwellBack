package app.controller;

import app.model.Online;
import app.repository.OnlineRepository;
import app.util.OnlineParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OnlineController {

    @Autowired
    OnlineRepository onlineRepository;

    @CrossOrigin
    @RequestMapping(value = {"/online", "online"}, method = RequestMethod.GET)
    public @ResponseBody
    List<Online> getHistory() {
        return onlineRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = {"/online", "online"}, method = RequestMethod.POST)
    public void saveData(@RequestBody List<Online> list) {
        System.out.println("Saving online data: "+list.size()+" records.");
        for(Online o : list){
            onlineRepository.save(o);
        }
        System.out.println("Done saving.");
    }

    @CrossOrigin
    @RequestMapping(value = {"/online/now", "online/now"}, method = RequestMethod.GET)
    public @ResponseBody
    void getCurrent() {
        Online on = OnlineParser.parse();
        System.out.println("Feronis: " + on.getFeronis() + ", Angrathar: " + on.getAngrathar());
    }
}
