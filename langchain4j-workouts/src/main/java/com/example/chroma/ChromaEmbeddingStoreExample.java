package com.example.chroma;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;

import java.util.List;

import static dev.langchain4j.internal.Utils.randomUUID;

public class ChromaEmbeddingStoreExample {

    /**
     * To run this example, ensure you have Chroma running locally. If not, then:
     * - Execute "docker pull ghcr.io/chroma-core/chroma:0.4.6"
     * - Execute "docker run -d -p 8000:8000 ghcr.io/chroma-core/chroma:0.4.6"
     * - Wait until Chroma is ready to serve (may take a few minutes)
     */

    public static void main(String[] args) {

        EmbeddingStore<TextSegment> embeddingStore = ChromaEmbeddingStore.builder()
                .baseUrl("http://localhost:8080")
                .collectionName(randomUUID())
                .build();

        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        TextSegment segment1 = TextSegment.from("I like football.");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);

        TextSegment segment2 = TextSegment.from("The weather is good today.");
        Embedding embedding2 = embeddingModel.embed(segment2).content();
        embeddingStore.add(embedding2, segment2);

        Embedding queryEmbedding = embeddingModel.embed("What is your favourite sport?").content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        System.out.println(embeddingMatch.score()); // 0.8144288493114709
        System.out.println(embeddingMatch.embedded().text()); // I like football.
    }
}