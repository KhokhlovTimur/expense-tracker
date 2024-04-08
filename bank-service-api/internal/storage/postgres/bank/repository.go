package bank

import (
	"context"
	"github.com/jackc/pgx/v5/pgxpool"
)

type repository struct {
	dbpool *pgxpool.Pool
}

func NewRepository(context context.Context, pool *pgxpool.Pool) (*repository, error) {
	_, err := pool.Exec(context, `CREATE TABLE IF NOT EXISTS banks(
						id CHAR(36) PRIMARY KEY,
						name VARCHAR(100) NOT NULL UNIQUE,
    					bic CHAR(9) NOT NULL UNIQUE,
    					corr_acc CHAR(20) NOT NULL UNIQUE,
    					kpp CHAR(9) NOT NULL UNIQUE,
    					inn CHAR(12) NOT NULL UNIQUE,
    					ogrn CHAR(13) NOT NULL UNIQUE
					)`)
	if err != nil {
		return nil, err
	}

	return &repository{dbpool: pool}, nil
}
