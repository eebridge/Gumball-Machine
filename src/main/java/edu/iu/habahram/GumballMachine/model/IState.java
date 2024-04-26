package edu.iu.habahram.GumballMachine.model;

// IState interface without setCount method
public interface IState {
    void refill(int count);
    TransitionResult insertQuarter();
    TransitionResult ejectQuarter();
    TransitionResult turnCrank();
    TransitionResult dispense();
    String getTheName();
}
