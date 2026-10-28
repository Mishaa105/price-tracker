const searchInput = document.getElementById('searchInput');
const homeView = document.getElementById('home-view');
const searchView = document.getElementById('search-view');
const productView = document.getElementById('product-view');
const homeGrid = document.getElementById('home-grid');
const searchGrid = document.getElementById('search-grid');
const backBtn = document.getElementById('back-btn');
const productBackBtn = document.getElementById('product-back-btn');
const productDetails = document.getElementById('product-details');

let priceChartInstance = null;

backBtn.onclick = function()
{
    searchView.classList.add('hidden');
    homeView.classList.remove('hidden');
};

productBackBtn.onclick = function()
{
    productView.classList.add('hidden');
    homeView.classList.remove('hidden');
};

async function loadHome()
{
    homeGrid.innerHTML = '<p>Загрузка...</p>';
    try {
        const response = await fetch('/api/v1/homepage');
        const items = await response.json();

        let htmlString = '';
        for (let i = 0; i < items.length; i++)
        {
            htmlString += createCardHTML(items[i]);
        }
        homeGrid.innerHTML = htmlString;
    }
    catch (error)
    {
        console.error('Ошибка загрузки главной:', error);
        homeGrid.innerHTML = '<p>Не удалось загрузить товары</p>';
    }
}

loadHome();

function createCardHTML(item, customId = null)
{
    const productId = customId || item.productId || '';
    const priceObj = item.currentPrices && item.currentPrices.length > 0 ? item.currentPrices[0] : null;

    if (!priceObj)
    {
        return `
            <div class="card" data-id="${productId}">
                <img src="${item.preview}" alt="картинка">
                <div class="card-content">
                    <div class="card-title">${item.name}</div>
                    <div class="card-price">Цена не указана</div>
                </div>
            </div>
        `;
    }

    const discounted = (priceObj.discountPrice / 100).toFixed(2) + ' ' + priceObj.currency;
    const hasDiscount = priceObj.originalPrice && priceObj.originalPrice > priceObj.discountPrice;
    const original = hasDiscount ? (priceObj.originalPrice / 100).toFixed(2) + ' ' + priceObj.currency : '';

    return `
        <div class="card" data-id="${productId}">
            <img src="${item.preview}" alt="картинка">
            <div class="card-content">
                <div class="card-title">${item.name}</div>
                <div class="card-price">
                    ${hasDiscount ? `<span class="original-price">${original}</span>` : ''}
                    <span class="discounted-price">${discounted}</span>
                </div>
            </div>
        </div>
    `;
}

searchInput.addEventListener('input', async (event) =>
{
    const query = event.target.value.trim();
    if (!query)
    {
        searchView.classList.add('hidden');
        productView.classList.add('hidden');
        homeView.classList.remove('hidden');
        return;
    }

    homeView.classList.add('hidden');
    productView.classList.add('hidden');
    searchView.classList.remove('hidden');
    searchGrid.innerHTML = 'Загрузка...';

    try
    {
        const response = await fetch(`/api/v1/products/search?query=${encodeURIComponent(query)}`);
        const ids = await response.json();

        if (!ids || ids.length === 0)
        {
            searchGrid.innerHTML = '<p>Ничего не найдено</p>';
            return;
        }

        let htmlString = '';
        for (let i = 0; i < ids.length; i++)
        {
            const id = ids[i];
            const productResponse = await fetch(`/api/v1/products/page?productId=${encodeURIComponent(id)}`);
            const item = await productResponse.json();
            htmlString += createCardHTML(item, id);
        }

        searchGrid.innerHTML = htmlString;
    }
    catch (error)
    {
        console.error('Ошибка поиска:', error);
        searchGrid.innerHTML = '<p>Ошибка загрузки данных</p>';
    }
});

function renderPriceChart(prices)
{
    const ctx = document.getElementById('priceChart').getContext('2d');

    if (priceChartInstance)
    {
        priceChartInstance.destroy();
    }

    if (!Array.isArray(prices) || prices.length === 0)
    {
        return;
    }

    const firstCurrency = prices[0].currency;
    const cleanPrices = firstCurrency
        ? prices.filter(p => p.currency === firstCurrency)
        : prices;

    const labels = cleanPrices.map(p => p.date ? p.date.substring(0, 10) : '');
    const dataValues = cleanPrices.map(p => p.discountPrice / 100);

    let lineColor = '#0f2ce0';
    const brand = cleanPrices[0] ? cleanPrices[0].brand : null;

    if (brand)
    {
        const b = brand.toLowerCase();
        if (b.includes('ps plus')) lineColor = '#f1c40f';
        else if (b.includes('ea play')) lineColor = '#e74c3c';
    }

    const currencyLabel = firstCurrency ? ` (${firstCurrency})` : '';

    priceChartInstance = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: brand ? `Цена ${brand}${currencyLabel}` : `Цена${currencyLabel}`,
                data: dataValues,
                borderColor: lineColor,
                borderWidth: 2,
                tension: 0.3,
                fill: false
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: !!brand }
            },
            scales: {
                y: { beginAtZero: false }
            }
        }
    });
}

async function openProductPage(productId)
{
    homeView.classList.add('hidden');
    searchView.classList.add('hidden');
    productView.classList.remove('hidden');
    productDetails.innerHTML = 'Загрузка...';

    try
    {
        const [productResponse] = await Promise.all([
            fetch(`/api/v1/products/page?productId=${productId}`),
        ]);

        const item = await productResponse.json();

        const priceObj = item.currentPrices && item.currentPrices.length > 0 ? item.currentPrices[0] : null;
        const priceText = priceObj ? `${(priceObj.discountPrice / 100).toFixed(2)} ${priceObj.currency}` : 'Цена не указана';

        productDetails.innerHTML = `
            <div class="product-container">
                <img class="product-preview" src="${item.preview}" alt="${item.name}">
                <div class="product-details-info">
                    <h2 class="product-title">${item.name}</h2>
                    <p class="product-text"><strong>Издание:</strong> ${item.edition || 'Стандартное'}</p>
                    <p class="product-text"><strong>Издатель:</strong> ${item.publisherName || 'Не указан'}</p>
                    <p class="product-text"><strong>Жанры:</strong> ${item.genres ? item.genres.join(', ') : 'Не указаны'}</p>
                    <p class="product-text"><strong>Платформы:</strong> ${item.platforms ? item.platforms.join(', ') : 'Не указано'}</p>
                    <p class="product-text"><strong>Рейтинг:</strong> ${item.averageRating || 'Нет'} (${item.ratingsCount || 0} оценок)</p>
                    <p class="product-price"><strong>Цена:</strong> ${priceText}</p>
                </div>
            </div>

            ${item.description ? `
                <div class="product-description-block">
                    <h3>Описание</h3>
                    <div class="product-description-content">
                        ${item.description}
                    </div>
                </div>
            ` : ''}
        `;

        renderPriceChart(item.prices);
    }

    catch (error)
    {
        console.error('Ошибка загрузки товара:', error);
        productDetails.innerHTML = 'Не удалось загрузить данные о товаре';
    }
}

document.addEventListener('click', (event) =>
{
    const card = event.target.closest('.card');
    if (!card) return;

    const productId = card.getAttribute('data-id');
    openProductPage(productId);
});