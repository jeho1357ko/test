// 문자열 바이트 길이 계산 함수

function getBytesSize(str){
    const encoder = new TextEncoder(); //브라우저 내장 객체 문자열을 utf-8형식으로 인코딩 해서 바이트 배열로 반환 해줌
    const byteArray = encoder.encode(str); //	Uint8Array 타입의 바이트 배열
    return byteArray.length;
}

function loadScript(url){
    return new Promise(resolve, reject) => {   // 비동기 코드 = 이 작업 끝나면 알려줄게를 위한 약속
    //resolve(data) → 성공적으로 끝났을 때 약속을 지킴 (→ .then(data => {})) ,reject(error) → 실패했을 때 약속을 깨트림 (→ .catch(err => {}))
        const scriptEle = document.createElement('script');
        scriptEle.src = url;
        scriptEle.defer = true;

        scriptEle.addEventListener('load', e => resolve(`${url} 로딩성공!`));
        scriptEle.addEventListener('error', e=> reject(new Error(`${url}` 로딩실패!)));

        document.head.appendChild(scriptEle);  // 만든 <script> 태그를 실제 HTML의 <head> 태그 안에 넣는 코드
    }
}



