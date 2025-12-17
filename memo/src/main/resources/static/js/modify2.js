const url = `http://localhost:8080/memo`;
const form = document.querySelector("#modify-form");

// 삭제  클릭시
document.querySelector(".btn-outline-danger").addEventListener("click", () => {
  const id = form.id.value;

  fetch(`http://localhost:8080/memo/${id}`, {
    method: "DELETE",
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`error! ${res.status}`);
      }
      // 문구(text) 추출
      return res.text();
    })
    .then((data) => {
      console.log(data);
      if (data) {
        Swal.fire({
          title: "데이터 삭제 완료",
          icon: "success",
          draggable: true,
        });
      }
      // 이동
      location.href = "/memo/list2";
    })
    .catch((err) => console.log(err));
});

form.addEventListener("submit", (e) => {
  e.preventDefault();
  // 스크립트 객체
  const send = {
    id: form.id.value,
    text: form.text.value,
  };

  console.log(send);

  // POST(put)
  fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json", // text/html;charset=UTF-8
    },
    body: JSON.stringify(send),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`error! ${res.status}`);
      }
      // json body 추출
      return res.json();
    })
    .then((data) => {
      console.log(data);
      if (data) {
        Swal.fire({
          title: "데이터 수정 완료",
          icon: "success",
          draggable: true,
        });
      }
      // 새로고침 효과
      // location.reload();
    })
    .catch((err) => console.log(err));
});
