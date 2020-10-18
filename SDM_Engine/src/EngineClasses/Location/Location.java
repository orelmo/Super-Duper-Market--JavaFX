package EngineClasses.Location;

import Exceptions.LocationException;

import java.util.Objects;

public class Location {
    private int x;
    private int y;

    public Location(jaxbClasses.Location location) {
        setX(location.getX());
        setY(location.getY());
    }

    public Location(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        if (isValidCoordinate(x) == false) {
            throw new LocationException("x coordinate is out of range");
        }
        this.x = x;
    }

    public void setY(int y) {
        if (isValidCoordinate(y) == false) {
            throw new LocationException("y coordinate is out of range");
        }
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    private boolean isValidCoordinate(int coord) {
        return coord >= 1 && coord <= 50;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}