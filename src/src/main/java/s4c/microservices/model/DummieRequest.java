package s4c.microservices.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="PingRequest", description="request data")
public class DummieRequest {

    private long id;
    private String message;

    
    public DummieRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DummieRequest(long id, String message) {
        this.id = id;
        this.message = message;
    }

	@ApiModelProperty(required=true, notes="The request id")
    public long getId() {
        return id;
    }

	@ApiModelProperty(required=true, notes="The request message")
    public String getMessage() {
        return message;
    }
	
	@Override
	public String toString() {
		return "id: " + String.valueOf(id) + " message: " + message;
	}
}
