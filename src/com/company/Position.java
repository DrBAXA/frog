package com.company;

class Position{
    int radius;
    int sector;

    public Position(int radius, int sector) {
        this.radius = radius;
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "{" + radius +"," + sector +'}';
    }
}
