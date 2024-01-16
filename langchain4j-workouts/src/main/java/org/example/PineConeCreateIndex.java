package org.example;

import io.pinecone.PineconeClientConfig;
import io.pinecone.PineconeIndexOperationClient;
import io.pinecone.model.CreateIndexRequest;

import java.io.IOException;

public class PineConeCreateIndex {


    public static void main(String[] args) throws IOException {

        String apiKey    = "9d58954a-b652-4df8-8cd2-e9e6af4153a0"; //System.getProperty("pinecone.apikey", "example-api-key");
        String indexName        = "shyam-index"; // System.getProperty("pinecone.indexName", "shyam-index");
        String environment      = "gcp-starter";    //System.getProperty("pinecone.environment","example-environment");
        String projectName      = "62oatny";              //System.getProperty("pinecone.projectName", "example-project-name");
        String namespace        = "test-ns";



        PineconeClientConfig clientConfig = new PineconeClientConfig()
                .withApiKey(apiKey)
                .withProjectName(projectName)
                .withEnvironment(environment);



        CreateIndexRequest createIndexRequest = new CreateIndexRequest()
                .withIndexName(indexName).withDimension(3);
        PineconeIndexOperationClient indexOperationClient = new PineconeIndexOperationClient(clientConfig);
        indexOperationClient.createIndex(createIndexRequest);

    }
}
