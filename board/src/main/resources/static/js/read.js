const replyList = document.querySelector(".replyList");

// ---------- 댓글 목록 가져오기
const url = `http://localhost:8080/replies`;
const loadReply = () => {
  fetch(`${url}/board/${bno}`)
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      console.log(data);

      // ---------- 댓글 개수 보여주기
      // document.querySelector(".row .card:nth-child(2) .card-title span").innerHTML = data.length;
      replyList.previousElementSibling.firstElementChild.innerHTML = data.length;

      let result = "";
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}">`;
        result += `<div class="p-3">`;
        result += `<img
                  src="/img/user.png"
                  alt=""
                  class="rounded-circle mx-auto d-block"
                  style="width: 60px; height: 60px"
                />`;
        result += `</div>`;
        result += `<div class="flex-grow-1 align-self-center">`;
        result += `<div>${reply.replyer}</div>`;
        result += `<div>`;
        result += `<span class="fs-5">${reply.text}</span>`;
        result += `</div>`;
        result += `<div class="text-muted">`;
        result += `<span class="small">${formatDate(reply.createDate)}</span>`;
        result += `</div>`;
        result += `</div>`;
        result += `<div class="d-flex flex-column align-self-center">`;
        result += `<div class="mb-2">`;
        result += `<button class="btn btn-outline-primary btn-sm">수정</button>`;
        result += `</div>`;
        result += `<div class="mb-2">`;
        result += `<button class="btn btn-outline-danger btn-sm">삭제</button>`;
        result += `</div>`;
        result += `</div>`;
        result += `</div>`;
      });
      // replyList.insertAdjacentHTML("afterbegin", result);
      replyList.innerHTML = result;
    });
};
loadReply();

// ---------- 댓글 추가
// 댓글 작성 클릭 시 == replyForm submit 이 발생 시
// submit 중지
// POST 요청
document.querySelector("#replyForm").addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  const rno = form.rno.value;

  const reply = {
    // rno: form.rno.value, // " "(new) or "501"
    rno: rno,
    text: form.text.value,
    replyer: form.replyer.value,
    bno: bno,
  };

  // new or modify => rno value 존재 여부
  if (!rno) {
    // form.rno.value -> 변수 선언 함
    //new
    fetch(`${url}/new`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(reply),
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
            title: "댓글 작성 완료",
            icon: "success",
            draggable: true,
          });
        }

        form.replyer.value = "";
        form.text.value = "";

        // 댓글 가져오기
        loadReply();
      })
      .catch((err) => console.log(err));
  } else {
    // modify
    fetch(`${url}/${rno}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(reply),
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
            title: "댓글 수정 완료",
            icon: "success",
            draggable: true,
          });
        }

        form.rno.value = "";
        form.replyer.value = "";
        form.text.value = "";

        // 수정 뒤에 버튼 내용 변경
        form.rbtn.innerHTML = "댓글 작성";

        // 댓글 가져오기
        loadReply();
      })
      .catch((err) => console.log(err));
  }
});
// ---------- 날짜/시간
const formatDate = (data) => {
  const date = new Date(data);
  // 2025/12/15 12:20
  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

loadReply();

// ---------- 댓글 삭제 버튼 클릭 시
// rno 값을 화면에 가져오는
// document.querySelectorAll(".btn-outline-danger").forEach((btn) => {
//   btn.addEventListener("click", (e) => {
//     const targetBtn = e.target;

//     const rno = targetBtn.closest(".reply-row").dataset.rno;
//   });
// });

// 이벤트 발생시 부모에게 전달 -> "이벤트 버블링"
replyList.addEventListener("click", (e) => {
  console.log(e.target); // 어느 버튼의 이벤트인가?
  const btn = e.target;

  // 부모쪽으로만 검색
  // data- 접급 : dataset
  const rno = btn.closest(".reply-row").dataset.rno;
  console.info("rno", rno);

  // 삭제 or 수정
  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("정말로 삭제하겠습니까?")) return;
    // true인 경우 삭제 요청(fetch)
    fetch(`${url}/${rno}`, {
      method: "DELETE",
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        return res.text();
      })
      .then((data) => {
        if (data) {
          Swal.fire({
            title: "댓글 삭제 완료",
            icon: "success",
            draggable: true,
          });
        }
        // 댓글 다시 가져오기
        loadReply();
      })
      .catch((err) => console.log(err));
    // 댓글 수정
  } else if ("btn-outline-primary") {
    // rno를 이용해 reply 가져오기
    // 가져온 reply를 replyForm에 보여주기
    const form = document.querySelector("#replyForm");
    fetch(`${url}/${rno}`)
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        // json body 추출
        return res.json();
      })
      .then((data) => {
        console.log(data);

        form.rno.value = data.rno;
        form.replyer.value = data.reply;
        form.text.value = data.text;

        // 댓글 작성 버튼 => 댓글 수정
        // 버튼 텍스트 변경
        form.rbtn.innerHTML = "댓글 수정";
      })
      .catch((err) => console.log(err));
  }
});
