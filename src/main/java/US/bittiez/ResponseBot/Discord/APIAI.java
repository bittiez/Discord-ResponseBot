package US.bittiez.ResponseBot.Discord;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class APIAI {
    private AIConfiguration aiConfiguration;
    private AIDataService dataService;
    private Settings config;

    public APIAI(Settings config) {
        aiConfiguration = new AIConfiguration(config.api_token);
        dataService = new AIDataService(aiConfiguration);
        this.config = config;
    }

    public String getResponse(String message) {
        AIRequest aiRequest = new AIRequest(message);
        try {
            AIResponse aiResponse = dataService.request(aiRequest);
            if (aiResponse.getStatus().getCode() == 200)
                return aiResponse.getResult().getFulfillment().getSpeech();

        } catch (AIServiceException e) {
            e.printStackTrace();
        }
        return "";
    }
}
