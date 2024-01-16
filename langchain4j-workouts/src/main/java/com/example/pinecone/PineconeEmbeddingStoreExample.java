package com.example.pinecone;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;

import java.util.List;

public class PineconeEmbeddingStoreExample {

    public static void main(String[] args) {

//        EmbeddingStore<TextSegment> embeddingStore = PineconeEmbeddingStore.builder()
//                .apiKey(System.getenv("PINECONE_API_KEY"))
//                .environment("northamerica-northeast1-gcp")
//                // Project ID can be found in the Pinecone url:
//                // https://app.pinecone.io/organizations/{organization}/projects/{environment}:{projectId}/indexes
//                .projectId("19a129b")
//                // Make sure the dimensions of the Pinecone index match the dimensions of the embedding model
//                // (384 for all-MiniLM-L6-v2, 1536 for text-embedding-ada-002, etc.)
//                .index("test")
//                .build();


        //https://shyam-index-62oatny.svc.gcp-starter.pinecone.io
        String apiKey    = "9d58954a-b652-4df8-8cd2-e9e6af4153a0"; //System.getProperty("pinecone.apikey", "example-api-key");
        String indexName        = "shyam-index"; // System.getProperty("pinecone.indexName", "shyam-index");
        String environment      = "gcp-starter";    //System.getProperty("pinecone.environment","example-environment");
        String projectName      = "62oatny";              //System.getProperty("pinecone.projectName", "example-project-name");
        String namespace        = "test-ns";
        int topK = 1;

        EmbeddingStore<TextSegment> embeddingStore = PineconeEmbeddingStore.builder()
                .apiKey(apiKey)
                .environment(environment)
                // Project ID can be found in the Pinecone url:
                // https://app.pinecone.io/organizations/{organization}/projects/{environment}:{projectId}/indexes
                .projectId(projectName)
                // Make sure the dimensions of the Pinecone index match the dimensions of the embedding model
                // (384 for all-MiniLM-L6-v2, 1536 for text-embedding-ada-002, etc.)
                .index(indexName)
                .build();

        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        TextSegment segment1 = TextSegment.from("I like football.");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);

        TextSegment segment2 = TextSegment.from("The weather is good today.");
        Embedding embedding2 = embeddingModel.embed(segment2).content();
        embeddingStore.add(embedding2, segment2);

        TextSegment segment3 = TextSegment.from("Sachin Tendulker is a Cricket player.");
        Embedding embedding3 = embeddingModel.embed(segment3).content();
        embeddingStore.add(embedding3, segment3);

        TextSegment segment4 = TextSegment.from("Narendra Modi  is a prime minister of India .");
        Embedding embedding4 = embeddingModel.embed(segment4).content();
        embeddingStore.add(embedding4, segment4);

//       Embedding queryEmbedding = embeddingModel.embed("What is your favourite sport?").content();
        Embedding queryEmbedding = embeddingModel.embed("Who is Prime minister of India ?").content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);


        System.out.println(embeddingMatch.score()); // 0.8144288515898701
        System.out.println(embeddingMatch.embedded().text()); // I like football.

        queryEmbedding = embeddingModel.embed("Liability ?").content();
        relevant = embeddingStore.findRelevant(queryEmbedding, 1);
        embeddingMatch = relevant.get(0);


        System.out.println(embeddingMatch.score()); // 0.8144288515898701
        System.out.println(embeddingMatch.embedded().text()); // I like football.
    }



}
