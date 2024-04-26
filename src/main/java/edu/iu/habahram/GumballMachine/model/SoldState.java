package edu.iu.habahram.GumballMachine.model;

public class SoldState implements IState {
    IGumballMachine gumballMachine;

    @Override
    public void refill(int count) {
        // Simply delegates the refill to the machine.
        gumballMachine.refill(count);
    }


    public SoldState(IGumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public TransitionResult insertQuarter() {
        return new TransitionResult(false, "Please wait, we're already giving you a gumball", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult ejectQuarter() {
        return new TransitionResult(false, "Sorry, you already turned the crank", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult turnCrank() {
        return new TransitionResult(false, "Turning again doesn't get you another gumball!", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult dispense() {
        gumballMachine.releaseBall();
        if (gumballMachine.getCount() > 0) {
            gumballMachine.changeTheStateTo(GumballMachineState.NO_QUARTER);
        } else {
            gumballMachine.changeTheStateTo(GumballMachineState.OUT_OF_GUMBALLS);
        }
        return new TransitionResult(true, "A gumball comes rolling out the slot", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public String getTheName() {
        return GumballMachineState.GUMBALL_SOLD.name();
    }
}
