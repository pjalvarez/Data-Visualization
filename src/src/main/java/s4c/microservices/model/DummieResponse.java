package s4c.microservices.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="PingResponse", description="response data")
public class DummieResponse {
	
	@ApiModelProperty(required=true, notes="The response message")
    private String responseMessage;

    public DummieResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DummieResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }
	
    public String getResponseMessage() {
        return responseMessage;
    }
    
    @Override
	public String toString() {
		return " message: " + responseMessage;
	}
}
