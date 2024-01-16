package org.example;

import dev.langchain4j.data.message.AiMessage;
//import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.tinylog.Logger;
//import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static dev.langchain4j.data.message.SystemMessage.systemMessage;
import static dev.langchain4j.data.message.UserMessage.userMessage;
import static dev.langchain4j.model.huggingface.HuggingFaceModelName.TII_UAE_FALCON_7B_INSTRUCT;
import static java.time.Duration.ofSeconds;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    String OPEN_AI_KEY ="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";

    public static  String HUGGING_FACE_API_KEY ="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";
//    public static void main(String[] args) {
//        // Create an instance of a model
//        ChatLanguageModel model = OpenAiChatModel.withApiKey(ApiKeys.OPENAI_API_KEY);
//        // Start interacting
//        String answer = model.generate("Hello world!");
//        System.out.println(answer); // Hello! How can I assist you today?
//    }
    public static void main(String[] args) {
//      System.setProperty("",HUGGING_FACE_API_KEY);
//      System.setProperty("HF_API_KEY",HUGGING_FACE_API_KEY);

        Logger.info("Hello World!");
        System.out.println(System.getenv("MAVEN_HOME"));
        HuggingFaceChatModel model = HuggingFaceChatModel.builder()
//                .accessToken(System.getenv("HF_API_KEY"))
                .accessToken(HUGGING_FACE_API_KEY)
                .modelId(TII_UAE_FALCON_7B_INSTRUCT)
                .timeout(ofSeconds(15))
                .temperature(0.7)
                .maxNewTokens(20)
                .waitForModel(true)
                .build();

        AiMessage aiMessage = model.generate(
                systemMessage("in 2018New Greater Austin Malayali Association President is Jaison Mathew"),
                userMessage("Who is reater Austin Malayali Association President?")).content();


//        System.out.println(Objects.requireNonNull(getMessage(),"This is a test message "));
        System.out.println(aiMessage.text());
        Logger.info(aiMessage.text());
    }



    public static String getMessage(){
        return null;
    }


    void chatModel(){
        ChatLanguageModel model= OpenAiChatModel.withApiKey(OPEN_AI_KEY);
        String stringTemplate =" ";
        PromptTemplate promptTemplate =  PromptTemplate.from(stringTemplate);
        Map<String,Object> map= new HashMap<>();
        map.put("dishType","Oven Dish");
        map.put("ingredients","potato,tomato,feta, olive oil");

        Prompt prompt = promptTemplate.apply(map);

        String response = model.generate(prompt.text());
        System.out.println(response);

    }
}