package cz.cvut.fit.ejk.client.cliCommand

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder


val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build()
