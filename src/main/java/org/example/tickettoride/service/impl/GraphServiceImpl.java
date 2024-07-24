package org.example.tickettoride.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.tickettoride.repository.RouteRepository;
import org.example.tickettoride.service.GraphService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GraphServiceImpl implements GraphService {
    private final RouteRepository routeRepository;

    /**
     * Defines the most optimal route between two towns using Dijkstra's algorithm.
     * This method finds the path with the lowest total distance.
     *
     * @param departure the name of the town where the path begins.
     * @param arrival   the name of the town where the path should end.
     * @return the shortest distance between the departure and arrival.
     * If no path can be found, the method returns -1.
     */
    @Override
    public int findMostOptimalRoute(String departure, String arrival) {
        Graph graph = new Graph();
        routeRepository.findAll().forEach(route -> {
            graph.addTown(route.getDeparture());
            graph.addTown(route.getArrival());
            graph.addEdge(route.getDeparture(), route.getArrival(), route.getSegments());
        });

        Map<String, Integer> shortestDistances = new HashMap<>();
        Set<String> processedTowns = new HashSet<>();
        PriorityQueue<TownNode> queue = new PriorityQueue<>(Comparator.comparingInt(TownNode::distance));

        shortestDistances.put(departure, 0);
        queue.add(new TownNode(departure, 0));

        while (!queue.isEmpty()) {
            TownNode currentTownNode = queue.poll();
            String currentTown = currentTownNode.town();

            if (!processedTowns.add(currentTown)) continue;

            Map<String, Integer> neighbours = graph.getNeighbouringTowns(currentTown);
            for (String neighbourTown : neighbours.keySet()) {
                Integer tentativeDistance = shortestDistances.get(currentTown) + neighbours.get(neighbourTown);
                if (tentativeDistance < shortestDistances.getOrDefault(neighbourTown, Integer.MAX_VALUE)) {
                    shortestDistances.put(neighbourTown, tentativeDistance);
                    queue.add(new TownNode(neighbourTown, tentativeDistance));
                }
            }
        }

        return shortestDistances.getOrDefault(arrival, -1);
    }

    private record TownNode(String town, Integer distance) {
    }

    private static class Graph {
        private final Map<String, Map<String, Integer>> adjacencyList = new HashMap<>();

        public void addTown(String town) {
            adjacencyList.putIfAbsent(town, new HashMap<>());
        }

        public void addEdge(String town1, String town2, Integer segments) {
            adjacencyList.get(town1).put(town2, segments);
            adjacencyList.get(town2).put(town1, segments);
        }

        public Map<String, Integer> getNeighbouringTowns(String town) {
            return adjacencyList.get(town);
        }
    }
}
