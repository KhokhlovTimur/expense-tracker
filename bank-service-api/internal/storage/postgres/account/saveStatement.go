package account

import (
	"bank-service-api/internal/model"
	"context"
	"fmt"
	"github.com/brianvoe/gofakeit/v6"
	"strings"
)

func (r *repository) SaveStatements(ctx context.Context, statements []model.Statement, requestData *model.StatementRequest) error {
	tx, err := r.dbpool.Begin(ctx)
	if err != nil {
		return err
	}

	accId := gofakeit.UUID()
	_, err = tx.Exec(
		ctx,
		"INSERT INTO accounts(id, bank_id, account_number) VALUES ($1, $2, $3)",
		accId,
		requestData.BankName,
		requestData.AccountNumber,
	)
	if err != nil {
		tx.Rollback(ctx)

		return err
	}

	query := buildInsertForStatements(statements, accId)
	_, err = tx.Exec(
		ctx,
		query,
	)
	if err != nil {
		tx.Rollback(ctx)

		return err
	}

	err = tx.Commit(ctx)
	if err != nil {
		return err
	}

	return nil
}

// id, amount, time, name, accId
func buildInsertForStatements(statements []model.Statement, accId string) string {
	res := make([]string, len(statements))
	var statement model.Statement
	for i := 0; i < len(statements); i++ {
		statement = statements[i]
		res[i] = fmt.Sprintf("('%s', %d, '%s', '%s', '%s')",
			gofakeit.UUID(), gofakeit.UintRange(100, 100000000), prepareTime(statement.Time), statement.Name, accId)
	}
	return "INSERT INTO statements (id, cost, payment_time, name, account_id) VALUES " + strings.Join(res, ",")
}

func prepareTime(time string) string {
	data := strings.Split(time, " ")
	return strings.Join(data[:2], " ")
}
