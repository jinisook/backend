const url = `http://localhost:8080/memo/${id}`;
const form = document.querySelector("#form");
// fetch() : window 함수 기본 사용 가능
// get으로 데이터를 가져올때
fetch(url)
  .then((res) => {
    if (!res.ok) {
      throw new Error(`error! ${res.status}`);
    }
    // json body 추출
    return res.json();
  })
  .then((data) => {
    console.log(data);

    form.id.value = data.id;
    form.text.value = data.text;
  })
  .catch((err) => console.log(err));
