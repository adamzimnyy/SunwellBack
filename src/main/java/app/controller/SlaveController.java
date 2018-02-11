package app.controller;

import app.model.Slave;
import app.repository.SlaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class SlaveController {

    @Autowired
    SlaveRepository slaveRepository;

    @CrossOrigin
    @RequestMapping(value = {"/slave", "slave"}, method = RequestMethod.POST)
    public @ResponseBody
    List<Slave> getSlaves() {
       return slaveRepository.findAll();
    }

}
