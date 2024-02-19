package ca.ulaval.glo2003;

public class Reservation {
    private Integer duration;

    public Reservation() {}

    public Reservation(Integer duration) {
        setDuration(duration);
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
