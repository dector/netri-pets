package com.github.pmcompany.petri_net.model;

import com.github.pmcompany.petri_net.model.util.PetriNetState;

import java.util.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class PetriNet {
    private Map<Integer, Place> places;
    private Map<Integer, Transition> transitions;
    private List<Arc> connections;

    private int lastPlaceId;
    private int lastTransitionId;

    public PetriNet() {
        init();
    }

    private void init() {
        places = new LinkedHashMap<Integer, Place>();
        transitions = new LinkedHashMap<Integer, Transition>();
        connections = new LinkedList<Arc>();

        lastPlaceId = 0;
        lastTransitionId = 0;
    }

    public int getPlacesCount() {
        return places.size();
    }

    public Place createNewPlace() {
        lastPlaceId++;

        Place place = new Place(lastPlaceId);
        places.put(lastPlaceId, place);

        return place;
    }

    public Place removePlace(int id) {
        LinkedList<Integer> idList = new LinkedList<Integer>(places.keySet());
        int lastId = idList.removeLast();

        Place place = places.remove(id);

        connections.removeAll(place.getInputArcs());
        connections.removeAll(place.getOutputArcs());
        place.clearAllConnections();

        if (id == lastId) {
            lastPlaceId = (!idList.isEmpty()) ? idList.getLast() : 0;
        }

        return place;
    }

    private Place getPlace(int id) {
        Place p = null;

        if (places.containsKey(id)) {
            p = places.get(id);
        }

        return p;
    }

    public Transition createNewTransition() {
        return createNewTransition(0);
    }

    public Transition createNewTransition(double time) {
        lastTransitionId++;

        Transition transition = new Transition(lastTransitionId, time);
        transitions.put(lastTransitionId, transition);

        return transition;
    }

    public Transition removeTransition(int id) {
        LinkedList<Integer> idList = new LinkedList<Integer>(transitions.keySet());
        int lastId = idList.removeLast();

        Transition transition = transitions.remove(id);
        connections.removeAll(transition.getInputArcs());
        connections.removeAll(transition.getOutputArcs());
        transition.clearAllConnections();

        if (id == lastId) {
            lastTransitionId = (!idList.isEmpty()) ? idList.getLast() : 0;
        }

        return transition;
    }

    public Transition getTransition(int id) {
        Transition t = null;

        if (transitions.containsKey(id)) {
            t = transitions.get(id);
        }

        return t;
    }

    public int getTransitionsCount() {
        return transitions.size();
    }

    public Arc<Place, Transition> createNewConnection(Place from, Transition to) {
        Arc arc = from.getConnectionTo(to);

        if (arc == null) {
            arc = Arc.newInstance(from, to);
            connections.add(arc);
        } else {
            arc.incrementCount();
        }

        return arc;
    }

    public Arc<Transition, Place> createNewConnection(Transition from, Place to) {
        Arc arc = from.getConnectionTo(to);

        if (arc == null) {
            arc = Arc.newInstance(from, to);
            connections.add(arc);
        } else {
            arc.incrementCount();
        }

        return arc;
    }

    public Arc<Place, Transition> createInputConnection(int fromPlaceId, int toTransitionId) {
        Arc arc = null;

        if (places.containsKey(fromPlaceId) && transitions.containsKey(toTransitionId)) {
            arc = createNewConnection(getPlace(fromPlaceId), getTransition(toTransitionId));
        }

        return arc;
    }

    public Arc<Place, Transition> createOutputConnection(int toPlaceId, int fromTransitionId) {
        Arc arc = null;

        if (transitions.containsKey(fromTransitionId) && places.containsKey(toPlaceId)) {
            arc = createNewConnection(getTransition(fromTransitionId), getPlace(toPlaceId));
        }

        return arc;
    }

    public boolean removeConnection(Arc connection) {
        connection.clearConnection();
        return connections.remove(connection);
    }

    public int getConnectionsCount() {
        return connections.size();
    }

    public void updateTokensNumber(int placeId, int tokens) {
        getPlace(placeId).setTokens(tokens);
    }

    public void updateTransitionTime(int transitionId, double time) {
        getTransition(transitionId).setTime(time);
    }


    public void updateConnectionsNumber(Arc arc, int connections) {
        if (connections > 0) {
            arc.setCount(connections);
        }
    }

    public int getTokensNumber(int placeId) {
        if (places.containsKey(placeId)) {
            return getPlace(placeId).countTokens();
        } else {
            return -1;
        }
    }

    public double getTransitionTime(int transitionId) {
        if (transitions.containsKey(transitionId)) {
            return getTransition(transitionId).getTime();
        } else {
            return -1;
        }
    }

    private int[][] getMatrix(boolean input) {
        int[][] matrix = null;

        int tCount = getTransitionsCount();
        int pCount = getPlacesCount();
        int cCount = getConnectionsCount();

        if (cCount != 0 && tCount != 0 && pCount != 0) {
            matrix = new int[tCount][pCount];

            int tIndex;
            int pIndex;

            LinkedList<Integer> placesList = new LinkedList<Integer>(places.keySet());
            LinkedList<Integer> transitionsList = new LinkedList<Integer>(transitions.keySet());

            ListIterator<Arc> iter = connections.listIterator();

            Arc arc;
            Node inputNode;
            Node outputNode;
            while (iter.hasNext()) {
                arc = iter.next();
                inputNode = arc.getInputNode();
                outputNode = arc.getOutputNode();

                if (input) {
                    if (arc.isInput()) {        // Is input to transition
                        tIndex = transitionsList.indexOf(inputNode.getId());
                        pIndex = placesList.indexOf(outputNode.getId());

                        matrix[tIndex][pIndex] += arc.getCount();
                    }
                } else {
                    if (!arc.isInput()) {        // Is input to place
                        tIndex = transitionsList.indexOf(outputNode.getId());
                        pIndex = placesList.indexOf(inputNode.getId());

                        matrix[tIndex][pIndex] += arc.getCount();
                    }
                }
            }
        }

        return matrix;
    }

    public int[][] getDI() {
        return getMatrix(true);
    }

    public int[][] getDQ() {
        return getMatrix(false);
    }

    public int[][] getDR() {
        int[][] matrix = null;

        int[][] dq = getDQ();
        int[][] di = getDI();


        if (dq != null && di != null) {
            int tCount = getTransitionsCount();
            int pCount = getPlacesCount();

            matrix = new int[tCount][pCount];

            for (int i = 0; i < tCount; i++) {
                for (int j = 0; j < pCount; j++) {
                    matrix[i][j] = dq[i][j] - di[i][j];
                }
            }
        }

        return matrix;
    }

    public String[] getPlacesTitlesVector() {
        String[] titles = null;

        if (getPlacesCount() != 0) {
            titles = new String[getPlacesCount()];

            Iterator<Place> iter = places.values().iterator();
            int index = 0;

            Place place;
            while (iter.hasNext()) {
                place = iter.next();

                titles[index++] = place.toString();
            }
        }

        return titles;
    }

    public int[] getPlacesVector() {
        int[] pVector = null;

        if (getPlacesCount() != 0) {
            pVector = new int[getPlacesCount()];

            Iterator<Place> iter = places.values().iterator();
            int index = 0;

            Place place;
            while (iter.hasNext()) {
                place = iter.next();

                pVector[index++] = place.countTokens();
            }
        }

        return pVector;
    }

    public String[] getTransitionsTitlesVector() {
        String[] titles = null;

        if (getTransitionsCount() != 0) {
            titles = new String[getTransitionsCount()];

            Iterator<Transition> iter = transitions.values().iterator();
            int index = 0;

            Transition transition;
            while (iter.hasNext()) {
                transition = iter.next();

                titles[index++] = transition.toString();
            }
        }

        return titles;
    }

    public double[] getTransitionsTimes() {
        double[] tVector = null;

        if (getTransitionsCount() != 0) {
            tVector = new double[getTransitionsCount()];

            Iterator<Transition> iter = transitions.values().iterator();
            int index = 0;

            Transition transition;
            while (iter.hasNext()) {
                transition = iter.next();

                tVector[index++] = transition.getTime();
            }
        }
        return tVector;
    }

    public Map<Double, List<Transition>> getEnabledTimeTransitionsMap() {
        Map<Double, List<Transition>> map = new LinkedHashMap<Double, List<Transition>>();

        double time;
        for (Transition t : getEnabledTimeTransitions()) {
            time = t.getTime();

            if (! map.containsKey(time)) {
                map.put(time, new LinkedList<Transition>());
            }

            map.get(time).add(t);
        }

        return map;
    }

    public ArrayList<Transition> getEnabledTimeTransitions() {
        ArrayList<Transition> enabledTransitions = new ArrayList<Transition>();
        Set<Integer> keyset = transitions.keySet();
        for (Integer key : keyset) {
            Transition trans = transitions.get(key);
            if (trans.isEnabled() && !trans.isMomentary()) {
                enabledTransitions.add(trans);
            }
        }
        return enabledTransitions;
    }

    public boolean isTemporary() {
        return !getEnabledImmediateTransitions().isEmpty();
    }

    public ArrayList<Transition> getEnabledImmediateTransitions() {
        ArrayList<Transition> enabledTransitions = new ArrayList<Transition>();
        Set<Integer> keyset = transitions.keySet();
        for (Integer key : keyset) {
            Transition trans = transitions.get(key);
            if (trans.isEnabled() && trans.isMomentary()) {
                enabledTransitions.add(trans);
            }
        }
        return enabledTransitions;
    }

    public PetriNetState getState() {
        return new PetriNetState(getPlacesVector());
    }

    public void setState(PetriNetState state) {
        Set<Integer> keyset = places.keySet();
        Integer[] keys = new Integer[keyset.size()];
        keyset.toArray(keys);
        Arrays.sort(keys);
        int c = 0;
        for (Integer key : keys) {
            places.get(key).setTokens(state.getTokens(c++));
        }
    }

    public void simulate(double maxTime) {
        double simTime = 0;

//        Map<PetriNetState, Integer> statesFreq = new LinkedHashMap<PetriNetState, Integer>();
//        Map<PetriNetState, Double> statesStayTime = new LinkedHashMap<PetriNetState, Double>();
//        Map<PetriNetState, Double> statesReturnTime = new LinkedHashMap<PetriNetState, Double>();
//        Map<PetriNetState, Map.Entry<Boolean, Double>> statesInOutComeTime =
//                new LinkedHashMap<PetriNetState, Map.Entry<Boolean, Double>>();

        boolean done = false;

//        List<Transition> waitingTransitionsList = new LinkedList<Transition>();
        Map<Double, List<Transition>> waitingTransitions = new LinkedHashMap<Double, List<Transition>>();
                                
//        double actionTime = 0;
        while(!done && simTime < maxTime) {
//            PetriNetState state = getState();
//
//            if (! statesFreq.containsKey(state)) {
//                statesFreq.put(state, 1);
//                statesStayTime.put(state, 0d);
//                statesReturnTime.put(state, 0d);
//                statesInOutComeTime.put(state, new HashMap.SimpleEntry<Boolean, Double>(true, simTime));
//            }



            // ===================

//            Map<Double, List<Transition>> enabledTrans = getEnabledTimeTransitionsMap();
//            List<Double> transitionsTimes = new LinkedList<Double>(enabledTrans.keySet());
//            Collections.sort(transitionsTimes);
//
//            List<Transition> transitionsToExecute;
//            for (double trTime : transitionsTimes) {
//                transitionsToExecute = enabledTrans.get(trTime);
//
//                if (transitionsToExecute.size() > 1) {
//
//                } else {
//                    transitionsToExecute.get(0).execute();
//                }
//
//                simTime += actionTime;
//                timePassed += actionTime;
//            }
//
//            for (Transition t : getEnabledImmediateTransitions()) {
//                t.execute();
//            }
        }
    }
}
