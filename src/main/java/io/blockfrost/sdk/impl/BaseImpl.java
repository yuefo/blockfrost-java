package io.blockfrost.sdk.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.blockfrost.sdk.api.exception.APIException;
import io.blockfrost.sdk.api.model.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class BaseImpl {

    private final static Logger LOG = LoggerFactory.getLogger(BaseImpl.class);
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String baseUrl;
    private String projectId;

    public BaseImpl(String baseUrl, String projectId) {
        this.baseUrl = baseUrl;
        this.projectId = projectId;

        if(LOG.isDebugEnabled()) {
            LOG.debug("Blockfrost URL : " + baseUrl);
        }
    }

    protected Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getProjectId() {
        return projectId;
    }


    protected  <T> T processResponse(Response<T> response) throws APIException, IOException {
        if (response.isSuccessful()){
            return response.body();
        } else {
            ResponseError responseError = OBJECT_MAPPER.readValue(response.errorBody().bytes(), ResponseError.class);
            String errorMessage = responseError.getError() + " : " + responseError.getMessage();
            throw new APIException(errorMessage);
        }
    }

    protected <T> T getApiClient(Class<T> clazz) {
        return getRetrofit().create(clazz);
    }

}
