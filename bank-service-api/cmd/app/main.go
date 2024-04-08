package main

import (
	"bank-service-api/internal/config"
	bankHandler "bank-service-api/internal/http-server/handlers/bank"
	statementHandler "bank-service-api/internal/http-server/handlers/statement"
	bankService "bank-service-api/internal/service/bank"
	"bank-service-api/internal/service/statement"
	"bank-service-api/internal/storage/postgres/account"
	"bank-service-api/internal/storage/postgres/bank"
	"context"
	"github.com/go-chi/chi/v5"
	"github.com/jackc/pgx/v5/pgxpool"
	"log"
	"net/http"
	"time"
)

func main() {
	time.Sleep(1000)
	ctx := context.Background()

	cfg, err := config.Load()
	if err != nil {
		log.Fatalf("failed to load config: %s", err)
	}

	pool, err := pgxpool.New(ctx, cfg.StoragePath)
	if err != nil {
		log.Fatalf("failed create connection pool: %s\n", err.Error())
	}

	accRepository, err := account.NewRepository(ctx, pool)
	if err != nil {
		log.Fatalf("failed init account repo: %s", err)
	}

	bankRepository, err := bank.NewRepository(ctx, pool)
	if err != nil {
		log.Fatalf("failed init bank repo: %s", err)
	}

	bankService := bankService.NewService(bankRepository)
	accService := statement.NewService(accRepository)

	router := chi.NewRouter()

	router.Get("/bank/{bank_id}/account/{acc_id}", statementHandler.New(ctx, accService))
	router.Get("/banks", bankHandler.New(ctx, bankService))

	httpServer := &http.Server{
		Addr:    cfg.HTTPServer.Address,
		Handler: router,
	}

	err = httpServer.ListenAndServe()
	if err != nil {
		log.Fatalf("failed start server: %s", err)
	}
}
