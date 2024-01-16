package com.example.pinecone;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;

import java.util.List;

public class PineConeQueryByText {

    public static void main(String[] args) {

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


        Embedding queryEmbedding = embeddingModel.embed("Use of Vehicle ?").content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        System.out.println(embeddingMatch.score()); // 0.8144288515898701
        System.out.println(embeddingMatch.embedded().text()); // I like football.

    }
}
