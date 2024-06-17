package model

type Account struct {
	ID            string
	BankId        string `json:"bankId"`
	AccountNumber string `json:"accountNumber"`
}
