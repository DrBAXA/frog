package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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



    public static void main(String[] args) throws IOException {
        int roundCount = 0;
        int sectorCount = 0;
        Position finish = null;
        Position frogPosition = null;


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean badInput = true;
        while ( badInput) {
            try {
                System.out.println("Введіть кількість кілець");
                roundCount = Integer.parseInt(br.readLine());
                badInput = false;
            } catch (NumberFormatException ioe) {
                System.out.println("Неправильне введення.");
            }
        }

        badInput = true;
        while ( badInput) {
            try {
                System.out.println("Введіть кількість секторів");
                sectorCount = Integer.parseInt(br.readLine());
                badInput = false;
            } catch (NumberFormatException ioe) {
                System.out.println("Неправильне введення.");
            }
        }

        badInput = true;
        while ( badInput) {
            try {
                System.out.println("Введіть фінішну точку(цифри через кому)");
                String finishString = br.readLine();
                finish = new Position(Integer.parseInt(finishString.split(",")[0]), Integer.parseInt(finishString.split(",")[1]));
                badInput = false;
            } catch (NumberFormatException ioe) {
                System.out.println("Неправильне введення.");
            }
        }

        badInput = true;
        while ( badInput) {
            try {
                System.out.println("Введіть початкову точку(цифри через кому)");
                String startStr = br.readLine();
                frogPosition = new Position(Integer.parseInt(startStr.split(",")[0]), Integer.parseInt(startStr.split(",")[1]));
                badInput = false;
            } catch ( NumberFormatException ioe) {
                System.out.println("Неправильне введення.");
            }
        }

        System.out.println("Введіть положення дерев((цифри через кому, після кожного введеного дерева натисніть ентер.");
        System.out.println("Для завершення введеня введіть число 0");
        Collection<Position> trees = new ArrayList<Position>();
        String treesStr = br.readLine();
        while (! treesStr.equals("0")){
            badInput = true;
            while ( badInput) {
                try {
                    Position treePos = new Position(Integer.parseInt(treesStr.split(",")[0]), Integer.parseInt(treesStr.split(",")[1]));
                    trees.add(treePos);
                    badInput = false;
                } catch ( NumberFormatException ioe) {
                    System.out.println("Неправильне введення.");
                }
                treesStr = br.readLine();
            }
        }


        Solver solver = new Solver(roundCount, sectorCount, finish, trees);

        LinkedList<Position> initPath = new LinkedList<Position>();
        initPath.add(frogPosition);

        solver.go(initPath);

        List<Position> result = solver.go(initPath);

        for(int i = result.size() - 1; i >= 0; i--){
            System.out.println(result.get(i));
        }

    }

    public Solver(int radiusCount, int sectorCount, Position finish, Collection<Position> trees) {
        map = new int[radiusCount + 1][sectorCount + 1];
        this.radiusCount = radiusCount;
        this.sectorCount = sectorCount;
        markTree(trees);
        markFinish(finish);
    }

    public LinkedList<Position> go(LinkedList<Position> path) {
        LinkedList<Position> result;

        LinkedList<Position> newPath = new LinkedList<Position>(path);

        if (isFinish(path.get(0))) return path;

        markVisited(path.element());

        for (Position position : getJumpPoints(path.element())) {
            newPath.add(0, position);
            result = go(newPath);
            if(result != null) return result;
            newPath.remove(0);
        }

        return null;
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

