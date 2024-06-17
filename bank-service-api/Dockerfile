FROM golang:alpine AS builder

WORKDIR /build

COPY . .

RUN go mod tidy
RUN go mod download

RUN go build -o bank-service ./cmd/app/main.go

FROM alpine

WORKDIR /build

COPY --from=builder /build/bank-service /build/bank-service
COPY --from=builder /build/config/docker-config.yaml /build/config.yaml

ENV CONFIG_PATH="/build/config.yaml"

CMD ["/build/bank-service"]
