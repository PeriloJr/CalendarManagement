package com.vacationcalendar.Classes;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.vacationcalendar.Models.Holiday;
import com.vacationcalendar.Models.HolidayJson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HolidayAPI {
    private String BuildApiUrl(LocalDateTime date, String state){
        String url = "https://api.invertexto.com/v1/holidays/" + date.getYear() +
                "?token=vbxP2XjN3CkJUIC1FygmpuFSejdrISd1&state=" + state;
        return url;
    }
    private HttpRequest BuildRequest(String url, HttpClient client){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return request;
    }
    private String GetResponseBody(HttpRequest request, HttpClient client){
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }
    public String ConsumeAPI(LocalDateTime date, String state){
        String url = BuildApiUrl(date, state);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = BuildRequest(url, client);
        String response = GetResponseBody(request, client);

        return response;
    }
    public List<HolidayJson> DeserializeResponse(String response){
        Gson gson = new Gson();
        List<HolidayJson> holidaysJsonList = gson.fromJson(response, new TypeToken<List<HolidayJson>>(){}.getType());
        return holidaysJsonList;
    }
    public List<Holiday> HolydayList(LocalDateTime date, String state){
        String response = ConsumeAPI(date, state);
        List<HolidayJson> holidaysJsonList = DeserializeResponse(response);

        List<Holiday> holidaysList = new ArrayList<>();
        for(HolidayJson holiday : holidaysJsonList){
            holidaysList.add(new Holiday(holiday));
        }
        return holidaysList;
    }
}
