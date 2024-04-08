package account

import (
	"bank-service-api/internal/model"
	"context"
	"errors"
	"github.com/jackc/pgx/v5"
	"time"
)

func (r *repository) GetStatements(ctx context.Context, accountId string, from time.Time, to time.Time) ([]model.Statement, error) {
	rows, err := r.dbpool.Query(ctx, `SELECT cost, name, payment_time
											FROM statements 
											WHERE account_id = $1 and payment_time <= $2 and payment_time >= $3`, accountId, to, from)
	if err != nil {
		return nil, err
	}

	statements, err := extractStatements(rows)
	if err != nil {
		return nil, err
	}

	return statements, nil
}

func (r *repository) GetAllStatements(ctx context.Context, accountId string) ([]model.Statement, error) {
	rows, err := r.dbpool.Query(ctx, "SELECT cost, name, payment_time FROM statements WHERE account_id = $1", accountId)
	if err != nil {
		return nil, err
	}

	statements, err := extractStatements(rows)
	if err != nil {
		return nil, err
	}

	return statements, nil
}

func extractStatements(rows pgx.Rows) ([]model.Statement, error) {
	var (
		cost        float64
		name        string
		paymentTime time.Time
	)

	statements := make([]model.Statement, 0)
	for rows.Next() {
		err := rows.Scan(&cost, &name, &paymentTime)

		if errors.Is(err, pgx.ErrNoRows) {
			return nil, nil
		} else if err != nil {
			return nil, err
		}
		statements = append(statements, model.Statement{
			Amount: cost / 100,
			Name:   name,
			Time:   paymentTime.String(),
		})
	}
	return statements, nil
}
