package com.example.allergendetector;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChatGPTService {
    @Headers({

            "Content-Type: application/json",
            "Authorization: Bearer kewOzkyXTkxyA4DoeOVQT3BlbkFJrV2anKGazEvCzBnNSu7H"

    })
    @POST("https://api.openai.com/gpt-3/complete") // Replace with the appropriate endpoint for the GPT-3.5 API
    Call<ChatGPTResponse> getChatGPTResponse(@Body ChatGPTRequest request);


}
