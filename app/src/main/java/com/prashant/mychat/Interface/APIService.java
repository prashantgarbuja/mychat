package com.prashant.mychat.Interface;

import com.prashant.mychat.Notifications.MyResponse;
import com.prashant.mychat.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=" + YOUR_FIREBASE_API_KEY +""
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
