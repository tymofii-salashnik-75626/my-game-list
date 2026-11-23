const btnMore = document.getElementById("btn-more");
const btnAdd = document.getElementById("AddGame");
const addDetail= document.getElementById("add-details");
const removeDetail = document.getElementById("remove-add");
const detailsSection = document.getElementById("details-section");
const btnBack = document.getElementById("btn-back");
const mainContent = document.getElementById("main-content");
const coverInput = document.getElementById("cover-file");
const fileLabelText = document.getElementById("file-label-text");
const btnCreateGame = document.getElementById("create-game");
const inputTitle = document.getElementById("input-title");
const inputYear = document.getElementById("input-year");
const inputGenre = document.getElementById("input-genre");
const inputDesc = document.getElementById("input-desc");
const searchInput = document.getElementById("FoundGame");

btnAdd.addEventListener("click", function() {
    addDetail.style.display = "flex"; 
});

removeDetail.addEventListener("click", function() {
    addDetail.style.display = "none";
});
btnMore.addEventListener("click", function(){
    mainContent.style.display = "none";
    detailsSection.style.display = "block";
});

btnBack.addEventListener("click", function(){
    mainContent.style.display = "block";
    detailsSection.style.display = "none";
});

coverInput.addEventListener("change", function() {
    if (coverInput.files[0]) {
        fileLabelText.textContent = "Выбрано: " + coverInput.files[0].name;
    } else {
        fileLabelText.textContent = "Нажмите, чтобы выбрать картинку";
    }
});

btnCreateGame.addEventListener("click", function(){
    const title = inputTitle.value.trim();
    const year = inputYear.value.trim();
    const genre = inputGenre.value.trim();
    const desc = inputDesc.value.trim();

    if(!title || !year || !genre){
        alert("Пожалуйста, заполните обязательные поля: Название, Год выпуска и Жанр!");
        return;
    }
    const card = document.createElement("div");
    card.className = "Game-Card";
    
    const coverDiv = document.createElement("div");
    coverDiv.className = "Game-Cover";
   if (coverInput.files && coverInput.files[0]) {
        const img = document.createElement("img");
        img.src = URL.createObjectURL(coverInput.files[0]);
        img.style.width = "300px";
        img.style.height = "200px";
        img.style.objectFit = "cover";
        img.style.borderRadius = "10px";
        coverDiv.appendChild(img);
    } else {
        coverDiv.innerHTML = "<h1>Нет Обложки</h1>";
    }

    card.appendChild(coverDiv);

    const infoDiv = document.createElement("div");
    infoDiv.className = "game-info";
    infoDiv.innerHTML = `
        <h2 class="game-title">${title}</h2>
        <p class="game-description">${desc}</p>
        <p class="game-year"><strong>Год выпуска:</strong> ${year}</p>
        <p class="game-ganre"><strong>Жанр:</strong> ${genre}</p>
        <button class="btn-more">Подробнее</button>
    `;
// 5. Навешиваем событие на новую кнопку "Подробнее"
    const newBtnMore = infoDiv.querySelector(".btn-more");
    newBtnMore.addEventListener("click", function() {
        document.getElementById("detail-title").textContent = title;
        mainContent.style.display = "none";
        detailsSection.style.display = "block";
    });

    card.appendChild(infoDiv);

    // 6. Добавляем новую карточку в основной контейнер
    mainContent.appendChild(card);

    inputTitle.value = "";
    inputYear.value = "";
    inputGenre.value = "";
    inputDesc.value = "";
    coverInput.value = "";
    fileLabelText.textContent = "Нажмите, чтобы выбрать картинку";
    addDetail.style.display = "none";
});

searchInput.addEventListener("input", function() {
    // Получаем текущее значение из поля поиска и приводим его к нижнему регистру
    const query = searchInput.value.toLowerCase().trim();

    // Находим все карточки игр в mainContent
    const cards = mainContent.querySelectorAll(".Game-Card");

    cards.forEach(card => {
        // Находим заголовок игры внутри текущей карточки
        const titleElement = card.querySelector(".game-title");
        
        if (titleElement) {
            const titleText = titleElement.textContent.toLowerCase();

            // Если название содержит текст из поиска, показываем карточку, иначе скрываем
            if (titleText.includes(query)) {
                card.style.display = "flex";
            } else {
                card.style.display = "none";
            }
        }
    });
});
