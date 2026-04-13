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