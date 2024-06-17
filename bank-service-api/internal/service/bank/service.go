package bank

import (
	"bank-service-api/internal/model"
	"context"
)

type Repository interface {
	GetAll(ctx context.Context) ([]model.Bank, error)
	SaveBanks(ctx context.Context) error
}

type service struct {
	bankRepository Repository
}

func NewService(
	bankRepository Repository,
) *service {
	return &service{
		bankRepository: bankRepository,
	}
}
