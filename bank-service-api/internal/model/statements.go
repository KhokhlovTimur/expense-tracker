package model

import "time"

type Statements struct {
	AccountNumber string      `json:"number"`
	Statements    []Statement `json:"expenses"`
}

type Period struct {
	StartTime  time.Time `json:"startTime"`
	FinishTime time.Time `json:"finishTime"`
}
