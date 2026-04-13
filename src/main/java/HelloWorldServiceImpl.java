import io.grpc.stub.StreamObserver;

public class HelloWorldServiceImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    @Override
    public void hello( Hello.HelloRequest request, StreamObserver<Hello.HelloResponse> responseObserver) {

        System.out.println("Handling hello endpoint: " + request.toString());

        String text = "Hello World, " + request.getFirstname() + " " + request.getLastname();
        Hello.HelloResponse response = Hello.HelloResponse.newBuilder().setText(text).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void sendWarehouse(Hello.WarehouseRecord request,
                              StreamObserver<Hello.HelloResponse> responseObserver) {

        System.out.println("Warehouse received:");
        System.out.println("ID: " + request.getWarehouseID());
        System.out.println("Name: " + request.getWarehouseName());
        System.out.println("Timestamp: " + request.getTimestamp());

        for (Hello.ProductData p : request.getProductDataListList()) {
            System.out.println("Product: " + p.getProductName()
                    + " | qty: " + p.getQuantity()
                    + " | price: " + p.getPrice());
        }

        Hello.HelloResponse response = Hello.HelloResponse.newBuilder()
                .setText("Warehouse data received")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}