package statement

import (
	"bank-service-api/internal/model"
	"context"
	"github.com/go-chi/chi/v5"
	"github.com/go-chi/render"
	"log"
	"net/http"
	"time"
)

type Service interface {
	GetPayments(ctx context.Context, requestData *model.StatementRequest) (*model.Statements, error)
}

func New(ctx context.Context, service Service) http.HandlerFunc {
	return func(writer http.ResponseWriter, request *http.Request) {
		bankId := chi.URLParam(request, "bank_id")
		if bankId == "" {
			log.Println("failed to get bank id from url")

			render.Status(request, http.StatusBadRequest)
			render.JSON(writer, request, model.Response{
				Status: http.StatusBadRequest,
				Error:  "failed to get bank id from url",
			})
			return
		}

		accId := chi.URLParam(request, "acc_id")
		if accId == "" {
			log.Println("failed to get acc id from url")

			render.Status(request, http.StatusBadRequest)
			render.JSON(writer, request, model.Response{
				Status: http.StatusBadRequest,
				Error:  "failed to get acc id from url",
			})
			return
		}

		period := model.Period{
			StartTime:  time.Date(2021, 1, 0, 0, 0, 0, 0, time.UTC),
			FinishTime: time.Now(),
		}

		statements, err := service.GetPayments(ctx, &model.StatementRequest{
			Bank:          bankId,
			AccountNumber: accId,
			Period:        period,
		})
		if err != nil {
			log.Printf("failed to get statements: %s\n", err.Error())

			render.Status(request, http.StatusInternalServerError)
			render.JSON(writer, request, model.Response{
				Status: http.StatusInternalServerError,
				Error:  "failed to get statements",
			})
			return
		}

		log.Printf("quried bank<%s> account<%s>\n", bankId, accId)
		render.Status(request, http.StatusCreated)
		render.JSON(writer, request, statements)
	}
}
