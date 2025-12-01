// 삭제 버튼 클릭 시
// submit 기능 중지
// form.action = "가야할 곳"

document.querySelector(".btn-outline-danger").addEventListener("click", () => {
  const form = document.querySelector("#modify-form");
  form.action = "/memo/remove";
  form.submit();
});
