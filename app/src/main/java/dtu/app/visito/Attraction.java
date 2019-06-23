package dtu.app.visito;


import java.io.Serializable;

public class Attraction implements Serializable {

    private String title, body, img;
    private float latitude, longitude;

    public Attraction (String title, String body, String img, float latitude, float longitude){
        this.title = title;
        this.body = body;
        this.img = img;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Attraction() {
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImg() {
        return img;
    }

    public float getLatitude() {
        return latitude;
    }


    public float getLongitude() {
        return longitude;
    }


}
