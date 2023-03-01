fetch("http://localhost:8080/accounts/1/movements", {
  // mode: 'no-cors',
  method: "GET",
  headers: {
    Accept: "application/json",
  },
}).then((response) => {
  if (response.ok) {
    response.json().then((json) => {
      let tableData = "";
      json.map((values) => {
        tableData += `<tr>
            <th scope="row">${values.id}</th>`;
        if (values.deposit != null) {
          tableData += `<td>Depósito</td>`;
        }
        if (values.transfer != null) {
          tableData += `<td>Transferencia</td>`;
        }
        if (values.withdrawal != null) {
          tableData += `<td>Retiro</td>`;
        }
        let date = new Date(values.dateTime);
        tableData += `
        <td>${values.amount}</td>
        <td>${values.currencyIsoCode}</td>
        <td> ${date.toLocaleString("es-SP")}</td>
      `;

        //   console.log( Object.hasOwn(values, 'transfer'))
      });
      $(document).ready(function () {
        $("#movementTable").DataTable();
      });
      document.getElementById("tableBody").innerHTML = tableData;
    });
  }
});

