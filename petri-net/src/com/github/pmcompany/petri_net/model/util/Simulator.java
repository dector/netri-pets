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
    private boolean done;

    public Simulator(PetriNet ptnet) {
        this.ptnet = ptnet;

        init();
    }

    public void init() {
        simTime = 0;
        done = false;

        waitingTransitions = new LinkedHashMap<Double, List<Transition>>();
        waitingTransitionsList = new LinkedList<Transition>();
    }

    public void simulate(double maxTime) {
        while(!done && simTime < maxTime) {
            step();
        }
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
                double trTime = transitionsTimes.get(0);
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
//                    state = getState();
//
//                    if ()
                //    b) Add transition execution time to simulation time
                simTime += trTime;
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
}
