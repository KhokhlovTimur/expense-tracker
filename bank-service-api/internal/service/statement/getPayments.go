package statement

import (
	"bank-service-api/internal/model"
	"context"
)

func (s *service) GetPayments(ctx context.Context, requestData *model.StatementRequest) (*model.Statements, error) {
	acc, err := s.accountRepository.GetAccount(ctx, requestData.AccountNumber, requestData.Bank)
	if err != nil {
		return nil, err
	}

	if acc == nil {
		err = s.generatePayments(ctx, requestData)
		if err != nil {
			return nil, err
		}
	}

	acc, err = s.accountRepository.GetAccount(ctx, requestData.AccountNumber, requestData.Bank)
	if err != nil {
		return nil, err
	}

	statementsArr, err := s.getPaymentsByAccountID(ctx, acc.ID, &requestData.Period)
	if err != nil {
		return nil, err
	}

	return &model.Statements{
		AccountNumber: acc.AccountNumber,
		Statements:    statementsArr,
	}, nil
}

func (s *service) getPaymentsByAccountID(ctx context.Context, id string, period *model.Period) ([]model.Statement, error) {
	if period == nil {
		return s.getAllPayments(ctx, id)
	}

	return s.getPaymentsInPeriod(ctx, id, period)
}

func (s *service) getAllPayments(ctx context.Context, id string) ([]model.Statement, error) {
	statements, err := s.accountRepository.GetAllStatements(ctx, id)
	if err != nil {
		return nil, err
	}

	return statements, nil
}

func (s *service) getPaymentsInPeriod(ctx context.Context, id string, period *model.Period) ([]model.Statement, error) {
	statements, err := s.accountRepository.GetStatements(ctx, id, period.StartTime, period.FinishTime)
	if err != nil {
		return nil, err
	}

	return statements, nil
}
