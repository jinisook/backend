// 삭제 버튼 클릭 시
// remove-form 가져온 후 submit 시키기

document.querySelector(".btn-outline-danger").addEventListener("click", () => {
  document.querySelector("[name ='remove-form']").submit();
});
