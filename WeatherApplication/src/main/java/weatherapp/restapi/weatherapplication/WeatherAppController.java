package weatherapp.restapi.weatherapplication;

import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.google.gson.Gson;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class WeatherAppController {


    public TextField LonTF;
    public Label ResultLB;
    public TextField LatTF;
    public TextField addressTF;

    public void getWeatherData(ActionEvent actionEvent) throws IOException, InterruptedException {
        String Result="";
        String Country="";
        String address = addressTF.getText();

        Transcript weatherResult = new Transcript();
        Gson gson = new Gson(); //Gson is used for conversion between class object and JSON data format
        HttpClient client = HttpClient.newHttpClient();

        System.out.println("\naddress TF: "+ address+"\n");
        if (!address.isEmpty()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openweathermap.org/geo/1.0/direct?q="+address+"&limit=1&appid=Your_API_Key"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            //Since the response JSON data starting with "[", which represents it is returning as JSON array,
            //therefore they should be extract to List instead of a single object.
            Type locationListType = new TypeToken<List<Location>>(){}.getType();
            List<Location> locationList = gson.fromJson(response.body(), locationListType);

            LatTF.setText(String.valueOf(locationList.get(0).getLat()));
            LonTF.setText(String.valueOf(locationList.get(0).getLon()));
            Country = locationList.get(0).getCountry();
            System.out.println("Country: "+Country);
        }

        String lat = LatTF.getText();
        String lon = LonTF.getText();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&APPID=Your_API_Key" + "&units=metric"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //BodyHandlers.ofString is the response java will be receiving, which can be extract by body()

        System.out.println(response.body());
        System.out.println("\n /////////////////////////////////\n");
        weatherResult = gson.fromJson(response.body(), Transcript.class); //convert received JSON data format to Transcript class object format and store in 'weatherResult'
        //System.out.println(weatherResult.getWeather().get(0).getDescription());


        Result = "Locatation: " + weatherResult.getName() +" "+ Country + "\nCurrent temperature: " + weatherResult.getMain().getTemp() + ", feels like: " + weatherResult.getMain().getFeels_like() + ".\n"
                + weatherResult.getWeather().get(0).getMain() + ": " + weatherResult.getWeather().get(0).getDescription() + "\n"
                + "Humidity: " + weatherResult.getMain().getHumidity() + ", Pressure: " + weatherResult.getMain().getPressure() + "\n"
                + "Wind speed: " + weatherResult.getWind().getSpeed() + ", degree: " + weatherResult.getWind().getDeg();


        ResultLB.setText(Result);
    }
}