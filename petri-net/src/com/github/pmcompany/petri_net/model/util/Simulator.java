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

    private Map<Map.Entry<PetriNetState, PetriNetState>, Integer> statesComeFreq;
    private Map<PetriNetState, Integer> statesFreq;
    private Map<PetriNetState, Double> statesStayTime;
    private Map<PetriNetState, Integer> statesReturnFreq;
    private Map<PetriNetState, Double> statesReturnTime;
    private Map<PetriNetState, Double> statesOutComeTime;

    private PetriNetState prevState;
    private List<Transition> lastExecuted;

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

        statesComeFreq = new LinkedHashMap<Map.Entry<PetriNetState, PetriNetState>, Integer>();
        statesFreq = new LinkedHashMap<PetriNetState, Integer>();
        statesStayTime = new LinkedHashMap<PetriNetState, Double>();
        statesReturnFreq = new LinkedHashMap<PetriNetState, Integer>();
        statesReturnTime = new LinkedHashMap<PetriNetState, Double>();
        statesOutComeTime = new LinkedHashMap<PetriNetState, Double>();

        prevState = ptnet.getState();
        processState(prevState);

        lastExecuted = new LinkedList<Transition>();
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

        lastExecuted.clear();

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
                lastExecuted.add(trToExecute);
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
                    waitingTransitions.put(time - trTime, waitingTransitions.get(time));
                }
            }
        } else {
            done = true;
        }

        deleteNotEnabled(ptnet.getEnabledTimeTransitions());

        for (Transition t : ptnet.getEnabledImmediateTransitions()) {
            t.execute();
            lastExecuted.add(t);
        }

        System.out.printf("[END] Simulation step. TIME: %.3f, STATE: %s %n", simTime, ptnet.getState());
    }

    private void processState(PetriNetState state) {
        if (! statesFreq.containsKey(state)) {
            statesFreq.put(state, 0);
            statesStayTime.put(state, 0d);
            statesReturnFreq.put(state, 0);
            statesReturnTime.put(state, 0d);
            statesOutComeTime.put(state, simTime);
        }

        Map.Entry<PetriNetState, PetriNetState> statePair
                = new LinkedHashMap.SimpleEntry<PetriNetState, PetriNetState>(prevState, state);
        if (! statesComeFreq.containsKey(statePair)) {
            statesComeFreq.put(statePair, 1);
        } else {
            statesComeFreq.put(statePair, statesComeFreq.get(statePair) + 1);
        }

        statesFreq.put(state, statesFreq.get(state) + 1);

        if (prevState.equals(state)) {
            statesStayTime.put(state, statesStayTime.get(state) + trTime);
        } else {
            statesStayTime.put(prevState, statesStayTime.get(prevState) + trTime);

            statesOutComeTime.put(prevState, simTime - trTime);

            statesReturnFreq.put(state, statesReturnFreq.get(state) + 1);
            statesReturnTime.put(state, statesReturnTime.get(state) + simTime - statesOutComeTime.get(state));
            prevState = state;
        }
    }

    private void deleteNotEnabled(List<Transition> enabledTransitions) {
        List<Double> timesToRemove = new LinkedList<Double>();

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
                timesToRemove.add(time);

            }
        }

        for (double time : timesToRemove) {
            waitingTransitions.remove(time);
        }
    }

    public void showStatistics() {
        StringBuilder sb = new StringBuilder();

        sb.append(":: Simulation statistics ::")
                .append("\nSIMULATION TIME: ").append(simTime)
                .append("\nITERATIONS: ").append(iterations)
                .append("\nSTATES:");

        boolean first = true;

        sb.append("\n").append("State |")
                .append('\t').append("Occur num |")
                .append('\t').append("Occur probab |")
                .append('\t').append("Sum stay time |")
                .append('\t').append("Stay probab |")
                .append('\t').append("Avg stay time |")
                .append('\t').append("Return num |")
                .append('\t').append("Sum return time |")
                .append('\t').append("Return probab |")
                .append('\t').append("Avg return time");

        for (PetriNetState state : statesFreq.keySet()) {
            int freqNum = statesFreq.get(state);
            int freqRetNum = statesReturnFreq.get(state);
            double stayTime = statesStayTime.get(state);
            double retTime = statesReturnTime.get(state);

            if (first) {
                freqNum--;

                if (freqNum == 0) {
                    freqNum = 1;
                }

                first = false;
            }

            sb.append("\n").append(state)
                    .append('\t').append(freqNum)
                    .append('\t').append(String.format("%.3f", (double)freqNum / iterations * 100)).append("%")
                    .append('\t').append(stayTime)
                    .append('\t').append(String.format("%.3f", stayTime / iterations * 100)).append("%")
                    .append('\t').append(String.format("%.3f", stayTime / freqNum))
                    .append('\t').append(freqRetNum)
                    .append('\t').append(retTime)
                    .append('\t').append(String.format("%.3f", retTime / simTime * 100)).append("%")
                    .append('\t').append(String.format("%.3f", retTime / freqRetNum));
        }

        sb.append("\nSTATES COME:");

        for (Map.Entry<PetriNetState, PetriNetState> pair : statesComeFreq.keySet()) {
            sb.append("\n").append(pair.getKey().toString()).append(" --- ")
                    .append(String.format("%.3f", (double)statesComeFreq.get(pair) / (iterations - 1) * 100))
                    .append("% --->").append(pair.getValue().toString());
        }

        System.out.println(sb.toString());
    }

    public boolean wasExecuted(Transition transition) {
        return lastExecuted.contains(transition);
    }
}
