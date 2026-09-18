package ccrm.domain;

public enum Grade {
    S(10.0, "S - Outstanding"),
    A(9.0, "A - Excellent"),
    B(8.0, "B - Very Good"),
    C(7.0, "C - Good"),
    D(6.0, "D - Average"),
    E(5.0, "E - Below Average"),
    F(0.0, "F - Fail");
    
    private final double points;
    private final String description;
    
    Grade(double points, String description) {
        this.points = points;
        this.description = description;
    }
    
    public double getPoints() {
        return points;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static Grade fromPoints(double points) {
        if (points >= 9.5) return S;
        if (points >= 8.5) return A;
        if (points >= 7.5) return B;
        if (points >= 6.5) return C;
        if (points >= 5.5) return D;
        if (points >= 4.5) return E;
        return F;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
