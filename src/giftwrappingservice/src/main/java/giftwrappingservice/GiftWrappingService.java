/*
 * Copyright 2018, Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package giftwrappingservice;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.health.v1.HealthCheckResponse.ServingStatus;
import io.grpc.protobuf.services.HealthStatusManager;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

public final class GiftWrappingService {

    private static final Logger logger = LogManager.getLogger(GiftWrappingService.class);

    private Server server;
    private HealthStatusManager healthMgr;

    private static final GiftWrappingService service = new GiftWrappingService();

    private void start() throws IOException {
    int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "9600"));
    healthMgr = new HealthStatusManager();

    server =
        ServerBuilder.forPort(port)
            .addService(new GiftWrappingServiceImpl())
            .addService(healthMgr.getHealthService())
            .build()
            .start();
    logger.info("Gift Wrapping Service started, listening on " + port);
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                  System.err.println(
                      "*** shutting down gRPC GiftWrappingService server since JVM is shutting down");
                    GiftWrappingService.this.stop();
                  System.err.println("*** server shut down");
                }));
    healthMgr.setStatus("", ServingStatus.SERVING);
    }

    private void stop() {
    if (server != null) {
      healthMgr.clearStatus("");
      server.shutdown();
        }
    }


    private static GiftWrappingService getInstance() {
        return service;
    }

    /** Await termination on the main thread since the grpc library uses daemon threads. */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }



    private static void initStats() {
        if (System.getenv("DISABLE_STATS") != null) {
            logger.info("Stats disabled.");
            return;
        }
        logger.info("Stats enabled, but temporarily unavailable");



        // TODO(arbrown) Implement OpenTelemetry stats

    }

    private static void initTracing() {
        if (System.getenv("DISABLE_TRACING") != null) {
            logger.info("Tracing disabled.");
            return;
        }
        logger.info("Tracing enabled but temporarily unavailable");
        logger.info("See https://github.com/GoogleCloudPlatform/microservices-demo/issues/422 for more info.");

        // TODO(arbrown) Implement OpenTelemetry tracing

        logger.info("Tracing enabled - Stackdriver exporter initialized.");
    }

    /** Main launches the server from the command line. */
    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(
            () -> {
                initStats();
                initTracing();
            })
            .start();

        // Start the RPC server. You shouldn't see any output from gRPC before this.
        logger.info("GiftWrappingService starting.");
        final GiftWrappingService service = GiftWrappingService.getInstance();
        service.start();
        service.blockUntilShutdown();
    }


    //generate new proto with gradle build
    private static class GiftWrappingServiceImpl
            extends giftwrappingservice.GiftWrappingServiceGrpc.GiftWrappingServiceImplBase {

          @Override
              public void getGiftWrappingPrice(GiftWrappingPriceRequest request, StreamObserver<Money> responseObserver) {
              // TODO Auto-generated method stub
              //example response
              //Sollte Quantity * 5€ zurückgeben
                int units = request.getQuantity() * 5;
              Money giftwrapping = Money.newBuilder()
                .setUnits(units)
                .setNanos(100_000_000)
                .setCurrencyCode("EUR").build();
              responseObserver.onNext(giftwrapping);
              }
    }
}
