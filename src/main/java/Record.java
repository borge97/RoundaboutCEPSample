public class Record {


    static int counter = 0;
    private double lat;
   private double lng;
   private double accpitch;
   private double accyaw;
   private double accroll;
   private double gyropitch;
   private double gyroyaw;
   private double gyroroll;
   private double speed;
   private double timedifference;

    public Record(double lat, double lng, double accpitch, double accyaw, double accroll, double gyropitch, double gyroyaw, double gyroroll, double speed, double timedifference) {
        this.lat = lat;
        this.lng = lng;
        this.accpitch = accpitch;
        this.accyaw = accyaw;
        this.accroll = accroll;
        this.gyropitch = gyropitch;
        this.gyroyaw = gyroyaw;
        this.gyroroll = gyroroll;
        this.speed = speed;
        this.timedifference = timedifference;
    }

    @Override
    public String toString() {
        counter++;
        return Integer.toString(counter) + ": " + Double.toString(getTimedifference());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getAccpitch() {
        return accpitch;
    }

    public void setAccpitch(double accpitch) {
        this.accpitch = accpitch;
    }

    public double getAccyaw() {
        return accyaw;
    }

    public void setAccyaw(double accyaw) {
        this.accyaw = accyaw;
    }

    public double getAccroll() {
        return accroll;
    }

    public void setAccroll(double accroll) {
        this.accroll = accroll;
    }

    public double getGyropitch() {
        return gyropitch;
    }

    public void setGyropitch(double gyropitch) {
        this.gyropitch = gyropitch;
    }

    public double getGyroyaw() {
        return gyroyaw;
    }

    public void setGyroyaw(double gyroyaw) {
        this.gyroyaw = gyroyaw;
    }

    public double getGyroroll() {
        return gyroroll;
    }

    public void setGyroroll(double gyroroll) {
        this.gyroroll = gyroroll;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getTimedifference() {
        return timedifference;
    }

    public void setTimedifference(double timedifference) {
        this.timedifference = timedifference;
    }
}
