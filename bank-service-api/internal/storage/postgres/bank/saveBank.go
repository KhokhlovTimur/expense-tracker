package bank

import (
	"context"
	"fmt"
	"github.com/brianvoe/gofakeit/v6"
	"strings"
)

func (r *repository) SaveBanks(ctx context.Context) error {
	query := buildInsertForBanks()
	_, err := r.dbpool.Exec(
		ctx,
		query,
	)
	if err != nil {

		return err
	}

	return nil
}

// id, name, bic, corr_acc, kpp, inn, ogrn
func buildInsertForBanks() string {
	bankNames := []string{"СохранБанк", "Открыте сбережения", "Вклад Банк", "Ю-банк"}
	ogrns := []string{"30109163003326095517", "30109845931283475334", "30109741917950062361", "30109204701623446708"}
	res := make([]string, len(bankNames))
	for i := 0; i < len(bankNames); i++ {
		res[i] = fmt.Sprintf("('%s', '%s', '%d', '%s', '%d', '%s', '%d')",
			gofakeit.UUID(),
			bankNames[i],
			gofakeit.IntRange(100000000, 999999999),
			ogrns[i],
			gofakeit.IntRange(100000000, 999999999),
			gofakeit.AchAccount(),
			gofakeit.IntRange(1000000000000, 9999999999999))
	}
	return "INSERT INTO banks (id, name, bic, corr_acc, kpp, inn, ogrn) VALUES " + strings.Join(res, ",")
}
