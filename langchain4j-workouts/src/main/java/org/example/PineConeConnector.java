package org.example;

import com.google.common.primitives.Floats;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.pinecone.PineconeClient;
import io.pinecone.PineconeClientConfig;
import io.pinecone.PineconeConnection;
import io.pinecone.PineconeConnectionConfig;
import io.pinecone.exceptions.PineconeException;
import io.pinecone.proto.*;

public class PineConeConnector {

    public static class Args {
        //https://shyam-index-62oatny.svc.gcp-starter.pinecone.io
        public String apiKey    = "9d58954a-b652-4df8-8cd2-e9e6af4153a0"; //System.getProperty("pinecone.apikey", "example-api-key");
        String indexName        = "shyam-index"; // System.getProperty("pinecone.indexName", "shyam-index");
        String environment      = "gcp-starter";    //System.getProperty("pinecone.environment","example-environment");
        String projectName      = "62oatny";              //System.getProperty("pinecone.projectName", "example-project-name");
        String namespace        = "test-ns";
        int topK = 1;

        //https://shyam-index-62oatny.svc.gcp-starter.pinecone.io
        //https://shyam-index-62oatny.svc.gcp-starter.pinecone.io

        // https://shyam-index-62oatny.svc.gcp-starter.pinecone.io
        //shyam-index-shyam_search_application.svc.gcp-starter.pinecone.io
    }

    public static void main(String[] cliArgs) {
        System.out.println("Starting application...");

        Args args = new Args();

        PineconeClientConfig configuration = new PineconeClientConfig()
                .withApiKey(args.apiKey)
                .withEnvironment(args.environment)
                .withProjectName(args.projectName);

        PineconeClient pineconeClient = new PineconeClient(configuration);
        PineconeConnectionConfig connectionConfig = new PineconeConnectionConfig()
                .withIndexName(args.indexName);

        try (PineconeConnection connection = pineconeClient.connect(connectionConfig)) {
            Vector v1 = Vector.newBuilder()
                    .setId("v1")
                    .addAllValues(Floats.asList(1F, 3F, 5F))
                    .build();

            Vector v2 = Vector.newBuilder()
                    .setId("v2")
                    .addAllValues(Floats.asList(5F, 3F, 1F))
                    .build();

            Vector v3 = Vector.newBuilder()
                    .setId("v3")
                    .addAllValues(Floats.asList(4F, 2F, 1F))
                    .build();

            // Deprecated: use addValue() or addAllValues() instead of addVector() and addAllVectors() respectively
            UpsertRequest upsertRequest = UpsertRequest.newBuilder()
                    .addVectors(v1)
                    .addVectors(v2)
                    .addVectors(v3)
                    .setNamespace(args.namespace)
                    .build();

            System.out.println("Sending upsert request:");
            System.out.println(upsertRequest);

            UpsertResponse upsertResponse = connection.getBlockingStub().upsert(upsertRequest);

            System.out.println("Got upsert response:");
            System.out.println(upsertResponse);


//            QueryVector queryVector = QueryVector
//                    .newBuilder()
//                    .addAllValues(Floats.asList(1F, 2F, 2F))
//                    .setTopK(args.topK)
//                    .setNamespace(args.namespace)
//                    .build();


            float[] rawVector = {1.0F, 2.0F, 3.0F};
            QueryVector queryVector = QueryVector.newBuilder()
                    .addAllValues(Floats.asList(rawVector))
//                    .setFilter(Struct.newBuilder()
//                            .putFields("some_field", Value.newBuilder()
//                                    .setStructValue(Struct.newBuilder()
//                                            .putFields("$lt", Value.newBuilder()
//                                                    .setNumberValue(3)
//                                                    .build()))
//                                    .build())
//                            .build())
                    .setNamespace(args.namespace)
                    .build();


//            QueryRequest queryRequest = QueryRequest
//                    .newBuilder()
//                    .setTopK(args.topK)
//                    .addAllVector(Floats.asList(0.74F,0.98F,0.75F))
////                    .setId("v3")
//                    .build();


                    QueryRequest queryRequest = QueryRequest
                    .newBuilder()
                    .setTopK(args.topK)
                    .addQueries(queryVector)

                    .build();


            System.out.println("Sending query request:");
            System.out.println(queryRequest);
            QueryResponse queryResponse = connection.getBlockingStub().query(queryRequest);

            System.out.println("Got query response:");
            System.out.println(queryResponse);

        } catch (PineconeException e) {
            e.printStackTrace();
        }
    }

}
