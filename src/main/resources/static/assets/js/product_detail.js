

//html에서 정의된 상품 정보 변수를 잘 로드하는지 확인하기 위한 로그 출력
console.group("상품정보");
console.log(productData);
console.groupEnd();


let selectedCombinations = [];

//UI 관련 이벤트
document.querySelectorAll(".tab-header").forEach((v, i) => {
    v.addEventListener("click", (e) => {
        //모든 탭 헤더에서 active클래스 제거
        const tabHeaders = document.querySelectorAll('.tab-header');
        tabHeaders.forEach(header => header.classList.remove('active'));

        //모든 탭 패인에서 active클래스 제거
        const tabPanes = document.querySelectorAll('.tab-pane');
        tabPanes.forEach(pane => pane.classList.remove('active'));

        //클릭된 탭 헤더에서 active 클래스 추가
        e.currentTarget.classList.add('active');
        
        //클릭된 탭의 data-target 속성 가져오기
        const tabName =  e.currentTarget.dataset.target;

        //해당 탭 패인에 active 클래스 추가하여 표시
        document.getElementById(`${tabName}-tab`).classList.add('active');
    });
});


//상품 옵션 조합 관리 관련 함수들
/**
 * 상품 옵션 선택 이벤트 핸들러
 * 옵션 선택시 조합을 생성하고 선택된 조합 목록에 추가
 */

document.querySelectorAll(".option-value").forEach((v, i) => {
    v.addEventListener("click", (e) => {
        const current = e.currentTarget;

        const family = current.parentElement.querySelectorAll('.option-value');
        family.forEach((vv, ii) => vv.classList.remove('selected'));

        // 클릭된 옵션에 selected 클래스를 추가하여 선택 상태 표시
        current.classList.add('selected');

// 옵션 그룹 단위 객체
        const optionGroups = document.querySelectorAll('.option-group');

        const optionChoice = {};

// 각 옵션 그룹(컬러, 사이즈 등)을 순회
        optionGroups.forEach((vv, ii) => {
            // 각 그룹별로 선택된 항목 가져오기
            const selectedOption = vv.querySelector('.option-value.selected');
            // console.log(selectedOption);

            // 선택된 항목의 옵션번호, 옵션명, 옵션값 가져오기
            if (selectedOption) {
                const optionName = selectedOption.getAttribute('data-option-name');
                const optionValue = selectedOption.innerHTML;

                optionChoice[optionName] = optionValue;
            }
        });
        //optionChoice에 key와 value로 잘 담겼는지 확인
        // console.log(optionChoice);

        if (Object.keys(optionChoice).length !== optionGroups.length) {
            return;
        }

        let optionPrice = productData.price;

        if (productData.discount > 0) {
            optionPrice -= parseInt(productData.price * productData.discount / 100)
        }

        optionChoice.price = optionPrice;

        if (productData.discount > 0) {
            optionPrice -= parseInt(productData.price * productData.discount / 100)
        }

        optionChoice.price = optionPrice;
        optionChoice.quantity = 1;

        //price와 quantity까지 담겼는지 확인
        // console.log(optionChoice);

        const existingCombinationIndex = selectedCombinations.findIndex(combination => {
            return Object.keys(optionChoice).every(key =>{
                return key ==='price' || key ==='quantity' || combination[key] ===optionChoice[key];
            });
        });

        if(existingCombinationIndex !== -1){
            selectedCombinations[existingCombinationIndex].quantity += 1;
        } else{
            const newcombination = {
                id: Date.now(),
                ...optionChoice
            };
            selectedCombinations.push(newcombination);
        }

        updateCombinationDisplay();

    });
});

/**
 * 선택된 옵션 조합에 대한 UI 갱신 함수
 * 선택된 조합들의 목록을 화면에 표시하고 수량 조절 및 삭제 기능 제공
 */
function updateCombinationDisplay(){
    console.log(selectedCombinations);

    const combinationList = document.querySelector('#combinationList');
    combinationList.innerHTML = '';

    if(selectedCombinations.length === 0){
        combinationList.style.display = 'none';
        return;
    }

    combinationList.style.display= 'block';

    //옵션 정보만큼 순회
    selectedCombinations.forEach((vv, ii) =>{
        const combinationItem = document.createElement('div');
        combinationItem.className = 'combination-item';
        combinationItem.setAttribute('data-combination-id', vv.id);

        //조합 정보 영역 생성
        const combinationInfo = document.createElement('div');
        combinationInfo.className = 'combination-info';

        //옵션 정보 표시
        const combinationOptions = document.createElement("div");
        combinationOptions.className = 'combination-options';

        //옵션 정보를 순회하며 표시
        Object.keys(vv).forEach((key) => {
            if(key !== 'id'&& key !== 'price' && key !== 'quantity'){
                const optionItem = document.createElement('span');
                optionItem.className = 'combination-option';
                optionItem.textContent = `${key}: ${vv[key]}`;
                combinationOptions.appendChild(optionItem);
            }
        });

        //가격 정보 표시
        const combinationPrice = document.createElement('div');
        combinationPrice.className = 'combination-price';
        combinationPrice.textContent = `₩${(vv.price * vv.quantity).toLocaleString()}`;

        //조합 정보에 옵션과 가격 추가
        combinationInfo.appendChild(combinationOptions);
        combinationInfo.appendChild(combinationPrice);

        //수량 조절 영역 생성
        const combinationQuantity = document.createElement('div');
        combinationQuantity.className = 'combination-quantity';

        //수량 감소 버튼
        const decreaseBtn = document.createElement('button');
        decreaseBtn.type = 'button';
        decreaseBtn.className = 'combination-quantity-btn';
        decreaseBtn.textContent = '-';
        decreaseBtn.addEventListener('click', () => changeCombinationQuantity(vv.id, -1));

        //수량 입력 필드
        const quantityInput = document.createElement('input');
        quantityInput.type = 'number';
        quantityInput.className = 'combination-quantity-input';
        quantityInput.value = vv.quantity;
        quantityInput.min = '1';
        quantityInput.addEventListener('change', (e) => setCombinationQuantity(vv.id, e.target.value));

        //수령 증가 버튼
        const increaseBtn = document.createElement('button');
        increaseBtn.type = 'button';
        increaseBtn.className = 'combination-quantity-btn';
        increaseBtn.textContent = '+';
        increaseBtn.addEventListener('click', () => changeCombinationQuantity( vv.id, 1));

        //수령 조절 영역에 버튼들 추가
        combinationQuantity.appendChild(decreaseBtn);
        combinationQuantity.appendChild(quantityInput);
        combinationQuantity.appendChild(increaseBtn);

        //삭제 버튼 생성
        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.className = 'combination - remove';
        removeBtn.textContent = '삭제';
        removeBtn.addEventListener('click', () => removeCombination(vv.id));

        //메인 컨테이너 모든 요소 추가
        combinationItem.appendChild(combinationInfo);
        combinationItem.appendChild(combinationQuantity);
        combinationItem.appendChild(removeBtn);

        //조합 리스트에 추가
        combinationList.appendChild(combinationItem);
    });

    //총 가격 계산 및 표시
    //모든 조합의 총 수량 계산
    const totalQuantity = selectedCombinations.reduce((sum, combo) => sum + combo.quantity, 0);

    //모든 조합의 총 가격 계산
    const totalPrice = selectedCombinations.reduce((sum,combo) => sum + (combo.price * combo.quantity), 0);

    //DOM요소 참조
    const totalQuantityElement = document.getElementById('totalQuantity');
    const totalPriceElement = document.getElementById('totalPrice');
    const totalSummary = document.getElementById('totalSummary');

    if(totalQuantity > 0){
        totalQuantityElement.textContent = `총 ${totalQuantity}개`;
        totalPriceElement.textContent = `₩${totalPrice.toLocaleString()}`;
        totalSummary.style.display = 'block';
    } else{
        totalSummary.style.display = 'none';
    }

}

/**
 * 특정 조합을 목록에서 제거
 */
function removeCombination(combinationId){
    selectedCombinations = selectedCombinations.filter(combo => combo.id !== combinationId);
    updateCombinationDisplay();
}

/**
 * 특정 조합의 수량을 변경
 */
const changeCombinationQuantity = (combinationId, delta) => {
    const combination = selectedCombinations.find(combo => combo.id === combinationId);
    if(combination){
        combination.quantity = Math.max(1, combination.quantity + delta);
        updateCombinationDisplay();
    }
};

/**
 * 특정 조합의 수량을 직접 설정
 */
const setCombinationQUantity = (combinationId, newQuantity) => {
    const combinaition = selectedCombinations.find(combo => combo.id === combinationId);
if(combination){
    combination.quantity = Math.max(1, parseInt(newQuantity) || 1);
    updateCombinationDisplay();
}
};

//장바구니 및 구매 관련 버튼 이벤트 핸들러

/**
 * 선택된 상품 정보를 FormData 형태로 반환
 */
function getProductFormData(){
    if(selectedCombinations.length === 0){
        return null;
    }

    //요청에 필요한 FromData 생성
    const formData = new FormData();
    formData.append('productId', productData.id);
    formData.append('productName', productData.name);

    //상품 옵션 하나를 `|`로 조합하여 formData에 추가
    selectedCombinations.forEach((v, i) => {
        let optionValue = "";
        Object.keys(v).forEach((vv, ii) => {
            optionValue += `${vv}:${v[vv]}|`;
        });
        //마지막 파이프(|) 제거
        optionValue = optionValue.slice(0, -1);
        console.log(`${i}번째 옵션: ${optionValue}`);
        formData.append(`options`, optionValue);
    });

    return formData;

}

/**
 * 선택된 모든 조합을 장바구니에 담기
 */

document.querySelector(".btn-cart").addEventListener("click", (e) => {
    e.preventDefault();

    const formData = getProductFormData();
    if(!formData){
        alert("장바구니에 담을 상품이 없습니다. 옵션을 선택해주세요.");
        return;
    }

    try{
        fetchHelper.post('api/cart/add', formData);
    }catch (e){
        console.error('장바구니 추가 중 오류 발생:', e);
        alert('장바구니에 상품을 추가하는 중 오류가 발생했습니다. 다시 시도해주세요.');
        return;
    }

    if(confirm('장바구니에 상품이 추가되었습니다. 장바구니로 이동하시겠습니까?')){
        window.location.href = '/cart';
    }
})
