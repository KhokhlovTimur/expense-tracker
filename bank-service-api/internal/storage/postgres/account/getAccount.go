package account

import (
	"bank-service-api/internal/model"
	"context"
	"errors"
	"github.com/jackc/pgx/v5"
)

func (r *repository) GetAccount(ctx context.Context, accountNumber string, bankName string) (*model.Account, error) {
	row := r.dbpool.QueryRow(ctx, `
						SELECT * 
						FROM accounts 
						WHERE bank_id IN (SELECT id FROM banks WHERE name = $1) 
						  AND account_number = $2
						`,
		bankName,
		accountNumber)

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
