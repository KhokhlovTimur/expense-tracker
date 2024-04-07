package statement

import (
	"bank-service-api/internal/model"
	"context"
	"math/rand"
	"time"

	"github.com/brianvoe/gofakeit/v6"
)

// todo: бенчмаркнуть, а потом попробовать генерить в несколько горутин и сверить
func (s *service) generatePayments(ctx context.Context, requestData *model.StatementRequest) error {
	paymentsNum := gofakeit.IntRange(100, 1000)

	var (
		payment model.Statement
		err     error
	)

	payments := make([]model.Statement, paymentsNum)
	for i := 0; i < paymentsNum; i++ {
		payment, err = generate()
		if err != nil {
			return err
		}

		payments[i] = payment
	}

	err = s.accountRepository.SaveStatements(ctx, payments, requestData)
	if err != nil {
		return err
	}

	return nil
}

func generate() (model.Statement, error) {
	return model.Statement{
		Name: gofakeit.ProductName(),
		Time: randDate().String(),
	}, nil
}

func randDate() time.Time {
	minDate := time.Date(2022, 1, 0, 0, 0, 0, 0, time.UTC).Unix()
	maxDate := time.Now().Unix()
	delta := maxDate - minDate

	sec := rand.Int63n(delta) + minDate
	return time.Unix(sec, 0)
}
