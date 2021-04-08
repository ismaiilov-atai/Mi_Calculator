package com.example.calculator.ui.age

class Age {
	var days = 0
	var months = 0
	var years = 0

	var weeks = 0
	var hours = 0
	var minutes = 0

	constructor()

	constructor(days: Int, months: Int, years: Int) {
		this.days = days
		this.months = months
		this.years = years
	}

	constructor(days: Int, months: Int, years: Int, weeks: Int, hours: Int, minutes: Int) {
		this.days = days
		this.months = months
		this.years = years

		this.weeks = weeks
		this.hours = hours
		this.minutes = minutes
	}
}