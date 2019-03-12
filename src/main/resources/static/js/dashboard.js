function createProporcoes(proporcoes) {
	new Chart(document.getElementById("tipoInvestimento").getContext('2d'), {
		type : 'doughnut',
		data : {
			labels : proporcoes.legendas,
			datasets : [ {
				data : proporcoes.valores,
				backgroundColor : proporcoes.cores,
				hoverBackgroundColor : proporcoes.coresSecundarias
			} ]
		},
		options : {
			responsive : true
		}
	});
}

new Chart(document.getElementById("rendaVariavel").getContext('2d'), {
	type : 'doughnut',
	data : {
		labels : [ "XPML11", "KNIP11", "BBSE3", "GGRC11", "VRTA11" ],
		datasets : [ {
			data : [ 300, 50, 100, 40, 120 ],
			backgroundColor : [ "#F7464A", "#46BFBD", "#FDB45C", "#949FB1",
					"#4D5360" ],
			hoverBackgroundColor : [ "#FF5A5E", "#5AD3D1", "#FFC870",
					"#A8B3C5", "#616774" ]
		} ]
	},
	options : {
		responsive : true
	}
});

new Chart(document.getElementById("evolucao").getContext('2d'), {
	type : 'line',
	data : {
		labels : [ "January", "February", "March", "April", "May", "June",
				"July" ],
		datasets : [ {
			label : "Portfólio",
			data : [ 65, 59, 80, 81, 84, 87, 91 ],
			backgroundColor : [ 'rgba(105, 0, 132, .2)', ],
			borderColor : [ 'rgba(200, 99, 132, .7)', ],
			borderWidth : 2
		}, {
			label : "CDI",
			data : [ 28, 38, 40, 42, 45, 50, 52 ],
			backgroundColor : [ 'rgba(0, 137, 132, .2)', ],
			borderColor : [ 'rgba(0, 10, 130, .7)', ],
			borderWidth : 2
		} ]
	},
	options : {
		responsive : true
	}
});

function createPorInstituicoes(instituicoes) {
	new Chart(document.getElementById("rendaFixa"), {
		"type" : "horizontalBar",
		"data" : {
			"labels" : instituicoes.legendas,
			"datasets" : [ {
				"label" : "Instituições de renda fixa",
				"data" : instituicoes.valores,
				"fill" : false,
				"backgroundColor" : instituicoes.coresSecundarias,
				"borderColor" : instituicoes.cores,
				"borderWidth" : 1
			} ]
		},
		"options" : {
			"scales" : {
				"xAxes" : [ {
					"ticks" : {
						"beginAtZero" : true
					}
				} ]
			}
		}
	});
}

function createLiquidez(dados) {
	new Chart(document.getElementById("liquidez").getContext('2d'), {
		type : 'bar',
		data : {
			labels : dados.legendas,
			datasets : [ {
				label : 'Ano de vencimento',
				data : dados.valores,
				backgroundColor : dados.coresSecundarias,
				borderColor : dados.cores,
				borderWidth : 1
			} ]
		},
		options : {
			scales : {
				yAxes : [ {
					ticks : {
						beginAtZero : true
					}
				} ]
			}
		}
	});
}
