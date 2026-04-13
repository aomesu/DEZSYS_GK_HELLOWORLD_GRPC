# Middleware Engineering "DEZSYS_GK_HELLOWORLD_GRPC"

## Aufgabenstellung

## Implementierung

Start HelloWorldServer (Java)  
`gradle clean build`  
`gradle runServer`

Start HelloWorldClient (Java)  
`gradle runClient`

-------------------------------- 

# Documentation

## Questions:

* What is gRPC and why does it work accross languages and platforms?gRPC is a high-performance RPC framework that uses HTTP/2 and Protocol Buffers for communication. It works across languages and platforms because it generates client/server code from a language-neutral interface definition.

* Describe the RPC life cycle starting with the RPC client?The client calls a local stub function, which serializes the request and sends it over the network to the server. The server deserializes it, executes the method, and returns a serialized response back to the client.

* Describe the workflow of Protocol Buffers?You define data structures in a `.proto` file, then compile it to generate code in your target language. This code is used to serialize and deserialize structured data efficiently.

* What are the benefits of using protocol buffers?They are compact, fast, and language-neutral, making them efficient for data transmission. They also enforce a strict schema and support backward/forward compatibility.

* When is the use of protocol not recommended?It is not ideal when human readability is required or when working with simple, small-scale data where overhead is unnecessary. It can also be less suitable when dynamic or loosely structured data is needed.

* List 3 different data types that can be used with protocol buffers?Examples include `int32`, `string`, and `bool`.

## Changes

In the Proto File I added a methode that takes a WarehouseRecord and returns a HelloResponse.

```protobuf
service HelloWorldService {
  rpc hello(HelloRequest) returns (HelloResponse) {}
  rpc sendWarehouse(WarehouseRecord) returns (HelloResponse) {}
}
```

`ProductData` defines a Product and `WarehouseRecord` defines a Warehouse Record from the exercise MidEng 7.1.

```protobuf
message ProductData {
  int32 productId = 1;
  string productName = 2;
  string productCategory = 3;
  int32 quantity = 4;
  double price = 5;
}

message WarehouseRecord {
  string warehouseID = 1;
  string warehouseName = 2;
  string timestamp = 3;
  repeated ProductData productDataList = 4;
}
```



The service implementation (`HelloWorldServiceImpl`) handles incoming `sendWarehouse` RPC calls by processing the received `WarehouseRecord`, including warehouse metadata and a list of products. It logs the received data, iterates through the product list, and returns a confirmation response to the client.

```protobuf
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
```



The Client creates a `WarehouseRecord` object containing warehouse metadata and a list of `ProductData` entries. This object is then sent to the server via the new gRPC method `sendWarehouse`, and the server’s response is printed to the console.

```protobuf
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
```



## EK

The Python client establishes a gRPC connection to the server running on `localhost:50051` and invokes the `hello` and `sendWarehouse` remote procedures using generated stubs. It sends a greeting request and a structured warehouse record with product data, then prints the responses returned by the server.

```protobuf
import grpc
import sys
import hello_pb2
import hello_pb2_grpc

def run():
    firstname = sys.argv[1] if len(sys.argv) > 1 else "Angelina"
    lastname = sys.argv[2] if len(sys.argv) > 2 else "Omesu"

    channel = grpc.insecure_channel("localhost:50051")
    stub = hello_pb2_grpc.HelloWorldServiceStub(channel)

    response = stub.hello(
        hello_pb2.HelloRequest(
            firstname=firstname,
            lastname=lastname
        )
    )
    print(response.text)

    warehouse_response = stub.sendWarehouse(
        hello_pb2.WarehouseRecord(
            warehouseID="WH-01",
            warehouseName="Main Warehouse",
            timestamp="2026-04-13",
            productDataList=[
                hello_pb2.ProductData(
                    productId=1,
                    productName="Laptop",
                    productCategory="Electronics",
                    quantity=5,
                    price=999.99
                )
            ]
        )
    )

    print(warehouse_response.text)
    channel.close()

if __name__ == "__main__":
    run()
```
