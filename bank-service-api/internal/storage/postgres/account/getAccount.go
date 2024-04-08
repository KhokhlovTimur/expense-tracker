package account

import (
	"bank-service-api/internal/model"
	"context"
	"errors"
	"github.com/jackc/pgx/v5"
)

func (r *repository) GetAccount(ctx context.Context, accountNumber string, bankID string) (*model.Account, error) {
	row := r.dbpool.QueryRow(ctx, "SELECT * FROM accounts WHERE bank_id = $1 and account_number = $2", bankID, accountNumber)

	var (
		id        string
		bankId    string
		accNumber string
	)

	err := row.Scan(&id, &bankId, &accNumber)
	if errors.Is(err, pgx.ErrNoRows) {
		return nil, nil
	} else if err != nil {
		return nil, err
	}

	return &model.Account{
		ID:            id,
		BankId:        bankId,
		AccountNumber: accNumber,
	}, nil
}
