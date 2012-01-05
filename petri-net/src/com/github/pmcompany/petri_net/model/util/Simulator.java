package com.github.pmcompany.petri_net.model.util;

import com.github.pmcompany.petri_net.model.PetriNet;
import com.github.pmcompany.petri_net.model.Transition;

import java.util.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class Simulator {
    private PetriNet ptnet;

    private List<Transition> waitingTransitionsList;
    private Map<Double, List<Transition>> waitingTransitions;

    private double simTime;
    private double trTime;
    private boolean done;

    private int iterations;

    private Map<PetriNetState, Integer> statesFreq;
    private Map<PetriNetState, Double> statesStayTime;
    private Map<PetriNetState, Double> statesReturnTime;
    private Map<PetriNetState, Double> statesOutComeTime;

    private PetriNetState prevState;

    public Simulator(PetriNet ptnet) {
        this.ptnet = ptnet;

        init();
    }

    public void init() {
        simTime = 0;
        trTime = 0;
        done = false;

        iterations = 0;

        waitingTransitions = new LinkedHashMap<Double, List<Transition>>();
        waitingTransitionsList = new LinkedList<Transition>();

        statesFreq = new LinkedHashMap<PetriNetState, Integer>();
        statesStayTime = new LinkedHashMap<PetriNetState, Double>();
        statesReturnTime = new LinkedHashMap<PetriNetState, Double>();
        statesOutComeTime = new LinkedHashMap<PetriNetState, Double>();

        prevState = ptnet.getState();
        processState(prevState);
    }

    public void simulate(double maxTime) {
        System.out.printf("Starting simulation for %.3f%n", maxTime);

        while(!done && simTime < maxTime) {
            step();
        }

        System.out.printf("Simulation %s%n", done ? "terminated" : "finished");

        showStatistics();
    }

    public void step() {
        System.out.printf("[BEGIN] Simulation step. TIME: %.3f, STATE: %s %n", simTime, ptnet.getState());

        // 1) Get all enabled time transitions
        List<Transition> enabledTransitions = ptnet.getEnabledTimeTransitions();

        if (! enabledTransitions.isEmpty()) {
            System.out.println("Enabled transitions: " + enabledTransitions);
            // 2) Remove from waitingTransitions transitions, which are not enabled
            deleteNotEnabled(enabledTransitions);

            for (Transition t : enabledTransitions) {
                if (! waitingTransitionsList.contains(t)) {
                    waitingTransitionsList.add(t);

                    double tTime = t.getTime();
                    if (! waitingTransitions.containsKey(tTime)) {
                        waitingTransitions.put(tTime, new LinkedList<Transition>());
                    }

                    waitingTransitions.get(tTime).add(t);
                }
            }

            // 3) Sort waiting Transitions by time
            List<Double> transitionsTimes = new LinkedList<Double>(waitingTransitions.keySet());
            Collections.sort(transitionsTimes);

            if (! transitionsTimes.isEmpty()) {
                // 4) Select the fastest transition
                trTime = transitionsTimes.get(0);
                List<Transition> willBeNowExecutedTransitions = waitingTransitions.get(trTime);

                Transition trToExecute;

                int transitionIndex;

                // 5) If transition is NOT single - select one
                if (willBeNowExecutedTransitions.size() > 1) {
                    transitionIndex = new Random().nextInt(willBeNowExecutedTransitions.size());
                } else {
                    transitionIndex = 0;
                }

                trToExecute = willBeNowExecutedTransitions.get(transitionIndex);

                // 6) Execute transition
                //    a) Execute
                trToExecute.execute();
                //    b) Add transition execution time to simulation time
                simTime += trTime;
                processState(ptnet.getState());
                iterations++;
                //    c) Delete executed transition from waiting
                waitingTransitionsList.remove(trToExecute);
                waitingTransitions.get(trTime).remove(transitionIndex);
                if (waitingTransitions.get(trTime).isEmpty()) {
                    waitingTransitions.remove(trTime);
                }
                //    d) Decrease transitions waiting time
                for (double time : waitingTransitions.keySet()) {
                    waitingTransitions.put(time - trTime, waitingTransitions.remove(time));
                }
            }
        } else {
            done = true;
        }

        deleteNotEnabled(ptnet.getEnabledTimeTransitions());

        for (Transition t : ptnet.getEnabledImmediateTransitions()) {
            t.execute();
        }

        System.out.printf("[END] Simulation step. TIME: %.3f, STATE: %s %n", simTime, ptnet.getState());
    }

    private void processState(PetriNetState state) {
        if (! statesFreq.containsKey(state)) {
            statesFreq.put(state, 0);
            statesStayTime.put(state, 0d);
            statesReturnTime.put(state, 0d);
            statesOutComeTime.put(state, simTime);
        }

        statesFreq.put(state, statesFreq.get(state) + 1);

        if (prevState.equals(state)) {
            statesStayTime.put(state, statesStayTime.get(state) + trTime);
        } else {
            statesStayTime.put(prevState, statesStayTime.get(prevState) + trTime);

            statesOutComeTime.put(prevState, simTime - trTime);

            statesReturnTime.put(state, statesReturnTime.get(state) + simTime - statesOutComeTime.get(state));
            prevState = state;
        }
    }

    private void deleteNotEnabled(List<Transition> enabledTransitions) {
        for (double time : waitingTransitions.keySet()) {
            List<Transition> wTrans = waitingTransitions.get(time);

            List<Transition> transToRemove = new LinkedList<Transition>();

            for (Transition t : wTrans) {
                if (! enabledTransitions.contains(t)) {
                    transToRemove.add(t);
                    waitingTransitionsList.remove(t);
                }
            }

            wTrans.removeAll(transToRemove);

            if (wTrans.size() == 0) {
                waitingTransitions.remove(time);
            }
        }
    }

    public void showStatistics() {
        StringBuilder sb = new StringBuilder();

        sb.append(":: Simulation statistics ::")
                .append("\nSIMULATION TIME: ").append(simTime)
                .append("\nITERATIONS: ").append(iterations)
                .append("\nSTATES:");

        for (PetriNetState state : statesFreq.keySet()) {
            sb.append("\n").append(state)
                    .append('\t').append(statesFreq.get(state))
                    .append('\t').append(statesStayTime.get(state))
                    .append('\t').append(statesReturnTime.get(state));
        }

        System.out.println(sb.toString());
    }
}
