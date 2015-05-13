package com.company;

import java.util.*;

public class Solver {

    public static final int VISITED = 3;
    public static final int TREE = 1;
    public static final int FINISH = 2;
    private static final Jump[] possibleJumps = {new Jump(2, 1), new Jump(1, 2), new Jump(0, 3), new Jump(-1, 2), new Jump(-2, 1)};

    private int radiusCount;
    private int sectorCount;
    private int[][] map;

    int jumpCount = 0;



    public static void main(String[] args) {
        // write your code here

        Position frogPosition = new Position(4, 3);
        Position finish = new Position(4,2);
        Position tree1 = new Position(4,1);
        Position tree2 = new Position(4,4);
        Collection<Position> trees = new ArrayList<Position>();
        trees.add(tree1);
        trees.add(tree2);
        Solver solver = new Solver(5, 5, finish, trees);

        LinkedList<Position> initPath = new LinkedList<Position>();
        initPath.add(frogPosition);

        solver.go(initPath);

        System.out.println(solver.jumpCount);

    }

    public Solver(int radiusCount, int sectorCount, Position finish, Collection<Position> trees) {
        map = new int[radiusCount + 1][sectorCount + 1];
        this.radiusCount = radiusCount;
        this.sectorCount = sectorCount;
        markTree(trees);
        markFinish(finish);
    }

    public void go(Queue<Position> current) {
        Queue<Position> nexQUEUE = new LinkedList<Position>();

        Position position = current.poll();
        while ((position != null)){
            if(isFinish(position)) return;
            nexQUEUE.addAll(getJumpPoints(position));
            position = current.poll();
        }
        jumpCount++;
        go(nexQUEUE);

    }

    private List<Position> getJumpPoints(Position position) {
        List<Position> result = new ArrayList<Position>(5);
        for (Jump jump : possibleJumps) {
            Position jumpPosition = makeJump(position, jump);
            if (isPossible(jumpPosition)) {
                result.add(jumpPosition);
            }
        }
        return result;
    }

    private void markFinish(Position position) {
        map[position.radius][position.sector] = FINISH;
    }

    private void markTree(Collection<Position> positions) {
        for(Position position: positions) {
            map[position.radius][position.sector] = TREE;
        }
    }

    private void markVisited(Position position) {
        map[position.radius][position.sector] = VISITED;
    }

    private Position makeJump(Position position, Jump jump) {

        int newSector = position.sector + jump.dSector > sectorCount ? position.sector + jump.dSector - sectorCount : position.sector + jump.dSector;

        return new Position(position.radius + jump.dRadius, newSector);
    }

    private boolean isPossible(Position position) {
        if (position.radius > radiusCount || position.radius <= 0) return false;

        if (position.sector > sectorCount || position.sector <= 0) return false;

        if (map[position.radius][position.sector] == VISITED) return false;

        if (map[position.radius][position.sector] == TREE) return false;

        return true;
    }

    private boolean isFinish(Position position) {
        return map[position.radius][position.sector] == FINISH;
    }

}

