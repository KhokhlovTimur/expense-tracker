package bank

import (
	"bank-service-api/internal/model"
	"context"
	"github.com/go-chi/render"
	"log"
	"net/http"
)

type Service interface {
	GetAll(ctx context.Context) (*model.BankPage, error)
}

func New(ctx context.Context, service Service) http.HandlerFunc {
	return func(writer http.ResponseWriter, request *http.Request) {
		banks, err := service.GetAll(ctx)
		if err != nil {
			log.Printf("failed to get banks: %s\n", err.Error())

			render.Status(request, http.StatusInternalServerError)
			render.JSON(writer, request, model.Response{
				Status: http.StatusInternalServerError,
				Error:  "failed to create quest",
			})
			return
		}

		log.Printf("found %d banks\n", banks.ElementsTotalCount)
		render.Status(request, http.StatusCreated)
		render.JSON(writer, request, banks)
	}
}
