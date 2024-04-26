package edu.iu.habahram.GumballMachine.model;

public class SoldOutState implements IState {
    IGumballMachine gumballMachine;

    public SoldOutState(IGumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public TransitionResult insertQuarter() {
        return new TransitionResult(false, "You can't insert a quarter, the machine is sold out", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult ejectQuarter() {
        return new TransitionResult(false, "You haven't inserted a quarter", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult turnCrank() {
        return new TransitionResult(false, "You turned, but there are no gumballs", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult dispense() {
        return new TransitionResult(false, "No gumballs to dispense", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public String getTheName() {
        return GumballMachineState.OUT_OF_GUMBALLS.name();
    }


    // In SoldOutState class
    @Override
    public void refill(int count) {
        if (count > 0) {
            gumballMachine.refill(count); // This will also transition state if necessary
        }
    }

}
