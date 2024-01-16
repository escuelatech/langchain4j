package org.example;


import io.pinecone.PineconeClient;
import io.pinecone.PineconeClientConfig;
import io.pinecone.PineconeConnection;
import io.pinecone.PineconeConnectionConfig;
import io.pinecone.exceptions.PineconeException;
import io.pinecone.proto.*;

class PineconeDatabaseConnection {

    public static void main(String[] args) {
//        // Replace with your Pinecone API key and service URL
//        String apiKey = "your_api_key";
//        String serviceUrl = "https://your-service-url.pinecone.io";
//
//        try {
//            // Create a Pinecone client
//            PineconeClient pineconeClient = Pinecone.init(apiKey, serviceUrl);
//
//            // Sample vector data
//            float[] vectorData = {0.5f, 0.2f, 0.8f, 0.3f};
//
//            // Index vector data into Pinecone
//            String indexId = "your_index_id";
//            pineconeClient.index(indexId, vectorData);
//
//            // Perform a vector search
//            int topK = 5;
//            Pinecone.SearchResponse searchResponse = pineconeClient.search(indexId, vectorData, topK);
//
//            // Process search results
//            for (Pinecone.SearchResponse.Result result : searchResponse.results) {
//                String itemId = result.id;
//                float score = result.score;
//
//                // Display or use the search results as needed
//                System.out.println("Item ID: " + itemId + ", Score: " + score);
//            }
//
//            // Close the Pinecone client
//            pineconeClient.close();
//
//        } catch (PineconeException e) {
//            e.printStackTrace();
//        }
    }
}
