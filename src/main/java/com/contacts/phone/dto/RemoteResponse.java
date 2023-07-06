package com.contacts.phone.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteResponse {

    @Schema(description = "Flag that determines if the API operation is successful or failure")
    private boolean succeeded;

    @Schema(description = "A sub-code that indicates the status of the API operation")
    private String statusCode;

    @Schema(description = "Message that describes the status of the API operation")
    private String statusMessage;

    @Schema(description = "Result data of the API operation")
    private List results;

    public static RemoteResponse create(boolean succeeded, String statusCode, String statusMessage, List additionalElements) {
        RemoteResponse response = new RemoteResponse();
        response.setSucceeded(succeeded);
        response.setStatusCode(statusCode);
        response.setStatusMessage(statusMessage);
        response.setResults(additionalElements);

        return response;
    }

}
