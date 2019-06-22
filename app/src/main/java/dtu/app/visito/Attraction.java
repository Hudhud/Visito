package dtu.app.visito;


public class Attraction {

    public String title, body, img;
    public float latitude, longitude;

    public Attraction (String title, String body, String img, float latitude, float longitude){
        this.title = title;
        this.body = body;
        this.img = img;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
