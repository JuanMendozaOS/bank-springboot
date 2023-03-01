fetch("http://localhost:8080/accounts/1/balance", {
  // mode: 'no-cors',
  method: "GET",
  headers: {
    Accept: "application/json",
  },
}).then((response) => {
  if (response.ok) {
    response.json().then((json) => {
      let balanceData = "";
      let amount = (json.amount).toLocaleString('es-PY', {
        style: 'currency',
        currency: 'PYG'
    })
      balanceData += `${amount}`;
      document.getElementById("balanceAmount").innerHTML = balanceData;
    });
  }
});