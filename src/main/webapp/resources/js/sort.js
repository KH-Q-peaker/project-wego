/* 화살표 함수 */
const sortBy = document.querySelector('.btn-select');
const sortByItems = document.querySelectorAll('.sortByItem button');

// 클릭한 옵션의 텍스트를 라벨 안에 넣음
const handleSelect = (option) => {
  sortBy.innerHTML = option.textContent;
  sortBy.parentNode.classList.remove('active');
}

// 옵션 클릭시 클릭한 옵션을 넘김
sortByItems.forEach((sortByItem) => {
  sortByItem.addEventListener('click', () => handleSelect(sortByItem));
});

// 라벨을 클릭시 옵션 목록이 열림/닫힘
sortBy.addEventListener('click', () => {
  if (sortBy.parentNode.classList.contains('active')) {
    sortBy.parentNode.classList.remove('active');
  } else {
    sortBy.parentNode.classList.add('active');
  }
});

// 라벨 영역 외의 공간 클릭시 옵션 목록이 닫힘
window.addEventListener('click', (event) => {
  const target = event.target;
  if (!sortBy.parentNode.contains(target)) {
    sortBy.parentNode.classList.remove('active');
  }
});
