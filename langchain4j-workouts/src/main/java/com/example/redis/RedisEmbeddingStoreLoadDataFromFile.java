package com.example.redis;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.redis.RedisEmbeddingStore;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

public class RedisEmbeddingStoreLoadDataFromFile {

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
        TextDocumentParser parser= new TextDocumentParser();
        Document document =
                loadDocument("/Users/ameya/Work/Shyam/development/src/github/langchain4j/langchain4j-examples/spring-boot-example/src/main/resources/miles-of-smiles-terms-of-use.txt", parser);

        DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0,new OpenAiTokenizer(GPT_3_5_TURBO));
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(documentSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(document);


    }

}
