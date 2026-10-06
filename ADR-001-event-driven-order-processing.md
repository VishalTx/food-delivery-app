# ADR-001: Use Event-Driven Microservices for Order Processing

## Context

QuickFood processes orders across independently deployable User, Restaurant, Menu, Order, Payment, and Delivery Tracking services. A request-response workflow that synchronously calls each service in sequence would couple order completion to the availability and latency of every dependency. A slow payment provider, restaurant service, or tracking service could delay the customer request or cause the entire chain to fail.

Order processing also produces state changes that multiple services need to act on. Payment outcomes, restaurant acceptance and preparation, and delivery assignment can take different amounts of time. Delivery status and courier location must be propagated to customers in near real time. The system should let teams deploy services independently and absorb temporary traffic spikes or downstream outages without losing accepted work.

## Decision

Use event-driven communication through a durable event bus for the asynchronous order-processing workflow. The Order Service owns the order state and coordinates its lifecycle by publishing and consuming events. Services publish events when their work changes business state; interested services subscribe and react independently.

The core flow is:

1. The client submits an order through the API Gateway. The Order Service validates the request, records a pending order, and returns an order ID and accepted/pending status without waiting for the full workflow to finish.
2. The Order Service publishes `OrderPlaced`. Restaurant and Payment services process the order independently and publish outcomes such as `RestaurantAccepted`, `RestaurantRejected`, `PaymentAuthorized`, or `PaymentFailed`.
3. The Order Service consumes those outcomes, advances or cancels the order, and publishes the corresponding order-state event. On confirmation, the Delivery Tracking Service starts delivery coordination and publishes assignment and tracking updates.
4. The API Gateway or a real-time delivery channel streams order and tracking updates to the client.

Use Kafka as the event bus for durable, replayable event streams and independent consumers. Keep request-response for the client-facing API, authentication, and read/query operations that need an immediate answer; do not build the order workflow as a synchronous chain of service calls.

Persist order state before acknowledging acceptance. Publish events using a transactional outbox (or an equivalent atomic-publishing mechanism), and make consumers idempotent because events may be delivered more than once. Define versioned event schemas and correlation identifiers. Handle failures and compensating actions explicitly, including releasing a restaurant reservation or refunding payment when an order cannot proceed.

## Consequences

**Benefits**

- Order processing is less sensitive to temporary service or provider outages; events can wait for consumers to recover.
- Restaurant, Payment, and Delivery Tracking services can process work independently and scale or deploy separately.
- Multiple consumers can react to order events without the Order Service making a synchronous call to each one.
- Durable event history supports replay, auditing, and rebuilding downstream projections.
- Asynchronous processing supports timely order-status and real-time delivery updates without holding the initial client request open.

**Costs and risks**

- Order state is eventually consistent; clients must handle pending states and receive later updates.
- The event bus, schemas, consumer lag, retries, duplicate delivery, and dead-letter handling add operational complexity.
- Debugging a workflow across services requires correlation IDs, distributed tracing, and event-level observability.
- The Order Service must handle out-of-order or repeated outcomes safely and define timeout and compensation behavior for incomplete workflows.
- Kafka adds infrastructure and operational overhead compared with a simple request-response application.

This decision does not eliminate synchronous APIs or imply that every interaction should be event-driven. It applies to coordinating the multi-service order lifecycle, where asynchronous processing and independent service operation outweigh the added complexity.
