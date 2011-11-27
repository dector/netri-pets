package com.github.pmcompany.petri_net;

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

        if (id == lastId) {
            lastPlaceId = idList.getLast();
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
        lastTransitionId++;

        Transition transition = new Transition(lastTransitionId);
        transitions.put(lastTransitionId, transition);

        return transition;
    }

    public Transition removeTransition(int id) {
        LinkedList<Integer> idList = new LinkedList<Integer>(transitions.keySet());
        int lastId = idList.removeLast();

        Transition transition = transitions.remove(id);

        if (id == lastId) {
            lastTransitionId = idList.getLast();
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
        Arc arc = Arc.newInstance(from, to);

        connections.add(arc);

        return arc;
    }

    public Arc<Transition, Place> createNewConnection(Transition from, Place to) {
        Arc arc = Arc.newInstance(from, to);

        connections.add(arc);

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
        return connections.remove(connection);
    }

    public int getConnectionsCount() {
        return connections.size();
    }

//    private int countTransitionInputConnections(int transitionId, int fromPlaceId) {
//        int count = 0;
//
//        Transition t = getTransition(transitionId);
//        Iterator<Arc> iterator = t.getInputArcsIterator();
//
//        Arc<Place, Transition> connection;
//        while (iterator.hasNext()) {
//            connection = iterator.next();
//
//            if (connection.getOutputNode().getId() == fromPlaceId) {
//                count++;
//            }
//        }
//
//        return count;
//    }

//    public int[][] getDI() {
//        int[][] matrix = null;
//
//        int tCount = getTransitionsCount();
//        int pCount = getPlacesCount();
//
//        if (tCount != 0 && pCount != 0) {
//            matrix = new int[tCount][pCount];
//
//            int count;
//            int tIndex = 0;
//            int pIndex = 0;
//
//            int currTransition;
//            int currPlace;
//
//            Iterator<Integer> trIterator = transitions.keySet().iterator();
//
//            Iterator<Integer> plIterator;
//            while (trIterator.hasNext()) {
//                currTransition = trIterator.next();
//
//                plIterator = places.keySet().iterator();
//
//                while (plIterator.hasNext()) {
//                    currPlace = plIterator.next();
//
//
//
//                    pIndex++;
//                }
//
//                tIndex++;
//            }
//        }
//
//        return matrix;
//    }

    public int[][] getDI() {
        int[][] matrix = null;

        int tCount = getTransitionsCount();
        int pCount = getPlacesCount();
        int cCount = getConnectionsCount();

        if (cCount != 0 && tCount != 0 && pCount != 0) {
            matrix = new int[tCount][pCount];

            int tIndex = 0;
            int pIndex = 0;

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

                if (arc.isInput()) {        // Is transition
                    tIndex = transitionsList.indexOf(inputNode.getId());
                    pIndex = placesList.indexOf(outputNode.getId());

                    matrix[tIndex][pIndex] += arc.getCount();
                }
            }
        }

        return matrix;
    }
}
