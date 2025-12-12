// 검색 form 이 submit 될 때
const form = document.querySelector("#actionForm");
form.addEventListener("submit", (e) => {
  // submit 중지
  e.preventDefault();

  // keyword, select 값이 있는 지 확인
  // 없을 시 메시지 띄우기
  if (form.type.value === "") {
    alert("검색 타입을 선택하세요.");
    form.type.focus();
    return;
  } else if (form.keyword.value === "") {
    alert("검색어를 입력하세요.");
    form.keyword.focus();
    return;
  }
  // page 값을 1로 변경
  form.page.value = 1;
  form.submit();
});
