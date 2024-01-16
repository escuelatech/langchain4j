package com.example.redis;

import com.redis.testcontainers.RedisStackContainer;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.redis.RedisEmbeddingStore;

import java.util.List;

import static com.redis.testcontainers.RedisStackContainer.DEFAULT_IMAGE_NAME;
import static com.redis.testcontainers.RedisStackContainer.DEFAULT_TAG;

public class RedisEmbeddingStoreExample {

    public static void main(String[] args) {

//        redisEmbeddingStore = RedisEmbeddingStore.builder()
//                .host(redisHost)
//                .user("default")
//                .password(redisPassword)
//                .port(redisPort)
//                .dimension(384)
//                .metadataFieldsName(metadata)
//                .build();

        RedisStackContainer redis = new RedisStackContainer(DEFAULT_IMAGE_NAME.withTag(DEFAULT_TAG));
        redis.start();

//        EmbeddingStore<TextSegment> embeddingStore = RedisEmbeddingStore.builder()
//                .host(redis.getHost())
//                .port(redis.getFirstMappedPort())
//                .dimension(384)
//                .build();

//        var metadata = List.of(Converters.TALK_ID, Converters.TALK_TITLE);
        EmbeddingStore<TextSegment> embeddingStore = RedisEmbeddingStore.builder()
                .host("127.0.0.1")
                .user("default")
//                .password("redisPassword")
                .port(6379)
                .dimension(384)
//                .metadataFieldsName(metadata)
                .build();

        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        TextSegment segment1 = TextSegment.from("I like football.");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);

        TextSegment segment2 = TextSegment.from("The weather is good today.");
        Embedding embedding2 = embeddingModel.embed(segment2).content();
        embeddingStore.add(embedding2, segment2);

        TextSegment segment3 = TextSegment.from("Shyam is a fantastic Cricket Player.");
        Embedding embedding3 = embeddingModel.embed(segment3).content();
        embeddingStore.add(embedding3, segment3);

        Embedding queryEmbedding = embeddingModel.embed("Who is the best cricket player?").content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        System.out.println(embeddingMatch.score()); // 0.8144288659095
        System.out.println(embeddingMatch.embedded().text()); // I like football.

        redis.stop();
    }
}