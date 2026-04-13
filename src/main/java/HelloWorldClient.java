import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class HelloWorldClient {

    public static void main(String[] args) {

        String firstname = args.length > 0 ? args[0] : "Max";
        String lastname  = args.length > 1 ? args[1] : "Mustermann";

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub = HelloWorldServiceGrpc.newBlockingStub(channel);

        Hello.HelloResponse helloResponse = stub.hello(Hello.HelloRequest.newBuilder()
                .setFirstname(firstname)
                .setLastname(lastname)
                .build());
        System.out.println( helloResponse.getText() );

        Hello.WarehouseRecord request = Hello.WarehouseRecord.newBuilder()
                .setWarehouseID("WH-01")
                .setWarehouseName("Main Warehouse")
                .setTimestamp("2026-04-13")
                .addProductDataList(
                        Hello.ProductData.newBuilder()
                                .setProductId(1)
                                .setProductName("Laptop")
                                .setProductCategory("Electronics")
                                .setQuantity(5)
                                .setPrice(999.99)
                                .build()
                )
                .addProductDataList(
                        Hello.ProductData.newBuilder()
                                .setProductId(2)
                                .setProductName("Mouse")
                                .setProductCategory("Accessories")
                                .setQuantity(20)
                                .setPrice(19.99)
                                .build()
                )
                .build();

        Hello.HelloResponse response = stub.sendWarehouse(request);

        System.out.println(response.getText());
        channel.shutdown();
    }

}
