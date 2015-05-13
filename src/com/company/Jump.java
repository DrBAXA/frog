package com.company;

/**
 * Created by DrBAX_000 on 13.05.2015.
 */
public class Jump {
    int dRadius;
    int dSector;

    public Jump(int dRadius, int dSector) {
        if(dRadius > 2 || dRadius < -2) throw new RuntimeException();
        if(dSector > 3 || dSector < 1) throw new RuntimeException();
        this.dRadius = dRadius;
        this.dSector = dSector;
    }

}
