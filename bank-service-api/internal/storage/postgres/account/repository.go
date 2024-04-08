package account

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

	_, err = pool.Exec(context, `CREATE TABLE IF NOT EXISTS accounts(
    					id CHAR(36) PRIMARY KEY,
						bank_id CHAR(36) NOT NULL,
						account_number CHAR(20) NOT NULL,
    					UNIQUE (bank_id, account_number),
    					FOREIGN KEY (bank_id) references banks (id)
    				)`)

	if err != nil {
		return nil, err
	}

	_, err = pool.Exec(context, `CREATE TABLE IF NOT EXISTS statements(
						id CHAR(36) PRIMARY KEY,
						cost BIGINT NOT NULL,
						name VARCHAR(63) NOT NULL,
						payment_time TIMESTAMP NOT NULL DEFAULT current_timestamp,
						account_id CHAR(36),
						FOREIGN KEY (account_id) references accounts (id)
    				)`)

	if err != nil {
		return nil, err
	}

	return &repository{dbpool: pool}, nil
}
