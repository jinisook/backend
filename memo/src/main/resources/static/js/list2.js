const url = "http://localhost:8080/memo";
// fetch() : window 함수 기본 사용 가능
// get으로 데이터를 가져올때
fetch(url)
  .then((res) => {
    if (!res.ok) {
      throw new Error(`error! ${res.status}`);
    }
    return res.json();
  })
  .then((data) => {
    console.log(data);

    let result = "";

    data.forEach((memo) => {
      result += `<tr>`;
      result += `<th scope="row">${memo.id}</th>`;
      result += `<td>`;
      result += `<a href="/memo/read2?id=${memo.id}">${memo.text}</a>`;
      result += `</td>`;
      result += `<td>${memo.createDate}</td>`;
      result += `<td>${memo.updateDate}</td>`;
      result += `</tr>`;
    });

    document.querySelector("table tbody").insertAdjacentHTML("afterbegin", result);
  })
  .catch((err) => console.log(err));
