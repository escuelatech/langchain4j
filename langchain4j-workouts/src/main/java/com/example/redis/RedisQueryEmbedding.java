package com.example.redis;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.redis.RedisEmbeddingStore;

import java.util.List;

public class RedisQueryEmbedding {

    public static void main(String[] args) {

        EmbeddingStore<TextSegment> embeddingStore = RedisEmbeddingStore.builder()
                .host("127.0.0.1")
                .user("default")
//                .password("redisPassword")
                .port(6379)
                .dimension(384)
//                .metadataFieldsName(metadata)
                .build();

        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        Embedding queryEmbedding = embeddingModel.embed("Use of Vehicle ?").content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        System.out.println(embeddingMatch.score()); // 0.8144288515898701
        System.out.println(embeddingMatch.embedded().text()); // I like football.


    }
}
