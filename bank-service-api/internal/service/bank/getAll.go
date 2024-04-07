package bank

import (
	"bank-service-api/internal/model"
	"context"
)

func (s *service) GetAll(ctx context.Context) (*model.BankPage, error) {
	result, err := s.bankRepository.GetAll(ctx)
	if err != nil {
		return nil, err
	}

	if len(result) == 0 {
		err = s.bankRepository.SaveBanks(ctx)
		if err != nil {
			return nil, err
		}
	}

	result, err = s.bankRepository.GetAll(ctx)
	if err != nil {
		return nil, err
	}

	return &model.BankPage{
		Banks:              result,
		ElementsTotalCount: int64(len(result)),
	}, nil
}
