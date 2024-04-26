package edu.iu.habahram.GumballMachine.model;

public class GumballMachine implements IGumballMachine {
    final String SOLD_OUT = GumballMachineState.OUT_OF_GUMBALLS.name();
    final String NO_QUARTER = GumballMachineState.NO_QUARTER.name();
    final String HAS_QUARTER = GumballMachineState.HAS_QUARTER.name();
    final String SOLD = GumballMachineState.GUMBALL_SOLD.name();
    private String id;
    String state = SOLD_OUT;
    int count = 0;

    public GumballMachine(String id, String state, int count) {
        this.id = id;
        this.state = state;
        this.count = count;
    }

    @Override
    public TransitionResult insertQuarter() {
        boolean succeeded = false;
        String message = "";
        if (state.equalsIgnoreCase(HAS_QUARTER)) {
            message = "You can't insert another quarter";
        } else if (state.equalsIgnoreCase(NO_QUARTER)) {
            state = HAS_QUARTER;
            message = "You inserted a quarter";
            succeeded = true;
        } else if (state.equalsIgnoreCase(SOLD_OUT)) {
            message = "You can't insert a quarter, the machine is sold out";
        } else if (state.equalsIgnoreCase(SOLD)) {
            message = "Please wait, we're already giving you a gumball";
        }
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public TransitionResult ejectQuarter() {
        boolean succeeded = false;
        String message = "";
        if (state.equalsIgnoreCase(HAS_QUARTER)) {
            state = NO_QUARTER;
            message = "Returned quarter";
            succeeded = true;
        } else {
            message = "Can't return quarter";
        }
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public TransitionResult turnCrank() {
        boolean succeeded = false;
        String message = "";
        if (state.equalsIgnoreCase(HAS_QUARTER)) {
            if (count > 0) {
                state = SOLD;
                message = "Turned crank and dispensing gumball";
                succeeded = true;
            } else {
                state = SOLD_OUT;
                message = "No gumballs remaining";
            }
        } else if (state.equalsIgnoreCase(NO_QUARTER)) {
            message = "Insert a quarter first";
        } else {
            message = "Invalid operation";
        }
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public void releaseBall() {
        if (count > 0) {
            count--;
            if (count == 0) {
                state = SOLD_OUT;
            } else {
                state = NO_QUARTER;
            }
        }
    }

    @Override
    public void refill(int count) {
        this.count += count; // Add the count to the current count
        if (this.count > 0 && state.equals(GumballMachineState.OUT_OF_GUMBALLS.name())) {
            state = GumballMachineState.NO_QUARTER.name();
        }
    }

    @Override
    public void setCount(int count) {
        this.count = count; // Set the internal count
    }

    @Override
    public void changeTheStateTo(GumballMachineState name) {

    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public String getTheStateName() {
        return state;
    }



}
