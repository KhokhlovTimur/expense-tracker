package bank

import (
	"bank-service-api/internal/model"
	"context"
	"errors"
	"github.com/jackc/pgx/v5"
)

func (r *repository) GetAll(ctx context.Context) ([]model.Bank, error) {
	rows, err := r.dbpool.Query(ctx, "SELECT * FROM banks")
	if err != nil {
		return nil, err
	}

	var (
		id      string
		name    string
		bic     string
		corrAcc string
		kpp     string
		inn     string
		ogrn    string
	)

	banks := make([]model.Bank, 0)
	for rows.Next() {
		err = rows.Scan(&id, &name, &bic, &corrAcc, &kpp, &inn, &ogrn)

		if errors.Is(err, pgx.ErrNoRows) {
			return nil, nil
		} else if err != nil {
			return nil, err
		}
		banks = append(banks, model.Bank{
			ID:      id,
			Name:    name,
			BIC:     bic,
			CorrAcc: corrAcc,
			KPP:     kpp,
			INN:     inn,
			OGRN:    ogrn,
		})
	}

	return banks, nil
}
