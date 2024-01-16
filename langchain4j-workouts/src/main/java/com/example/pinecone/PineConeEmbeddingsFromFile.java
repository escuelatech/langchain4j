package com.example.pinecone;

import dev.langchain4j.data.document.Document;

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
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

public class PineConeEmbeddingsFromFile {

    public static void main(String[] args) {

        String apiKey           = "9d58954a-b652-4df8-8cd2-e9e6af4153a0"; //System.getProperty("pinecone.apikey", "example-api-key");
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

        TextDocumentParser parser= new TextDocumentParser();
        Document document =
                loadDocument("/Users/ameya/Work/Shyam/development/src/github/langchain4j/langchain4j-examples/spring-boot-example/src/main/resources/miles-of-smiles-terms-of-use.txt", parser);


//        DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0,new OpenAiTokenizer(GPT_3_5_TURBO));
        DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0,new OpenAiTokenizer(GPT_3_5_TURBO));

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(documentSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(document);
    }
}
