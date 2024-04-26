package edu.iu.habahram.GumballMachine.service;

import edu.iu.habahram.GumballMachine.model.GumballMachine2;
import edu.iu.habahram.GumballMachine.model.GumballMachineRecord;
import edu.iu.habahram.GumballMachine.model.IGumballMachine;
import edu.iu.habahram.GumballMachine.model.TransitionResult;
import edu.iu.habahram.GumballMachine.repository.IGumballRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Service
public class GumballService implements IGumballService {

    private final IGumballRepository gumballRepository;

    public GumballService(IGumballRepository gumballRepository) {
        this.gumballRepository = gumballRepository;
    }

    private TransitionResult handleNullRecord(String id) {
        return new TransitionResult(false, "No gumball machine found with ID: " + id, null, 0);
    }

    private TransitionResult operateGumballMachine(String id, Function<IGumballMachine, TransitionResult> operation) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        if (record == null) {
            return handleNullRecord(id);
        }
        IGumballMachine machine = new GumballMachine2(record.getId(), record.getState(), record.getCount());
        TransitionResult result = operation.apply(machine);
        if (result.succeeded()) {
            record.setState(result.stateAfter());
            record.setCount(result.countAfter());
            save(record);
        }
        return result;
    }

    @Override
    public TransitionResult insertQuarter(String id) throws IOException {
        return operateGumballMachine(id, IGumballMachine::insertQuarter);
    }

    @Override
    public TransitionResult ejectQuarter(String id) throws IOException {
        return operateGumballMachine(id, IGumballMachine::ejectQuarter);
    }

    @Override
    public TransitionResult turnCrank(String id) throws IOException {
        return operateGumballMachine(id, machine -> {
            TransitionResult result = machine.turnCrank();
            if (result.succeeded()) {
                machine.releaseBall();
                return new TransitionResult(true, result.message(), machine.getTheStateName(), machine.getCount());
            }
            return result;
        });
    }

    @Override
    public List<GumballMachineRecord> findAll() throws IOException {
        return gumballRepository.findAll();
    }

    @Override
    public GumballMachineRecord findById(String id) throws IOException {
        return gumballRepository.findById(id);
    }

    @Override
    public String save(GumballMachineRecord gumballMachineRecord) throws IOException {
        return gumballRepository.save(gumballMachineRecord);
    }
}
