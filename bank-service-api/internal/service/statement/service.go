package statement

import (
	"bank-service-api/internal/model"
	"context"
	"time"
)

type AccountRepository interface {
	// GetAccount return account by accountNumber and bankName
	GetAccount(ctx context.Context, accountNumber string, bankName string) (*model.Account, error)

	// GetStatements return statements by accountId in time period
	GetStatements(ctx context.Context, accountId string, from time.Time, to time.Time) ([]model.Statement, error)

	// GetAllStatements return all model.Statement by accountId
	GetAllStatements(ctx context.Context, accountId string) ([]model.Statement, error)

	// SaveStatements save statements in db with requestData params
	SaveStatements(ctx context.Context, statements []model.Statement, requestData *model.StatementRequest) error
}

type service struct {
	accountRepository AccountRepository
}

func NewService(
	accountRepository AccountRepository,
) *service {
	return &service{
		accountRepository: accountRepository,
	}
}
