
  const $btnUpdate = document.getElementById('btnUpdate');
 $btnUpdate.addEventListener('click',e=>{
   const $postId = document.getElementById('postId');
   const postId = $postId.value;
   location.href = `/posts/${postId}/edit`;  //GET http://localhost:9080/products/2/edit
 },false);




const $btnFindAll = document.getElementById('btnFindAll');
$btnFindAll.addEventListener('click', e=>{
 location.href='/posts'
},false);

    const $btnDel = document.getElementById('btnDelete');
  const $modalDel = document.getElementById('modalDel');
  const $btnYes = document.querySelector('.btnYes');
  const $btnNo = document.querySelector('.btnNo');
  const $form = document.querySelector('form');


  // 삭제 버튼 누르면 모달 띄우기
  $btnDel.addEventListener('click', e => {
    $modalDel.showModal();
  });

  // 예 버튼 → 폼 제출
  $btnYes.addEventListener('click', () => {
    $modalDel.close('yes');
  });

  // 아니오 버튼 → 모달 닫기
  $btnNo.addEventListener('click', () => {
    $modalDel.close('no');
  });

  // 모달 닫힌 후 판단
  $modalDel.addEventListener('close', () => {
    if ($modalDel.returnValue === 'yes') {
      console.log('삭제 처리됨');
      $form.submit();
    } else {
      console.log('삭제 취소됨');
    }
  });