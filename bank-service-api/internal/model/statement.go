package model

type Statement struct {
	Amount float64 `json:"cost"`
	Name   string  `json:"description"`
	Time   string  `json:"time"`
}

type StatementRequest struct {
	Bank          string `json:"bank"`
	AccountNumber string `json:"accountNumber"`
	Period        Period `json:"period"`
}
