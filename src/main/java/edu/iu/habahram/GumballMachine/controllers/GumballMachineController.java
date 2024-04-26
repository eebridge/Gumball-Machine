package edu.iu.habahram.GumballMachine.controllers;

import edu.iu.habahram.GumballMachine.model.GumballMachineRecord;
import edu.iu.habahram.GumballMachine.model.IGumballMachine;
import edu.iu.habahram.GumballMachine.model.TransitionRequest;
import edu.iu.habahram.GumballMachine.model.TransitionResult;
import edu.iu.habahram.GumballMachine.repository.IGumballRepository;
import edu.iu.habahram.GumballMachine.service.IGumballService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/gumballs")
public class GumballMachineController {

    IGumballService gumballService;

    public GumballMachineController(IGumballService gumballService) {
        this.gumballService = gumballService;
    }

    @GetMapping
    public List<GumballMachineRecord> findAll() {
        try {
            return gumballService.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping
    public String addOrUpdate(@RequestBody GumballMachineRecord record) {
        try {
            return gumballService.save(record);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/insert-quarter")
    public TransitionResult insertQuarter(@RequestBody TransitionRequest transitionRequest) {
        try {
            return gumballService.insertQuarter(transitionRequest.id());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/eject-quarter")
    public TransitionResult ejectQuarter(@RequestBody TransitionRequest transitionRequest) {
        try {
            return gumballService.ejectQuarter(transitionRequest.id());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/turn-crank")
    public TransitionResult turnCrank(@RequestBody TransitionRequest transitionRequest) {
        try {
            return gumballService.turnCrank(transitionRequest.id());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping("/refill")
    public String refill(@RequestBody GumballMachineRecord record) {
        try {
            GumballMachineRecord gumballMachine = gumballService.findById(record.getId());
            if (gumballMachine == null) {
                return "Gumball Machine not found";
            }
            int refillCount = record.getCount(); // Use the getter method for 'count'
            gumballMachine.refill(refillCount);
            return "Gumball Machine refilled with " + refillCount + " gumballs";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
