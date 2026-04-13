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

In the Proto File I added 

```protobuf
service HelloWorldService {
  rpc hello(HelloRequest) returns (HelloResponse) {}
  rpc sendWarehouse(WarehouseRecord) returns (HelloResponse) {}
}
```


    service HelloWorldService { 
        rpc hello(HelloRequest) returns (HelloResponse) {} 
        rpc sendWarehouse(WarehouseRecord) returns (HelloResponse) {}
    }

## Problems

After I first started the application, a error occured

 


