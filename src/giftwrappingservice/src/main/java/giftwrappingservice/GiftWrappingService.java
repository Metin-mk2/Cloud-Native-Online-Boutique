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

        private static Money eur(long units, int nanos) {
            return Money.newBuilder()
                .setCurrencyCode("EUR")
                .setUnits(units)
                .setNanos(nanos)
                .build();
        }

        @Override
        public void getGiftWrappingOptions(GiftWrappingOptionsRequest request, StreamObserver<GiftWrappingOptionsReponse> responseObserver) {

            GiftWrappingOption santa = GiftWrappingOption.newBuilder()
                .setId("A1B2C3D4E")
                .setName("Santa Classic Wrap")
                .setDescription("Festive gift wrapping with classic Santa-themed paper, perfect for Christmas gifts.")
                .setPicture("/static/img/giftwrapping/santa.png")
                .setPriceUsd(eur(1, 990_000_000))
                .build();

            GiftWrappingOption christmas = GiftWrappingOption.newBuilder()
                .setId("F5G6H7I8J")
                .setName("Birthday Theme Wrap")
                .setDescription("Red christmas wrapping paper for the perfect christmas vibe.")
                .setPicture("/static/img/giftwrapping/christmas.png")
                .setPriceUsd(eur(1, 790_000_000))
                .build();

            GiftWrappingOption luxury = GiftWrappingOption.newBuilder()
                .setId("K9L0M1N2O")
                .setName("Elegant Gold Paper")
                .setDescription("Premium luxury wrapping paper for a classy and elegant presentation.")
                .setPicture("/static/img/giftwrapping/luxury.jpg")
                .setPriceUsd(eur(2, 490_000_000))
                .build();

            GiftWrappingOption eco = GiftWrappingOption.newBuilder()
                .setId("P3Q4R5S6T")
                .setName("Eco Recycled Wrap")
                .setDescription("Environmentally friendly wrapping made from 100% recycled paper.")
                .setPicture("/static/img/giftwrapping/eco.png")
                .setPriceUsd(eur(1, 290_000_000))
                .build();

            GiftWrappingOption birthday = GiftWrappingOption.newBuilder()
                .setId("BHFU8Q21P")
                .setName("Birthday Theme Wrap")
                .setDescription("Colorful birthday-themed wrapping paper suitable for all ages.")
                .setPicture("/static/img/giftwrapping/birthday.jpg")
                .setPriceUsd(eur(1, 790_000_000))
                .build();

            GiftWrappingOptionsReponse response =
                GiftWrappingOptionsReponse.newBuilder()
                    .addOptions(santa)
                    .addOptions(christmas)
                    .addOptions(luxury)
                    .addOptions(eco)
                    .addOptions(birthday)
                    .build();
            responseObserver.onNext(response);
        }
    }
}
