package dev.example;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class ChatLanguageController {

    ChatLanguageModel model= OpenAiChatModel.withApiKey("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");

    @PostMapping("/chat")
    String chatMessage(@RequestBody String string){
        String answer = "";
        try {
             answer = model.generate(string);
            return answer;
        }catch (Exception e){
            e.printStackTrace();
            answer = e.getMessage();
        }
        return answer;
    }

    ConversationalChain chain = ConversationalChain.builder()
            .chatLanguageModel(OpenAiChatModel.withApiKey("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")).build();

    @PostMapping("/conversation")
    public String chatConversation(@RequestBody String message){
       return chain.execute(message);
    }

    @Autowired
    CustomerSupportAgent agent;

    @PostMapping("/customer")
    public String customerSupportAgent(@RequestBody String message){
        return interact(agent,message);
    }

    String interact(CustomerSupportAgent agent,String message){
        String response= agent.chat(message);
        return response;
    }
}
