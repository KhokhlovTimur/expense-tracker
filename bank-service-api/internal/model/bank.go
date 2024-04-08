package model

type BankPage struct {
	Banks              []Bank `json:"banks"`
	ElementsTotalCount int64  `json:"elementsTotalCount"`
}

type Bank struct {
	ID      string `json:"id"`
	Name    string `json:"name"`
	BIC     string `json:"bic"`
	CorrAcc string `json:"correspondentAccount"`
	KPP     string `json:"kpp"`
	INN     string `json:"inn"`
	OGRN    string `json:"ogrn"`
}
