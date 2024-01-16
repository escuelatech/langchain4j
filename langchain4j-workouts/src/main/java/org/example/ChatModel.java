package org.example;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {

    static String OPEN_AI_KEY ="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";

    public static void main(String[] args) {
      chatModel();
    }


    public static  void chatModel(){
        ChatLanguageModel model= OpenAiChatModel.withApiKey(OPEN_AI_KEY);
        //String stringTemplate =" Create a recipe for the {{dishType}} with following ingredients : {{ingredients}}";
        String stringTemplate =" Name all indian cricketers in team  {{teamName}}";
        PromptTemplate promptTemplate =  PromptTemplate.from(stringTemplate);
        Map<String,Object> map= new HashMap<>();
        //map.put("dishType","Oven Dish");
        //map.put("ingredients","potato,tomato,feta, olive oil");
        map.put("teamName","india");
        Prompt prompt = promptTemplate.apply(map);
        String response = model.generate(prompt.text());
        System.out.println(response);
    }
}
